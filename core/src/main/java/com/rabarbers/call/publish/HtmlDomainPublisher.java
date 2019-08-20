package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.filter.ClassFilter;
import com.rabarbers.call.filter.FilterListBuilder;
import com.rabarbers.call.pattern.Pattern;
import com.rabarbers.call.pattern.image.PatternImageProducer;
import com.rabarbers.htmlgen.html.page.HtmlPage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HtmlDomainPublisher extends HtmlPublisher implements DomainPublisher {

    private TracePublisher tracePublisher = new DynamicTracePublisher();
    private ClassPublisher classPublisher = new AccessCategorizedClassPublisher();

    public void publishClassList(Domain domain, String path, ClassFilter classFilter) {
        HtmlPage root = new HtmlPage("Classes");

        StringBuilder sb = new StringBuilder();
        if (classFilter == null) {
            domain.getClasses().values().stream()
                    .sorted()
                    .forEach(c -> {
                        sb.append(classLink(c, c.getFullName(), null)).append("<br/>").append("\n");
                    });
        } else {
            domain.getClasses().values().stream()
                    .sorted()
                    .filter(classFilter::match)
                    .forEach(c -> {
                        sb.append(classLink(c, c.getFullName(), null)).append("<br/>").append("\n");
                    });
        }

        root.getBody().appendChildContent(sb);
        writeFileWrapper(getPublisherFolder() + path, root);
    }

    protected void output(StringBuilder sb, Collection<ClassX> classes) {
        classes.stream()
                .sorted()
                .forEach(c -> {
                    sb.append(classLink(c, c.getFullName(), null)).append("<br/>").append("\n");
                });
    }

    public void publishClassList(Domain domain, String path, String title, ClassFilter mainFilter, List<FilterListBuilder.Item> filterList) {
        HtmlPage root = new HtmlPage(title);

        StringBuilder sb = new StringBuilder();
        Set<ClassX> remainingClasses;
        if (mainFilter == null) {
            remainingClasses = new HashSet<>(domain.getClasses().values());
        } else {
            remainingClasses = new HashSet<>(domain.getClasses().values().stream().filter(mainFilter::match).collect(Collectors.toList()));
        }

        filterList.stream().forEach(f -> {
            sb.append("<h3>" + f.getTitle() + "</h3>");
            Set<ClassX> portion = remainingClasses.stream().filter(f.getFilter()::match).collect(Collectors.toSet());
            output(sb, portion);
            remainingClasses.removeAll(portion);
        });

        sb.append("<h3>" + "Other" + "</h3>");
        output(sb, remainingClasses);

        root.getBody().appendChildContent(sb);
        writeFileWrapper(getPublisherFolder() + path, root);
    }

    public void publishDomainClassDetails(Domain domain, ClassFilter classFilter) {
        if (classFilter == null) {
            domain.getClasses().values().stream()
                    .forEach(c -> classPublisher.publish(c));
        } else {
            domain.getClasses().values().stream()
                    .filter(classFilter::match)
                    .forEach(c -> classPublisher.publish(c));
        }
    }

    protected String traceLink(String backtrack, Trace trace) {
        return "<a href=\"" + backtrack + "traces/" + trace.getName() + ".html" + "\">" + trace.getName() + "</a>"
                + " ("
                + trace.getCallCount()
                + ")";
    }

    @Override
    public void publishTraceDetails(Domain domain, String path, Map<Class<? extends Pattern>, PatternImageProducer<?>> patternImageProducerMap) {
        domain.getTraces().stream()
                .forEach(t -> tracePublisher.publish(t, patternImageProducerMap));
    }
}
