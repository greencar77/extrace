package com.rabarbers.call.html.svg;

import com.rabarbers.call.html.Element;

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
