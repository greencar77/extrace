package com.rabarbers.call.domain;

public class Call {
    private int depth;
    private MethodX method;

    public Call(int depth, MethodX method) {
        this.depth = depth;
        this.method = method;
    }

    public int getDepth() {
        return depth;
    }

    public MethodX getMethod() {
        return method;
    }
}
