package com.rabarbers.htmlgen.html;

public class A extends Element {
    public A() {
        super((Element) null, "a");
    }

    public A(Element parent) {
        super(parent, "a");
    }

    public A(String href, String title) {
        this();
        setAttr("href", href);
        appendChildContent(title);
    }
}
