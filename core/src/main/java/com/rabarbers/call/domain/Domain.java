package com.rabarbers.call.domain;

import java.util.HashMap;
import java.util.Map;

public class Domain {
    private Map<String, ClassX> classes = new HashMap<>();

    public Map<String, ClassX> getClasses() {
        return classes;
    }
}
