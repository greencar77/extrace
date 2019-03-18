package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.StubCall;
import com.rabarbers.call.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;

public abstract class TracePublisher extends HtmlPublisher {
    public static final int TAB_SIZE = 4;
    public static final String EMPTY_TAB = "|" + StringUtils.repeat("&nbsp;", TAB_SIZE);
    public static final String FILLED_TAB = "|" + StringUtils.repeat("-", TAB_SIZE);

    public void publish(Trace trace) {
        HtmlPage root = new HtmlPage("T: " + trace.getName());

        StringBuilder sb = new StringBuilder();
        appendTraceDetails(sb, trace);

        root.getBody().appendChildContent(sb);
        writeFileWrapper("html/traces/" + trace.getName() + ".html", root);
    }

    protected abstract void appendTraceDetails(StringBuilder sb, Trace trace);
}
