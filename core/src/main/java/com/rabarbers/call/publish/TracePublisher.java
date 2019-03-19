package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.html.page.HtmlPage;
import org.apache.commons.lang3.StringUtils;

public abstract class TracePublisher extends HtmlPublisher {
    public static final int TAB_SIZE = 4;
    public static final String EMPTY_TAB = "|" + StringUtils.repeat("&nbsp;", TAB_SIZE);
    public static final String FILLED_TAB = "|" + StringUtils.repeat("-", TAB_SIZE);

    public void publish(Trace trace) {
        HtmlPage root = new HtmlPage("T: " + trace.getName());
        appendScripts(root);

        StringBuilder sb = new StringBuilder();
        appendTraceDetails(sb, trace);

        root.getBody().appendChildContent(sb);
        writeFileWrapper(getPublisherFolder() + "traces/" + trace.getName() + ".html", root);
    }

    protected void appendScripts(HtmlPage root) {
    }

    protected abstract void appendTraceDetails(StringBuilder sb, Trace trace);
}
