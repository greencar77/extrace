package com.rabarbers.call.domain;

import java.util.Objects;


public class Call {
    private String indent;
    private String packageName;
    private String className;
    private String methodName;
    private String signatureX;

    public Call(String indent, String packageName, String className, String methodName, String signatureX) {
        this(packageName, className, methodName, signatureX);
        this.indent = indent;
    }

    public Call(String packageName, String className, String methodName, String signatureX) {
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.signatureX = signatureX;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSignatureX() {
        return signatureX;
    }

    public void setSignatureX(String signatureX) {
        this.signatureX = signatureX;
    }

    public String getClassFullName() {
        return packageName + "." + className;
    }

    public String getMethodLocalId() {
        return methodName + signatureX;
    }

    public String getMethodGlobalId() {
        return packageName + "." + className + methodName + signatureX;
    }

    public String shortVersion() {
        return methodName + " " + "(" + className + ")";
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
                Objects.equals(getPackageName(), call.getPackageName()) &&
                Objects.equals(getClassName(), call.getClassName()) &&
                Objects.equals(getMethodName(), call.getMethodName()) &&
                Objects.equals(getSignatureX(), call.getSignatureX());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndent(), getPackageName(), getClassName(), getMethodName(), getSignatureX());
    }

    @Override
    public String toString() {
        return "Call{" +
                "indent='" + indent + '\'' +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", signatureX='" + signatureX + '\'' +
                '}';
    }
}
