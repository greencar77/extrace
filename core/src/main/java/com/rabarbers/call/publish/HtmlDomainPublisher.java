package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.filter.Filter;
import com.rabarbers.call.html.Html;
import com.rabarbers.call.html.HtmlPage;

import java.util.Set;
import java.util.stream.Collectors;

public class HtmlDomainPublisher extends Publisher {

    public void publishDomainClasses(Domain domain, String path) {
        HtmlPage root = new HtmlPage("Classes");

        StringBuilder sb = new StringBuilder();
        domain.getClasses().values().stream()
                .sorted()
                .forEach(c -> {
                    sb.append("<a href=\"classes\\" + toPath(c.getPackageX()) + "/" + c.getName() + ".txt" + "\">" + c.getFullName() + "</a>").append("<br/>").append("\n");
                });

        root.getBody().appendChildContent(sb);
        writeFileWrapper(path, root);
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
        writeFileWrapper("html/classes/" + toPath(classX.getPackageX()) + "/" + classX.getName() + ".txt", sb);
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
}
