package com.rabarbers.call.filter;

import com.rabarbers.call.domain.ClassX;

import java.util.function.Predicate;

public class PredicateClassFilter extends ClassFilter {

    private Predicate<ClassX> predicate;

    public PredicateClassFilter(Predicate<ClassX> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean match(ClassX classX) {
        return predicate.test(classX);
    }
}
