package com.rabarbers.call.filter;

import com.rabarbers.call.domain.ClassX;

public class StartsWithClassFilter extends ClassFilter {
    private String start;

    public StartsWithClassFilter(String start) {
        this.start = start;
    }

    @Override
    public boolean match(ClassX classX) {
        return classX.getPackageX().startsWith(start);
    }
}
