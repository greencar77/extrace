package com.rabarbers.call.domain;

import com.rabarbers.call.domain.row.MethodRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MethodX implements Comparable<MethodX> {
    private String name;
    private String signatureX;
    private Set<Trace> traces = new HashSet<>();
    private ClassX classX;
    private Map<String, List<String>> attributes = new HashMap<>();

    public MethodX(MethodRow methodRow) {
        this.name = methodRow.getMethodName();
        this.signatureX = methodRow.getSignatureX();
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

    public String getMethodGlobalId() {
        return classX.getFullName() + "#" + name + signatureX;
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

    public void addAttributeValue(String key, String value) {
        if (attributes.containsKey(key)) {
            attributes.get(key).add(value);
        } else {
            List<String> values = new ArrayList<>();
            values.add(value);
            attributes.put(key, values);
        }
    }

    public List<String> getAttributeValues(String key) {
        if (attributes.containsKey(key)) {
            return attributes.get(key);
        } else {
            return null;
        }
    }

    public String getAttributeValueSingle(String key) {
        if (attributes.containsKey(key)) {
            List<String> values = attributes.get(key);
            if (values.size() > 0) {
                return values.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean existsAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    @Override
    public String toString() {
        return "MethodX{" +
                "name='" + name + '\'' +
                ", signatureX='" + signatureX + '\'' +
                ", traces=" + traces +
                ", classX=" + classX +
                '}';
    }
}
