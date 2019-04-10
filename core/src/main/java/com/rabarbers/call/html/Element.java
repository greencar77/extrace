package com.rabarbers.call.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Element {
    private String name;
    private String id;
    protected StringBuilder childContent = new StringBuilder();
    protected List<Element> children = new ArrayList<>();
    private Map<String, String> attributes = new HashMap<>();
    private String style;

    public Element(String name) {
        this.name = name;
    }

    public Element(String name, String id) {
        this.name = name;
        setId(id);
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

    public List<Element> getChildren() {
        return children;
    }

    public void appendChild(Element child) {
        this.children.add(child);
    }

    public StringBuilder getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("<" + name + getSerializedAttributes() + ">").append("\n");
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

    private String getSerializedAttributes() {
        if (attributes.size() > 0) {
            return " " + attributes.entrySet().stream()
                    .map(es -> es.getKey() + "=" + "\"" + es.getValue() + "\"")
                    .collect(Collectors.joining(" "));
        } else {
            return "";
        }
    }

    public void setAttr(String key, String value) {
        attributes.put(key, value);
    }

    public void setAttr(String key, Integer value) {
        attributes.put(key, value.toString());
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getStyle() {
        return style;
    }

    public void setId(String id) {
        this.id = id;
        setAttr("id", id);
    }

    public void setStyle(String style) {
        this.style = style;
        setAttr("style", style);
    }
}
