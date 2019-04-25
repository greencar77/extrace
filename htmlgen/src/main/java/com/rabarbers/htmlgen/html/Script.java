package com.rabarbers.htmlgen.html;

public class Script extends Element {

    public Script() {
        super("script");
    }

    public Script(String path) {
        this();
        appendChildContent(path);
    }
}
