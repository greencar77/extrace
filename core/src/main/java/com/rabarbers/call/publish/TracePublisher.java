package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.html.Link;
import com.rabarbers.call.html.page.HtmlPage;
import org.apache.commons.lang3.StringUtils;

public abstract class TracePublisher extends HtmlPublisher {
    public static final int TAB_SIZE = 4;
    public static final String EMPTY_TAB = "|" + StringUtils.repeat("&nbsp;", TAB_SIZE);
    public static final String FILLED_TAB = "|" + StringUtils.repeat("-", TAB_SIZE);

    public void publish(Trace trace) {
        HtmlPage root = new HtmlPage(trace.getName());
        appendStylesheets(root);
        appendScripts(root);
        appendFavicon(root);

        StringBuilder sb = new StringBuilder();
        appendTraceDetails(sb, trace);

        root.getBody().appendChildContent(sb);
        writeFileWrapper(getPublisherFolder() + "traces/" + trace.getName() + ".html", root);
    }

    protected void appendScripts(HtmlPage root) {}

    protected void appendStylesheets(HtmlPage root) {}

    protected abstract void appendTraceDetails(StringBuilder sb, Trace trace);

    protected String getMethod(Call call) {
        return "<span class=\"method\" title=\"" + call.getMethod().getSignatureX() + "\">" + call.getMethod().getName() + "</span>";
    }

    protected void appendFavicon(HtmlPage root) {
        Link link = new Link();
        link.setAttr("rel", "shortcut icon");
        link.setAttr("type", "image/png");
        link.setAttr("href", "../" + COMMON_FOLDER + "favicon_t.ico");
        root.getHead().appendChild(link);
    }
}
