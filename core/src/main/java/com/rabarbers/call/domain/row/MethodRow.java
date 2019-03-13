package com.rabarbers.call.domain.row;

import java.util.Objects;


public class MethodRow extends Row {
    private String packageName;
    private String className;
    private String methodName;
    private String signatureX;

    public MethodRow(int depth, String packageName, String className, String methodName, String signatureX) {
        this(packageName, className, methodName, signatureX);
        this.setDepth(depth);
    }

    public MethodRow(String packageName, String className, String methodName, String signatureX) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodRow methodRow = (MethodRow) o;
        return Objects.equals(packageName, methodRow.packageName) &&
                Objects.equals(className, methodRow.className) &&
                Objects.equals(methodName, methodRow.methodName) &&
                Objects.equals(signatureX, methodRow.signatureX);
    }

    @Override
    public int hashCode() {

        return Objects.hash(packageName, className, methodName, signatureX);
    }

    @Override
    public String toString() {
        return "MethodRow{" +
                "packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", signatureX='" + signatureX + '\'' +
                '}';
    }
}
