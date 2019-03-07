package com.rabarbers.call.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Domain {
    private Map<String, ClassX> classes = new HashMap<>();
    private Map<String, MethodX> methods = new HashMap<>();
    private List<Trace> traces = new ArrayList<>();

    public Map<String, ClassX> getClasses() {
        return classes;
    }

    public Map<String, MethodX> getMethods() {
        return methods;
    }

    public List<Trace> getTraces() {
        return traces;
    }
}
