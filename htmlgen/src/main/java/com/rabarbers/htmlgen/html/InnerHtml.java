package com.rabarbers.htmlgen.html;

public class InnerHtml extends Element {

    public InnerHtml() {
        super((Element) null, null);
    }

    public InnerHtml(Element parent) {
        super(parent, null);
    }

    public InnerHtml(StringBuilder stringBuilder) {
        this();
        appendChildContent(stringBuilder);
    }

    public InnerHtml(String string) {
        this();
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        appendChildContent(sb);
    }

    @Override
    public StringBuilder getContent() {
        return getChildContent();
    }
}