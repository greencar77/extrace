package com.rabarbers.call.domain;

import com.rabarbers.call.domain.row.MethodRow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassX implements Comparable<ClassX> {
    private String packageX;
    private String name;
    private Map<String, MethodX> methods = new HashMap<>();
    private Set<Trace> traces = new HashSet<>();

    public ClassX(MethodRow methodRow) {
        this(methodRow.getPackageName(), methodRow.getClassName());
    }

    public ClassX(String packageX, String name) {
        this.packageX = packageX;
        this.name = name;
    }

    public Map<String, MethodX> getMethods() {
        return methods;
    }

    public String getPackageX() {
        return packageX;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return packageX + "." + name;
    }

    @Override
    public int compareTo(ClassX o) {
        return getFullName().compareTo(o.getFullName());
    }

    public Set<Trace> getTraces() {
        return traces;
    }

    public void setTraces(Set<Trace> traces) {
        this.traces = traces;
    }

    public int packageCount() {
        return packageX.split("\\.").length;
    }
}
