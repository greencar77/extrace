package com.rabarbers.call.pattern;

import com.rabarbers.call.domain.Trace;

import java.util.HashSet;
import java.util.Set;

public abstract class Pattern {
    private PatternProducer<?> patternProducer;
    private String id;
    private Set<Trace> traces = new HashSet<>();

    public Pattern(String id) {
        this.id = id;
    }

    public String getId() {
        return this.getClass().getSimpleName() + "-" + id;
    }

    public Set<Trace> getTraces() {
        return traces;
    }

    public PatternProducer<?> getPatternProducer() {
        return patternProducer;
    }

    public void setPatternProducer(PatternProducer<?> patternProducer) {
        this.patternProducer = patternProducer;
    }
}
