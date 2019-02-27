package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.filter.ClassFilter;
import com.rabarbers.call.html.HtmlPage;

import java.util.Set;
import java.util.stream.Collectors;

public class HtmlDomainPublisher extends Publisher {

    public static final String BR = "<br/>\n";

    public void publishDomainClasses(Domain domain, String path, ClassFilter classFilter) {
        HtmlPage root = new HtmlPage("Classes");

        StringBuilder sb = new StringBuilder();
        if (classFilter == null) {
            domain.getClasses().values().stream()
                    .sorted()
                    .forEach(c -> {
                        sb.append("<a href=\"classes\\" + toPath(c.getPackageX()) + "/" + c.getName() + ".html" + "\">" + c.getFullName() + "</a>").append("<br/>").append("\n");
                    });
        } else {
            domain.getClasses().values().stream()
                    .sorted()
                    .filter(classFilter::match)
                    .forEach(c -> {
                        sb.append("<a href=\"classes\\" + toPath(c.getPackageX()) + "/" + c.getName() + ".html" + "\">" + c.getFullName() + "</a>").append("<br/>").append("\n");
                    });
        }

        root.getBody().appendChildContent(sb);
        writeFileWrapper(path, root);
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
        HtmlPage root = new HtmlPage(classX.getName());

        StringBuilder sb = new StringBuilder();
        appendClassDetails(sb, classX);

        root.getBody().appendChildContent(sb);
        writeFileWrapper("html/classes/" + toPath(classX.getPackageX()) + "/" + classX.getName() + ".html", root);
    }

    protected void appendClassDetails(StringBuilder sb, ClassX classX) {
        sb.append("=================== Class details ===================").append(BR);
        sb.append(classX.getFullName());
        sb.append("<ul>");
        classX.getMethods().values().stream()
                .sorted()
                .forEach(m -> sb.append("<li>").append(m.getMethodLocalId()).append("</li>"));
        sb.append("</ul>");

        sb.append(BR);
        Set<Trace> classTraces = classX.getMethods().values().stream()
                .flatMap(m -> m.getTraces().stream())
                .collect(Collectors.toSet());

        sb.append("Traces:");
        sb.append("<ul>");
        classTraces.stream()
                .sorted()
                .forEach(t -> sb.append("<li>").append(t.getName()).append("</li>"));
        sb.append("</ul>");

        sb.append(BR);
        sb.append("=================== Method details ===================").append(BR);

        classX.getMethods().values().stream()
                .sorted()
                .forEach(m -> methodDetails(sb, m));
    }

    protected void methodDetails(StringBuilder sb, MethodX method) {
        sb.append(BR);
        sb.append(method.getMethodLocalId());
        sb.append("<ul>");
        method.getTraces().stream()
                .sorted()
                .forEach(t -> sb.append("<li>").append(t.getName()).append("</li>"));
        sb.append("</ul>");
    }
}
