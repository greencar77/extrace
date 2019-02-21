package com.rabarbers.call.filter;

import com.rabarbers.call.domain.ClassX;

public class StartsWithFilter extends Filter {
    private String start;

    public StartsWithFilter(String start) {
        this.start = start;
    }

    @Override
    public boolean match(ClassX classX) {
        return classX.getPackageX().startsWith(start);
    }
}
