package com.rabarbers.call.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trace implements Comparable<Trace> {
    private String name;
    private List<Call> calls;
    private Set<ClassX> classes = new HashSet<>();
    private Set<MethodX> methods = new HashSet<>();

    public Trace(String name, List<Call> calls) {
        this.name = name;
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public Set<ClassX> getClasses() {
        return classes;
    }

    public Set<MethodX> getMethods() {
        return methods;
    }

    @Override
    public int compareTo(Trace o) {
        return this.name.compareTo(o.getName());
    }
}
