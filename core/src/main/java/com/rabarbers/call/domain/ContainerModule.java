package com.rabarbers.call.domain;

import com.rabarbers.call.domain.module.Module;

import java.util.Set;
import java.util.TreeSet;

public class ContainerModule {
    private Module module;
    private Set<ClassX> classes = new TreeSet<>();

    public ContainerModule(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public Set<ClassX> getClasses() {
        return classes;
    }
}
