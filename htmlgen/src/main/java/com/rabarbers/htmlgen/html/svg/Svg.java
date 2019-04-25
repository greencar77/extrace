package com.rabarbers.htmlgen.html.svg;

import com.rabarbers.htmlgen.html.Element;

public class Svg extends Element {

    private int width;
    private int height;

    public Svg() {
        super("svg");
    }

    public Svg(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int width) {
        this.width = width;
        setAttr("width", width);
    }

    public void setHeight(int height) {
        this.height = height;
        setAttr("height", height);
    }
}
