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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallTransformer {
    private static final Pattern PATTERN = Pattern.compile("^(\\s*)(.*)");
    public static final String UNKNOWN = "...";
    public static final String UNKNOWN_EXCEL = "…";
    public static final String COMMENT_MARK = "//";

    public static Call convertToCallTrimmed(String statement) {
        if (statement == null || statement.trim().length() == 0) {
            return null;
        } else {
            statement = StringUtils.trim(statement); //! difference from convertToCallWithWhitespace
        }

        if (UNKNOWN.equals(statement.trim()) || UNKNOWN_EXCEL.equals(statement.trim()) || statement.trim().startsWith(COMMENT_MARK)) {
            return null;
        }

        if (isIntellijStyle(statement)) {
            //convert to Eclipse style reference
            statement = statement.replaceAll("#", ".");
        }

        if (!statement.contains("(")) {
            statement += "()";
        }

        statement = statement.trim();

        String packageToMethod = statement.substring(0, statement.lastIndexOf("("));

        String packageAndClass = packageToMethod.substring(0, packageToMethod.lastIndexOf("."));
        String method = packageToMethod.substring(packageAndClass.length() + 1);
        String signature = statement.substring(statement.indexOf("("));

        return new Call(packageAndClass.substring(0, packageAndClass.lastIndexOf(".")),
                packageAndClass.substring(packageAndClass.lastIndexOf(".") + 1),
                method, signature);
    }

    public static boolean isIntellijStyle(String statement) {
        return statement.contains("#");
    }

    public static Call convertToCallWithWhitespace(String statement) {
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

        Call result = convertToCallTrimmed(statement.trim());
        if (indent != null) {
            result.setIndent(indent);
        }
        return result;
    }

    public static List<Call> convertToList(File file) {
        try {
            return convertToList(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(file.getAbsolutePath(), e);
        }
    }

    public static File convertToFile(File file, String path) {
        StringBuilder sb = new StringBuilder();
        convertToList(file).stream()
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
//        try (FileOutputStream fos = new FileOutputStream(path)) {
//            fos.write(sb.toString().getBytes());
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static List<Call> convertToList(InputStream is) {
        List<Call> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(convertToCallWithWhitespace(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
