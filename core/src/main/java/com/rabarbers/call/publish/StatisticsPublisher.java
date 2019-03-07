package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.domain.Suite;
import com.rabarbers.call.misc.AbstractCategoryByClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsPublisher extends Publisher {

    public void publish(Suite suite, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("Traces: " + suite.getDomain().getTraces().size()).append("\n");
        writeFileWrapper(path, sb);
    }

    public void publishDomain(Domain domain, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("Classes: " + domain.getClasses().values().size()).append("\n");
        int methodCount = domain.getClasses().values().stream()
                .map(c -> c.getMethods().size())
                .mapToInt(Integer::intValue)
                .sum();
        sb.append("Methods: " + methodCount).append("\n");
        sb.append("Traces: " + domain.getTraces().size()).append("\n");
        writeFileWrapper(path, sb);
    }

    public void publishDomain(Domain domain, String path, AbstractCategoryByClass category) {
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
}
