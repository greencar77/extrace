package com.rabarbers.call.domain.call;

import com.rabarbers.call.domain.MethodX;

import java.util.ArrayList;
import java.util.List;

public class Call extends Statement {
    private MethodX method;

    public Call(int depth, MethodX method) {
        super(depth);
        this.method = method;
    }

    public MethodX getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "Call{" +
                "method=" + method +
                '}';
    }
}
