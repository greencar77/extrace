package com.rabarbers.call.pattern;

import com.rabarbers.call.domain.Trace;

import java.util.Map;

public interface PatternProducer<T extends Pattern> {
    boolean match(Trace trace);
    T produce(Trace trace);
    Map<String, Pattern> getPatterns();
}
