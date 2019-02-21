package com.rabarbers.call.domain;

import java.util.Objects;


public class Call {
    private String indent;
    private String packageX;
    private String classX;
    private String methodX;
    private String signatureX;

    public Call(String indent, String packageX, String classX, String methodX, String signatureX) {
        this(packageX, classX, methodX, signatureX);
        this.indent = indent;
    }

    public Call(String packageX, String classX, String methodX, String signatureX) {
        this.packageX = packageX;
        this.classX = classX;
        this.methodX = methodX;
        this.signatureX = signatureX;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getMethodX() {
        return methodX;
    }

    public void setMethodX(String methodX) {
        this.methodX = methodX;
    }

    public String getSignatureX() {
        return signatureX;
    }

    public void setSignatureX(String signatureX) {
        this.signatureX = signatureX;
    }

    public String getClassFullName() {
        return packageX + "." + classX;
    }

    public String getMethodLocalId() {
        return methodX + signatureX;
    }

    public String getMethodGlobalId() {
        return packageX + "." + classX + methodX + signatureX;
    }

    public String shortVersion() {
        return methodX + " " + "(" + classX + ")";
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;

        Call call = (Call) o;
        return Objects.equals(getIndent(), call.getIndent()) &&
                Objects.equals(getPackageX(), call.getPackageX()) &&
                Objects.equals(getClassX(), call.getClassX()) &&
                Objects.equals(getMethodX(), call.getMethodX()) &&
                Objects.equals(getSignatureX(), call.getSignatureX());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndent(), getPackageX(), getClassX(), getMethodX(), getSignatureX());
    }

    @Override
    public String toString() {
        return "Call{" +
                "indent='" + indent + '\'' +
                ", packageX='" + packageX + '\'' +
                ", classX='" + classX + '\'' +
                ", methodX='" + methodX + '\'' +
                ", signatureX='" + signatureX + '\'' +
                '}';
    }
}
