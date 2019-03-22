package com.rabarbers.call.modres;

import com.rabarbers.call.domain.module.Module;

public abstract class ModuleResolver {
    public abstract Module resolve(String absoluteClassName);
}
