package com.rabarbers.call.domain;

import java.util.HashMap;
import java.util.Map;

public class ClassX implements Comparable<ClassX> {
    private String packageX;
    private String name;
    private Map<String, MethodX> methods = new HashMap<>();

    public ClassX(Call call) {
        this(call.getPackageX(), call.getClassX());
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
}
