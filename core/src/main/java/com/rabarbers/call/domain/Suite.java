package com.rabarbers.call.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Suite {
    private Domain domain = new Domain();
    private List<Trace> traces = new ArrayList<>();
    private Map<String, String> aliases = new HashMap<>();

    public Domain getDomain() {
        return domain;
    }

    public List<Trace> getTraces() {
        return traces;
    }

    public Map<String, String> getAliases() {
        return aliases;
    }
}
