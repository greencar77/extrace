package com.rabarbers.call.domain;

import java.util.Objects;


public class CallRow {
    private int depth;
    private String packageName;
    private String className;
    private String methodName;
    private String signatureX;

    public CallRow(int depth, String packageName, String className, String methodName, String signatureX) {
        this(packageName, className, methodName, signatureX);
        this.depth = depth;
    }

    public CallRow(String packageName, String className, String methodName, String signatureX) {
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallRow callRow = (CallRow) o;
        return depth == callRow.depth &&
                Objects.equals(packageName, callRow.packageName) &&
                Objects.equals(className, callRow.className) &&
                Objects.equals(methodName, callRow.methodName) &&
                Objects.equals(signatureX, callRow.signatureX);
    }

    @Override
    public int hashCode() {

        return Objects.hash(depth, packageName, className, methodName, signatureX);
    }

    @Override
    public String toString() {
        return "CallRow{" +
                "depth=" + depth +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", signatureX='" + signatureX + '\'' +
                '}';
    }
}
