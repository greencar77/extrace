package com.rabarbers.call.domain;

import java.util.Objects;


public class Call {
    private int depth;
    private String packageName;
    private String className;
    private String methodName;
    private String signatureX;
    private MethodX methodX;

    public Call(int depth, String packageName, String className, String methodName, String signatureX) {
        this(packageName, className, methodName, signatureX);
        this.depth = depth;
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public MethodX getMethodX() {
        return methodX;
    }

    public void setMethodX(MethodX methodX) {
        this.methodX = methodX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return depth == call.depth &&
                Objects.equals(packageName, call.packageName) &&
                Objects.equals(className, call.className) &&
                Objects.equals(methodName, call.methodName) &&
                Objects.equals(signatureX, call.signatureX) &&
                Objects.equals(methodX, call.methodX);
    }

    @Override
    public int hashCode() {

        return Objects.hash(depth, packageName, className, methodName, signatureX, methodX);
    }

    @Override
    public String toString() {
        return "Call{" +
                "depth=" + depth +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", signatureX='" + signatureX + '\'' +
                ", methodX=" + methodX +
                '}';
    }
}
