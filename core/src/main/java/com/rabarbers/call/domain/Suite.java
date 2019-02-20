package com.rabarbers.call.domain;

import java.util.ArrayList;
import java.util.List;

public class Suite {
    private Domain domain = new Domain();
    private List<Trace> traces = new ArrayList<>();

    public Domain getDomain() {
        return domain;
    }

    public List<Trace> getTraces() {
        return traces;
    }
}
