package com.rabarbers.call.domain;

import com.rabarbers.call.domain.call.Statement;
import com.rabarbers.call.pattern.Pattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trace implements Comparable<Trace> {
    private String name;
    private List<Statement> statements;
    private Statement rootStatement;
    private Set<ClassX> classes = new HashSet<>();
    private Set<MethodX> methods = new HashSet<>();
    private int callCount; //raw Call count (excluding comments etc)
    private List<Pattern> patterns = new ArrayList<>();

    public Trace(String name) {
        this.name = name;
    }

    public Trace(String name, List<Statement> statements) {
        this.name = name;
        this.statements = statements;
    }

    public String getName() {
        return name;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public Set<ClassX> getClasses() {
        return classes;
    }

    public Set<MethodX> getMethods() {
        return methods;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
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

    public List<Pattern> getPatterns() {
        return patterns;
    }
}
