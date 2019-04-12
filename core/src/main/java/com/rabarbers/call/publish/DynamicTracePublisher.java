package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.Statement;
import com.rabarbers.call.domain.call.StubCall;
import com.rabarbers.call.html.Link;
import com.rabarbers.call.html.Script;
import com.rabarbers.call.html.page.HtmlPage;
import org.apache.commons.lang3.StringUtils;

public class DynamicTracePublisher extends TracePublisher {

    protected void appendScripts(HtmlPage root) {
        Script script = new Script();
        script.setAttr("src", "../" + COMMON_FOLDER + "trace.js");
        root.getHead().appendChild(script);
    }

    protected void appendStylesheets(HtmlPage root) {
        Link link = new Link();
        link.setAttr("rel", "stylesheet");
        link.setAttr("type", "text/css");
        link.setAttr("href", "../" + COMMON_FOLDER + "main.css");
        root.getHead().appendChild(link);
    }

    @Override
    protected void appendTraceDetails(StringBuilder sb, Trace trace) {
        if (!trace.getPatterns().isEmpty()) {
            trace.getPatterns().forEach(p -> {
                sb.append(p.getId()).append("<br/>");
            });
        }

        trace.getCalls().forEach(c -> {
            sb.append("<span id=\"" + "s" + c.getTreeIndex() + "\">");
            if (c instanceof Call) {
                Call call = (Call) c;
                sb.append(c.getDepth() == 0? "" : StringUtils.repeat(EMPTY_TAB, c.getDepth() - 1) + FILLED_TAB)
                        .append(getMethod(call))
                        .append(" (" + classLink(call.getMethod().getClassX(), call.getMethod().getClassX().getName(), "../") + ")")
                        .append(call.getMethod().getTraces().size() > 1? " [" + call.getMethod().getTraces().size() + "]": "");
            } else if (c instanceof StubCall) {
                StubCall stubCall = (StubCall) c;
                sb.append(c.getDepth() == 0? "" : StringUtils.repeat(EMPTY_TAB, c.getDepth() - 1) + FILLED_TAB);
                sb.append("<span class=\"comment\">");
                sb.append(stubCall.getContent());
                sb.append("</span>");
            } else {
                throw new RuntimeException(c.getClass().getCanonicalName());
            }
            sb.append(createCollapseExpand(c))
                    .append("<br/>")
                    .append("</span>");
        });
    }

    private StringBuilder createCollapseExpand(Statement statement) {
        //<span id="sb_0_1_1_5_0_0" onclick="hide('s_0_1_1_5_0_0')">[-]</span>
        if (statement.getChildren().isEmpty()) {
            return new StringBuilder();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" " + "<span"
                + " id=\"" + "col_s" + statement.getTreeIndex() + "\""
                + " onclick=\"" + "collapse('" + "s" + statement.getTreeIndex() + "')\""
                + ">");
        sb.append("(-)");
        sb.append("</span>");

        sb.append(" " + "<span"
                + " id=\"" + "ex_s" + statement.getTreeIndex() + "\""
                + " onclick=\"" + "expand('" + "s" + statement.getTreeIndex() + "')\""
                + " style=\"display: none;\""
                + ">");
        sb.append("(+)");
        sb.append("</span>");

        return sb;
    }
}
