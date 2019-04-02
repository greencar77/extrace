package com.rabarbers.call.filter;

import com.rabarbers.call.domain.ClassX;

public class ContainsClassFilter extends ClassFilter {
    private String part;

    public ContainsClassFilter(String part) {
        this.part = part;
    }

    @Override
    public boolean match(ClassX classX) {
        return classX.getFullName().contains(part);
    }
}