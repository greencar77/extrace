package com.rabarbers.call.domain.call;

import java.util.ArrayList;
import java.util.List;

public abstract class Statement {
    private int depth;
    private String treeIndex;

    private Statement parent;
    private List<Statement> children = new ArrayList<>();

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
}
