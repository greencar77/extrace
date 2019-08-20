package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.StatementAttributeName;
import com.rabarbers.htmlgen.html.page.HtmlPage;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class ClassPublisher extends HtmlPublisher {

    public void publish(ClassX classX) {
        HtmlPage root = new HtmlPage("C: " + classX.getName());

        StringBuilder sb = new StringBuilder();
        appendClassDetails(sb, classX);

        root.getBody().appendChildContent(sb);
        writeFileWrapper(getPublisherFolder() + "classes/" + toPath(classX.getPackageX()) + "/" + classX.getName() + ".html", root);
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
                .forEach(t -> sb.append("<li>").append(traceLink(classX, t)).append("</li>"));
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

        sb.append(BR);
        sb.append("Traces:");
        sb.append("<ul>");
        method.getTraces().stream()
                .sorted()
                .forEach(t -> sb.append("<li>").append(traceLink(method.getClassX(), t, method)).append("</li>"));
        sb.append("</ul>");
    }

    private String traceLink(ClassX fromClass, Trace trace, MethodX method) {
        String backtrack = StringUtils.repeat("../", fromClass.packageCount() + 1); //+1 for "classes/"
        int maxDepth = trace.getStatements().stream()
                .filter(c -> c instanceof Call && ((Call) c).getMethod().equals(method))
                .mapToInt(c -> c.getDepth())
                .min().getAsInt();
        return "<a href=\"" + backtrack + "traces/" + trace.getName() + ".html" + "\">" + trace.getName() + "</a>"
                + " ("
                + "d" + maxDepth
                + "-"
                + trace.getCallCount()
                + ")";
    }

    protected String traceLink(ClassX fromClass, Trace trace) {
        String backtrack = StringUtils.repeat("../", fromClass.packageCount() + 1); //+1 for "classes/"
        int maxDepth = trace.getStatements().stream()
                .filter(c -> c instanceof Call && ((Call) c).getMethod().getClassX().equals(fromClass))
                .mapToInt(c -> c.getDepth())
                .min().getAsInt();
        return "<a href=\"" + backtrack + "traces/" + trace.getName() + ".html" + "\">" + trace.getName() + "</a>"
                + " ("
                + "d" + maxDepth
                + "-"
                + trace.getCallCount()
                + ")";
    }
}
