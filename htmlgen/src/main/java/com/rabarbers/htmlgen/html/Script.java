package com.rabarbers.htmlgen.html;

public class Script extends Element {

    public Script() {
        super((Element) null, "script");
    }

    public Script(Element parent) {
        super(parent, "script");
    }

    public Script(String path) {
        this();
        appendChildContent(path);
    }
}
