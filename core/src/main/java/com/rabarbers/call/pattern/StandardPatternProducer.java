package com.rabarbers.call.pattern;

import java.util.HashMap;
import java.util.Map;

public abstract class StandardPatternProducer<T extends Pattern> implements PatternProducer<T> {
    private Map<String, Pattern> patterns = new HashMap<>();

    @Override
    public Map<String, Pattern> getPatterns() {
        return patterns;
    }
}
