package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.StubCall;
import com.rabarbers.call.html.Element;
import com.rabarbers.call.html.InnerHtml;
import com.rabarbers.call.pattern.Pattern;
import com.rabarbers.call.pattern.image.PatternImageProducer;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class SimpleTracePublisher extends TracePublisher {

    @Override
    protected Element appendTraceDetails(Element element, Trace trace, Map<Class<? extends Pattern>, PatternImageProducer<? extends Pattern>> patternImageProducerMap) {
        StringBuilder sb = new StringBuilder();

        trace.getCalls().forEach(c -> {
            if (c instanceof Call) {
                Call call = (Call) c;
                sb.append(c.getDepth() == 0? "" : StringUtils.repeat(EMPTY_TAB, c.getDepth() - 1) + FILLED_TAB)
                        .append(getMethod(call))
                        .append(" (" + classLink(call.getMethod().getClassX(), call.getMethod().getClassX().getName(), "../") + ")")
                        .append(call.getMethod().getTraces().size() > 1? " [" + call.getMethod().getTraces().size() + "]": "")
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

        InnerHtml result = new InnerHtml(sb);
        element.appendChild(result);
        return result;
    }
}
