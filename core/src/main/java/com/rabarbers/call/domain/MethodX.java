package com.rabarbers.call.domain;

import java.util.HashSet;
import java.util.Set;

public class MethodX implements Comparable<MethodX> {
    private String name;
    private String signatureX;
    private Set<Trace> traces = new HashSet<>();
    private ClassX classX;

    public MethodX(CallRow callRow) {
        this.name = callRow.getMethodName();
        this.signatureX = callRow.getSignatureX();
    }

    public String getName() {
        return name;
    }

    public String getSignatureX() {
        return signatureX;
    }

    public String getMethodLocalId() {
        return name + signatureX;
    }

    public ClassX getClassX() {
        return classX;
    }

    public void setClassX(ClassX classX) {
        this.classX = classX;
    }

    @Override
    public int compareTo(MethodX o) {
        return getMethodLocalId().compareTo(o.getMethodLocalId());
    }

    public Set<Trace> getTraces() {
        return traces;
    }

    public void setTraces(Set<Trace> traces) {
        this.traces = traces;
    }
}
