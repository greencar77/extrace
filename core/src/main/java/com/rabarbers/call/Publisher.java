package com.rabarbers.call;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.misc.AbstractCategoryByClass;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Publisher {

    public void publishDomainClasses(Domain domain, String path) {
        StringBuilder sb = new StringBuilder();
        domain.getClasses().values().stream()
                .sorted()
                .forEach(
                c -> sb.append(c.getFullName()).append("\n")
        );
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

    public void publishDomainStatistics(Domain domain, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("Classes: " + domain.getClasses().values().size()).append("\n");
        int methodCount = domain.getClasses().values().stream()
                .map(c -> c.getMethods().size())
                .mapToInt(Integer::intValue)
                .sum();
        sb.append("Methods: " + methodCount).append("\n");
        writeFileWrapper(path, sb);
    }

    public void publishDomainStatistics(Domain domain, String path, AbstractCategoryByClass category) {
        StringBuilder sb = new StringBuilder();
        Map<String, List<ClassX>> categorized = domain.getClasses().values().stream()
                .collect(Collectors.groupingBy(category::getCategory, Collectors.toList()));
        categorized.keySet().stream()
                .sorted()
                .forEach(key -> {
                    sb.append(key + ": " + categorized.get(key).size());

                    int methodCount = categorized.get(key).stream().map(c -> c.getMethods().size())
                            .mapToInt(Integer::intValue)
                            .sum();
                    sb.append("/" + methodCount);
                    sb.append("\n");
                });
        writeFileWrapper(path, sb);
    }

    private void writeFileWrapper(String path, StringBuilder stringBuilder) {
        try {
            File file = new File(path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, stringBuilder.toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
