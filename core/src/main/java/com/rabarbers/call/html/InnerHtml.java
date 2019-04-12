package com.rabarbers.call.html;

public class InnerHtml extends Element {

    public InnerHtml() {
        super(null);
    }

    @Override
    public StringBuilder getContent() {
        return getChildContent();
    }
}