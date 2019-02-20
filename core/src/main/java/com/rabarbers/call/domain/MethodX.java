package com.rabarbers.call.domain;

public class MethodX implements Comparable<MethodX> {
    private String name;
    private String signatureX;

    public MethodX(Call call) {
        this.name = call.getMethodX();
        this.signatureX = call.getSignatureX();
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

    @Override
    public int compareTo(MethodX o) {
        return getMethodLocalId().compareTo(o.getMethodLocalId());
    }
}
