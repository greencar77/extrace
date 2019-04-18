package com.rabarbers.call.pattern;

import com.rabarbers.call.domain.Trace;

import java.util.HashSet;
import java.util.Set;

public abstract class Pattern {
    private PatternProducer<?> patternProducer;
    private Set<Trace> traces = new HashSet<>();

    public Set<Trace> getTraces() {
        return traces;
    }

    public PatternProducer<?> getPatternProducer() {
        return patternProducer;
    }

    public void setPatternProducer(PatternProducer<?> patternProducer) {
        this.patternProducer = patternProducer;
    }

    public abstract String getId();

    public String getPatternId() {
        return this.getClass().getSimpleName() + "-" + getId();
    }
}
