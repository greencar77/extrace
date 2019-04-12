package com.rabarbers.call.html;

public class InnerHtml extends Element {

    public InnerHtml() {
        super(null);
    }

    public InnerHtml(StringBuilder stringBuilder) {
        this();
        appendChildContent(stringBuilder);
    }

    @Override
    public StringBuilder getContent() {
        return getChildContent();
    }
}