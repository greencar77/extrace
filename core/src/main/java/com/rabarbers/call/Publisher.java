package com.rabarbers.call;

import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.filter.Filter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Publisher {

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
                            sb.append(c.getFullName()).append("\n");
                            c.getMethods().values().stream()
                                    .sorted()
                                    .forEach(m -> sb.append("    " + m.getMethodLocalId()).append("\n"));
                        }
                );
        writeFileWrapper(path, sb);
    }

    protected void writeFileWrapper(String path, StringBuilder stringBuilder) {
        try {
            File file = new File(path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, stringBuilder.toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
