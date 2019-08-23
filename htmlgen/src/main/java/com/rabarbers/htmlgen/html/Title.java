package com.rabarbers.htmlgen.html;

public class Title extends Element {

    public Title() {
        super((Element) null, "title");
    }

    public Title(Element parent) {
        super(parent, "title");
    }

    public Title(String text) {
        this();
        appendChildContent(text);
    }
}