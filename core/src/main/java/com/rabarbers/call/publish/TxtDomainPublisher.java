package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.filter.ClassFilter;

import java.util.Set;
import java.util.stream.Collectors;

public class TxtDomainPublisher extends Publisher implements DomainPublisher {

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

    public void publishDomainClassesWithMethods(Domain domain, String path, ClassFilter classFilter) {
        StringBuilder sb = new StringBuilder();
        domain.getClasses().values().stream()
                .sorted()
                .filter(classFilter::match)
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
        sb.append("=================== Class details ===================").append("\n");
        sb.append(classX.getFullName()).append("\n");
        classX.getMethods().values().stream()
                .sorted()
                .forEach(m -> sb.append("    " + m.getMethodLocalId()).append("\n"));

        sb.append("\n");
        Set<Trace> classTraces = classX.getMethods().values().stream()
                .flatMap(m -> m.getTraces().stream())
                .collect(Collectors.toSet());

        sb.append("Traces:").append("\n");
        classTraces.stream()
                .sorted()
                .forEach(t -> sb.append("    " + t.getName()).append("\n"));

        sb.append("\n");
        sb.append("=================== Method details ===================").append("\n");

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

    public void publishDomainClassDetails(Domain domain, ClassFilter classFilter) {
        if (classFilter == null) {
            domain.getClasses().values().stream()
                    .forEach(c -> publish(c));
        } else {
            domain.getClasses().values().stream()
                    .filter(classFilter::match)
                    .forEach(c -> publish(c));
        }
    }

    private void publish(ClassX classX) {
        StringBuilder sb = new StringBuilder();
        appendClassDetails(sb, classX);
        writeFileWrapper("classes/" + toPath(classX.getPackageX()) + "/" + classX.getName() + ".txt", sb);
    }

    @Override
    public void publishTraceDetails(Domain domain, String path) {
        throw new RuntimeException("Not implemented"); //TODO
    }
}
