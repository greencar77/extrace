package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.StatementAttributeName;

import java.util.Set;
import java.util.stream.Collectors;

public class AccessCategorizedClassPublisher extends ClassPublisher {

    @Override
    protected void appendClassDetails(StringBuilder sb, ClassX classX) {
        sb.append("=================== Class details ===================").append(BR);
        sb.append(classX.getFullName());
        sb.append("<ul>");

        sb.append("<li>").append("INTERCLASS").append("</li>");
        sb.append("<ul>");
        classX.getMethods().values().stream()
                .filter(m -> m.existsAttribute(StatementAttributeName.INTERCLASS.name()))
                .sorted()
                .forEach(m -> sb.append("<li>").append(m.getMethodLocalId()).append("</li>"));
        sb.append("</ul>");

        sb.append("<li>").append("inner").append("</li>");
        sb.append("<ul>");
        classX.getMethods().values().stream()
                .filter(m -> !m.existsAttribute(StatementAttributeName.INTERCLASS.name()))
                .sorted()
                .forEach(m -> sb.append("<li>").append(m.getMethodLocalId()).append("</li>"));
        sb.append("</ul>");
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
}
