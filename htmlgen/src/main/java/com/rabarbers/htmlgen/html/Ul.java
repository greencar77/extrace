package com.rabarbers.htmlgen.html;

public class Ul extends Element {
    public Ul() {
        super("ul");
    }

    public Li createLi() {
        return new Li();
    }
}