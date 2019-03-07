package com.rabarbers.call;

import com.rabarbers.call.domain.CallRow;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallTransformer {
    public static final int TAB_SIZE = 2;

    private static final Pattern PATTERN = Pattern.compile("^(\\s*)(.*)");
    public static final String UNKNOWN = "...";
    public static final String UNKNOWN_EXCEL = "…";
    public static final String COMMENT_MARK = "//";
    public static final String INTELLIJ_METHOD_SEPARATOR = "#";

    public static CallRow convertToCallTrimmed(String statement, Map<String, String> aliases) {
        if (statement == null || statement.trim().length() == 0) {
            return null;
        } else {
            statement = statement.trim(); //! difference from convertToCallWithWhitespace
        }

        if (aliases != null && aliases.keySet().contains(statement)) {
            statement = aliases.get(statement);
        }

        if (UNKNOWN.equals(statement.trim()) || UNKNOWN_EXCEL.equals(statement.trim()) || statement.trim().startsWith(COMMENT_MARK)) {
            return null;
        }

        statement = normalizeStatement(statement, aliases);

        String packageToMethod = statement.substring(0, statement.lastIndexOf("("));

        String packageAndClass = packageToMethod.substring(0, packageToMethod.indexOf(INTELLIJ_METHOD_SEPARATOR));
        String method = packageToMethod.substring(packageAndClass.length() + 1);
        String signature = statement.substring(statement.indexOf("("));

        return new CallRow(packageAndClass.substring(0, packageAndClass.lastIndexOf(".")),
                packageAndClass.substring(packageAndClass.lastIndexOf(".") + 1),
                method, signature);
    }

    private static String normalizeStatement(String statement, Map<String, String> aliases) {

        if (statement.indexOf(INTELLIJ_METHOD_SEPARATOR) == -1) {
            String[] parts = statement.split("\\.");
            if (parts.length < 2) {
                throw new RuntimeException("[" + statement + "]" + parts.length);
            }
            if (parts[parts.length - 1].equals(parts[parts.length - 2])) {
                //com.rabarbers.call.Alpha.Alpha
                statement = statement.substring(0, statement.lastIndexOf(".")) + INTELLIJ_METHOD_SEPARATOR + statement.substring(statement.lastIndexOf(".") + 1);
            } else {
                //com.rabarbers.call.Alpha
                statement = statement + INTELLIJ_METHOD_SEPARATOR + parts[parts.length - 1];
            }
        }

        if (!statement.contains("(")) {
            statement += "()";
        }

        return statement;
    }

    public static boolean isIntellijStyle(String statement) {
        return statement.contains("#");
    }

    public static CallRow convertToCallWithWhitespace(String statement, Map<String, String> aliases) {
        if (statement == null || statement.trim().length() == 0) {
            return null;
        } else {
            statement = StringUtils.stripEnd(statement, null);
        }

        if (UNKNOWN.equals(statement.trim()) || UNKNOWN_EXCEL.equals(statement.trim()) || statement.trim().startsWith(COMMENT_MARK)) {
            return null;
        }

        String indent = null;
        if (!statement.equals(statement.trim())) {
            Matcher matcher = PATTERN.matcher(statement);
            if (matcher.find()) {
                indent = matcher.group(1);
            }
        }

        CallRow result = convertToCallTrimmed(statement.trim(), aliases);
        if (result != null && indent != null) {
            int depth = 0;
            if (indent.startsWith("\t")) {
                depth = indent.length();
                if (indent.contains(" ")) {
                    throw new RuntimeException("Mixed indent: [" + indent + "]");
                }
            } else if (indent.startsWith(" ")) {
                if (indent.length() % TAB_SIZE != 0) {
                    throw new RuntimeException("Invalid indent length: [" + indent + "]" + " " + indent.length());
                } else {
                    depth = indent.length() / TAB_SIZE;
                }
            } else {
                throw new RuntimeException("Invalid indent: [" + indent + "]");
            }
            result.setDepth(depth);
        }
        return result;
    }

    public static List<CallRow> convertToList(File file, Map<String, String> aliases) {
        try {
            return convertToList(new FileInputStream(file), aliases);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(file.getAbsolutePath(), e);
        }
    }

    public static File convertToFile(String sourcePath, String destinationPath, Map<String, String> aliases) {
        return convertToFile(new File(sourcePath), destinationPath, aliases);
    }

    public static File convertToFile(File sourceFile, String destinationPath, Map<String, String> aliases) {
        StringBuilder sb = new StringBuilder();
        convertToList(sourceFile, aliases).stream()
                .filter(call -> call != null)
                .forEach(call -> {
                    sb.append(depthToIndent(call.getDepth()) + call.shortVersion()).append("\r\n");
                });

        File result = new File(destinationPath);
        try {
            FileUtils.writeByteArrayToFile(result, sb.toString().getBytes(Charset.forName("UTF8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static List<CallRow> convertToList(InputStream is, Map<String, String> aliases) {
        List<CallRow> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(convertToCallWithWhitespace(line, aliases));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static String depthToIndent(int depth) {
        StringBuilder sb = new StringBuilder(); //TODO more effectively with patterns
        for (int i = 0; i<depth; i++) {
            sb.append("  "); //TODO
        }
        return sb.toString();
    }
}
