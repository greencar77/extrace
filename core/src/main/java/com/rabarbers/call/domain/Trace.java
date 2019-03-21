package com.rabarbers.call.domain;

import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.Statement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trace implements Comparable<Trace> {
    private String name;
    private List<Statement> calls;
    private Statement rootStatement;
    private Set<ClassX> classes = new HashSet<>();
    private Set<MethodX> methods = new HashSet<>();
    private int callCount;

    public Trace(String name) {
        this.name = name;
    }

    public Trace(String name, List<Statement> calls) {
        this.name = name;
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public List<Statement> getCalls() {
        return calls;
    }

    public Set<ClassX> getClasses() {
        return classes;
    }

    public Set<MethodX> getMethods() {
        return methods;
    }

    public void setCalls(List<Statement> calls) {
        this.calls = calls;
    }

    @Override
    public int compareTo(Trace o) {
        return this.name.compareTo(o.getName());
    }

    public Statement getRootStatement() {
        return rootStatement;
    }

    public void setRootStatement(Statement rootStatement) {
        this.rootStatement = rootStatement;
    }

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }
}
