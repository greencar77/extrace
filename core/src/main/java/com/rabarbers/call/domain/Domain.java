package com.rabarbers.call.domain;

import com.rabarbers.call.domain.module.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Domain {
    private Set<Module> modules = new HashSet<>();
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

    public Set<Module> getModules() {
        return modules;
    }
}
