package com.rabarbers.call.filter;

import com.rabarbers.call.domain.ClassX;

public class EndsWithClassFilter extends ClassFilter {
    private String end;

    public EndsWithClassFilter(String end) {
        this.end = end;
    }

    @Override
    public boolean match(ClassX classX) {
        return classX.getFullName().endsWith(end);
    }
}
