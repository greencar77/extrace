package com.rabarbers.call.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trace implements Comparable<Trace> {
    private String name;
    private List<CallRow> callRows;
    private Set<ClassX> classes = new HashSet<>();
    private Set<MethodX> methods = new HashSet<>();

    public Trace(String name, List<CallRow> callRows) {
        this.name = name;
        this.callRows = callRows;
    }

    public String getName() {
        return name;
    }

    public List<CallRow> getCallRows() {
        return callRows;
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
