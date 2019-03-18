package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.StubCall;
import org.apache.commons.lang3.StringUtils;

public class DynamicTracePublisher extends TracePublisher {

    @Override
    protected void appendTraceDetails(StringBuilder sb, Trace trace) {
        //TODO
        trace.getCalls().forEach(c -> {
            if (c instanceof Call) {
                Call call = (Call) c;
                sb.append("<span id=\"" + call.getTreeIndex() + "\">")
                        .append(c.getDepth() == 0? "" : StringUtils.repeat(EMPTY_TAB, c.getDepth() - 1) + FILLED_TAB)
                        .append(call.getMethod().getName())
                        .append(" (" + classLink(call.getMethod().getClassX(), call.getMethod().getClassX().getName(), "../") + ")")
                        .append(call.getMethod().getTraces().size() > 1? " [" + call.getMethod().getTraces().size() + "]": "")
                        .append("</span>")
                        .append(BR);
            } else if (c instanceof StubCall) {
                StubCall stubCall = (StubCall) c;
                sb.append(c.getDepth() == 0? "" : StringUtils.repeat(EMPTY_TAB, c.getDepth() - 1) + FILLED_TAB)
                        .append(stubCall.getContent())
                        .append(BR);
            } else {
                throw new RuntimeException(c.getClass().getCanonicalName());
            }
        });
    }
}
