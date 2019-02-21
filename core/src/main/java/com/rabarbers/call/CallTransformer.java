package com.rabarbers.call;

import com.rabarbers.call.domain.Call;
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
    private static final Pattern PATTERN = Pattern.compile("^(\\s*)(.*)");
    public static final String UNKNOWN = "...";
    public static final String UNKNOWN_EXCEL = "…";
    public static final String COMMENT_MARK = "//";
    public static final String INTELLIJ_METHOD_SEPARATOR = "#";

    public static Call convertToCallTrimmed(String statement, Map<String, String> aliases) {
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

        return new Call(packageAndClass.substring(0, packageAndClass.lastIndexOf(".")),
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

    public static Call convertToCallWithWhitespace(String statement, Map<String, String> aliases) {
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

        Call result = convertToCallTrimmed(statement.trim(), aliases);
        if (result != null && indent != null) {
            result.setIndent(indent);
        }
        return result;
    }

    public static List<Call> convertToList(File file, Map<String, String> aliases) {
        try {
            return convertToList(new FileInputStream(file), aliases);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(file.getAbsolutePath(), e);
        }
    }

    public static File convertToFile(File file, String path, Map<String, String> aliases) {
        StringBuilder sb = new StringBuilder();
        convertToList(file, aliases).stream()
                .filter(call -> call != null)
                .forEach(call -> {
                    sb.append(call.getIndent() + call.shortVersion()).append("\r\n");
                });

        File result = new File(path);
        try {
            FileUtils.writeByteArrayToFile(result, sb.toString().getBytes(Charset.forName("UTF8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static List<Call> convertToList(InputStream is, Map<String, String> aliases) {
        List<Call> result = new ArrayList<>();
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
}
