package com.rabarbers.call.html;

import java.util.List;

public class Node {
    private String name;
    private StringBuilder content;
    private List<Node> children;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
