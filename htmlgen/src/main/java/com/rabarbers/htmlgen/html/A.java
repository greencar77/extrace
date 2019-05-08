package com.rabarbers.htmlgen.html;

public class A extends Element {
    public A() {
        super("a");
    }

    public A(String href, String title) {
        this();
        setAttr("href", href);
        appendChildContent(title);
    }
}
