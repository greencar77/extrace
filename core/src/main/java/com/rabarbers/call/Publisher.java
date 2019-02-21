package com.rabarbers.call;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.filter.Filter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Publisher {
    public static final String DATA_FOLDER = "data/";
    public static final String OUTPUT_FOLDER = DATA_FOLDER + "output/";

    public void publishDomainClasses(Domain domain, String path) {
        StringBuilder sb = new StringBuilder();
        domain.getClasses().values().stream()
                .sorted()
                .forEach(c -> sb.append(c.getFullName()).append("\n"));
        writeFileWrapper(path, sb);
    }

    public void publishDomainClassesWithMethods(Domain domain, String path) {
        StringBuilder sb = new StringBuilder();
        domain.getClasses().values().stream()
                .sorted()
                .forEach(
                        c -> {
                            sb.append(c.getFullName()).append("\n");
                            c.getMethods().values().stream()
                                    .sorted()
                                    .forEach(m -> sb.append("    " + m.getMethodLocalId()).append("\n"));
                        }
                );
        writeFileWrapper(path, sb);
    }

    public void publishDomainClassesWithMethods(Domain domain, String path, Filter filter) {
        StringBuilder sb = new StringBuilder();
        domain.getClasses().values().stream()
                .sorted()
                .filter(filter::match)
                .forEach(
                        c -> {
                            sb.append(c.getFullName()).append(" (" + c.getTraces().size() + ")").append("\n");
                            c.getMethods().values().stream()
                                    .sorted()
                                    .forEach(m -> sb.append("    " + m.getMethodLocalId()).append(" (" + m.getTraces().size() + ")").append("\n"));
                        }
                );
        writeFileWrapper(path, sb);
    }

    protected void appendClassDetails(StringBuilder sb, ClassX classX) {
        sb.append(classX.getFullName()).append("\n");
        classX.getMethods().values().stream()
                .sorted()
                .forEach(m -> sb.append("    " + m.getMethodLocalId()).append("\n"));

        sb.append("\n");
        sb.append("------------------------\n");

        classX.getMethods().values().stream()
                .sorted()
                .forEach(m -> methodDetails(sb, m));
    }

    protected void methodDetails(StringBuilder sb, MethodX method) {
        sb.append("\n");
        sb.append(method.getMethodLocalId()).append("\n");
        method.getTraces().stream()
                .sorted()
                .forEach(t -> sb.append("    " + t.getName()).append("\n"));
    }

    protected void writeFileWrapper(String path, StringBuilder stringBuilder) {
        try {
            File file = new File(OUTPUT_FOLDER + path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, stringBuilder.toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void publishDomainClassDetails(Domain domain, Filter filter) {
        if (filter == null) {
            domain.getClasses().values().stream()
                    .forEach(c -> publish(c));
        } else {
            domain.getClasses().values().stream()
                    .filter(filter::match)
                    .forEach(c -> publish(c));
        }
    }

    private void publish(ClassX classX) {
        StringBuilder sb = new StringBuilder();
        appendClassDetails(sb, classX);
        writeFileWrapper("classes/" + toPath(classX.getPackageX()) + "/" + classX.getName() + ".txt", sb);
    }

    private static final String toPath(String packageX) {
        return packageX.replaceAll("\\.", "/");
    }
}
