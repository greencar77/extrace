package com.rabarbers.call.domain;

import java.util.List;

public class Trace {
    private String name;
    private List<Call> calls;

    public Trace(String name, List<Call> calls) {
        this.name = name;
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public List<Call> getCalls() {
        return calls;
    }
}
