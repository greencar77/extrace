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
        System.out.println("Classes: " + domain.getClasses().values().size());
        int methodCount = domain.getClasses().values().stream()
                .map(c -> c.getMethods().size())
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Methods: " + methodCount);
    }

    public void publishDomainStatistics(Domain domain, String path, AbstractCategoryByClass category) {
        Map<String, List<ClassX>> categorized = domain.getClasses().values().stream()
                .collect(Collectors.groupingBy(category::getCategory, Collectors.toList()));
        categorized.keySet().stream()
                .sorted()
                .forEach(key -> {
            System.out.println(key + ": " + categorized.get(key).size());
        });
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
