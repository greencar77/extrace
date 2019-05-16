package com.rabarbers.htmlgen.html;

public class Title extends Element {

    public Title() {
        super("title");
    }

    public Title(String text) {
        this();
        appendChildContent(text);
    }
}