package com.rabarbers.call.filter.custom;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.filter.PredicateClassFilter;

import java.util.function.Predicate;

public class JdkClassFilter extends PredicateClassFilter {
    public JdkClassFilter() {
        super(c -> c.getFullName().startsWith("java.") || c.getFullName().startsWith("javax."));
    }
}
