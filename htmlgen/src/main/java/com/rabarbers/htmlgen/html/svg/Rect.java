package com.rabarbers.htmlgen.html.svg;

import com.rabarbers.htmlgen.html.Element;

public class Rect extends Element {

    private int x;
    private int y;
    private int width;
    private int height;

    public Rect() {
        super((Element) null, "rect");
    }

    public Rect(Element parent) {
        super(parent, "rect");
    }

    public Rect(int width, int height) {
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

    public void setX(int x) {
        this.x = x;
        setAttr("x", x);
    }

    public void setY(int y) {
        this.y = y;
        setAttr("y", y);
    }

    public void setCoord(int x, int y) {
        setX(x);
        setY(y);
    }
}