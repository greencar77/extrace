package com.rabarbers.call.html;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private String name;
    protected StringBuilder childContent = new StringBuilder();
    protected List<Node> children = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public StringBuilder getChildContent() {
        return childContent;
    }

    public void appendChildContent(StringBuilder childContent) {
        this.childContent.append(childContent);
    }

    public void appendChildContent(String childContent) {
        this.childContent.append(childContent);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void appendChild(Node child) {
        this.children.add(child);
    }

    public StringBuilder getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("<" + name + ">").append("\n");
        if (childContent.length() > 0 && children.size() > 0) {
            throw new RuntimeException("Only one content type allowed " + name);
        }
        if (childContent.length() > 0) {
            sb.append(childContent);
        } else if (children.size() > 0) {
            children.stream().forEach(c -> sb.append(c.getContent()));
        }
        sb.append("</" + name + ">").append("\n");
        return sb;
    }
}
