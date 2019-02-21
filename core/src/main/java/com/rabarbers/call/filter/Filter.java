package com.rabarbers.call.filter;

import com.rabarbers.call.domain.ClassX;

public abstract class Filter {
    public abstract boolean match(ClassX classX);
}
