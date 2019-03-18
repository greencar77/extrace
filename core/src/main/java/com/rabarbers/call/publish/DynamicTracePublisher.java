package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.Statement;
import com.rabarbers.call.domain.call.StubCall;
import com.rabarbers.call.html.Script;
import com.rabarbers.call.html.page.HtmlPage;
import org.apache.commons.lang3.StringUtils;

public class DynamicTracePublisher extends TracePublisher {

    public void publish(Trace trace) {
        HtmlPage root = new HtmlPage("T: " + trace.getName());
        Script script = new Script();
        script.setAttr("src", "../common/trace.js");
        root.getHead().appendChild(script);

        StringBuilder sb = new StringBuilder();
        appendTraceDetails(sb, trace);

        root.getBody().appendChildContent(sb);
        writeFileWrapper("html/traces/" + trace.getName() + ".html", root);
    }

    @Override
    protected void appendTraceDetails(StringBuilder sb, Trace trace) {
        //TODO
        trace.getCalls().forEach(c -> {
            sb.append("<span id=\"" + "s" + c.getTreeIndex() + "\">");
            if (c instanceof Call) {
                Call call = (Call) c;
                sb.append(c.getDepth() == 0? "" : StringUtils.repeat(EMPTY_TAB, c.getDepth() - 1) + FILLED_TAB)
                        .append(call.getMethod().getName())
                        .append(" (" + classLink(call.getMethod().getClassX(), call.getMethod().getClassX().getName(), "../") + ")")
                        .append(call.getMethod().getTraces().size() > 1? " [" + call.getMethod().getTraces().size() + "]": "");
            } else if (c instanceof StubCall) {
                StubCall stubCall = (StubCall) c;
                sb.append(c.getDepth() == 0? "" : StringUtils.repeat(EMPTY_TAB, c.getDepth() - 1) + FILLED_TAB)
                        .append(stubCall.getContent());
            } else {
                throw new RuntimeException(c.getClass().getCanonicalName());
            }
            sb.append(createCollapse(c))
                    .append("</span>")
                    .append(BR);
        });
    }

    private StringBuilder createCollapse(Statement statement) {
        StringBuilder sb = new StringBuilder();
        //<span onclick="expand('1')" id="a_1">[+]</span>

        sb.append(" " + "<span"
                + " id=\"" + "sb" + statement.getTreeIndex() + "\""
                + " onclick=\"" + "hide('" + "s" + statement.getTreeIndex() + "')" + "\">");

        sb.append("[-]");
        sb.append("</span>");

        return sb;
    }
}
