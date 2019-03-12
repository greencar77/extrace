package com.rabarbers.call.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Suite {
    public static final int TAB_SIZE = 2;

    private Domain domain = new Domain();
    private Map<String, String> aliases = new HashMap<>();
    private Set<String> forbiddenClasses = new HashSet<>();
    private Set<String> forbiddenMethods = new HashSet<>();

    public Domain getDomain() {
        return domain;
    }

    public Map<String, String> getAliases() {
        return aliases;
    }

    public Set<String> getForbiddenClasses() {
        return forbiddenClasses;
    }

    public void setForbiddenClasses(Set<String> forbiddenClasses) {
        this.forbiddenClasses = forbiddenClasses;
    }

    public Set<String> getForbiddenMethods() {
        return forbiddenMethods;
    }

    public void setForbiddenMethods(Set<String> forbiddenMethods) {
        this.forbiddenMethods = forbiddenMethods;
    }
}
