package com.rabarbers.call.domain.call;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Statement {
    private int depth;
    private String treeIndex;

    private Statement parent;
    private List<Statement> children = new ArrayList<>();
    private Map<String, List<String>> attributes = new HashMap<>();

    public Statement() {
    }

    public Statement(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public Statement getParent() {
        return parent;
    }

    public List<Statement> getChildren() {
        return children;
    }

    public void setParent(Statement parent) {
        this.parent = parent;
    }

    public String getTreeIndex() {
        return treeIndex;
    }

    public void setTreeIndex(String treeIndex) {
        this.treeIndex = treeIndex;
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
}
