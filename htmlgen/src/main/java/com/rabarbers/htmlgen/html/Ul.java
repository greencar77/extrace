package com.rabarbers.htmlgen.html;

public class Ul extends Element {
    public Ul() {
        super((Element) null, "ul");
    }

    public Ul(Element parent) {
        super(parent, "ul");
    }

    public Li createLi() {
        return new Li(this);
    }
}