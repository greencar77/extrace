package com.rabarbers.call;

import com.rabarbers.call.domain.row.MethodRow;
import com.rabarbers.call.domain.row.Row;
import com.rabarbers.call.domain.row.TextRow;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RowTransformer {
    public static final int TAB_SIZE = 2;

    private static final Pattern PATTERN = Pattern.compile("^(\\s*)(.*)");
    public static final String UNKNOWN = "...";
    public static final String UNKNOWN_EXCEL = "…";
    public static final String COMMENT_MARK = "//";
    public static final String INTELLIJ_METHOD_SEPARATOR = "#";

    static Row convertToCallTrimmed(String statement, Map<String, String> aliases, int depth) {
        if (statement == null || statement.trim().length() == 0) {
            return null;
        } else {
            statement = statement.trim(); //! difference from convertToCallWithWhitespace
        }

        if (aliases != null && aliases.keySet().contains(statement)) {
            statement = aliases.get(statement);
        }

        if (UNKNOWN.equals(statement.trim()) || UNKNOWN_EXCEL.equals(statement.trim()) || statement.trim().startsWith(COMMENT_MARK)) {
            Row result = new TextRow(statement.trim());
            result.setDepth(depth);
            return result;
        }

        statement = normalizeStatement(statement);

        String packageToMethod = statement.substring(0, statement.lastIndexOf("("));

        String packageAndClass = packageToMethod.substring(0, packageToMethod.indexOf(INTELLIJ_METHOD_SEPARATOR));
        String method = packageToMethod.substring(packageAndClass.length() + 1);
        String signature = statement.substring(statement.indexOf("("));

        return new MethodRow(packageAndClass.substring(0, packageAndClass.lastIndexOf(".")),
                packageAndClass.substring(packageAndClass.lastIndexOf(".") + 1),
                method, signature);
    }

    public static String normalizeStatement(String statement) {

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

    public static Row convertToCallWithWhitespace(String statement, Map<String, String> aliases) {
        if (statement == null || statement.trim().length() == 0) {
            return null;
        } else {
            statement = StringUtils.stripEnd(statement, null);
        }

        String indent = null;
        if (!statement.equals(statement.trim())) {
            Matcher matcher = PATTERN.matcher(statement);
            if (matcher.find()) {
                indent = matcher.group(1);
            }
        }

        if (UNKNOWN.equals(statement.trim()) || UNKNOWN_EXCEL.equals(statement.trim()) || statement.trim().startsWith(COMMENT_MARK)) {
            Row result = new TextRow(statement.trim());
            result.setDepth(getDepth(indent));
            return result;
        }

        Row result = convertToCallTrimmed(statement.trim(), aliases, getDepth(indent));
        if (result != null && indent != null) {
            result.setDepth(getDepth(indent));
        }
        return result;
    }

    private static int getDepth(String indent) {
        if (indent == null) {
            return 0;
        }

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

        return depth;
    }

    private static String depthToIndent(int depth) {
        StringBuilder sb = new StringBuilder(); //TODO more effectively with patterns
        for (int i = 0; i<depth; i++) {
            sb.append("  "); //TODO
        }
        return sb.toString();
    }
}
