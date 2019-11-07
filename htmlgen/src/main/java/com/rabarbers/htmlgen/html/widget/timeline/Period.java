package com.rabarbers.htmlgen.html.widget.timeline;

import com.rabarbers.htmlgen.html.svg.Rect;
import com.rabarbers.htmlgen.html.svg.Svg;

public class Period extends Svg {

//    private int width = 100;
//    private int height = 30;

    public Period() {}

    public Period(int width, int height, int percentage) {
        init(width, height, percentage);
    }

    public Period(int width, int height) {
        init(width, height, 0);
    }

    protected void init(int width, int height, int percentage) {
        setWidth(width);
        setHeight(height);

        Rect rect = new Rect(width, height);
        rect.setCoord(0, 0);
        rect.setStyle("fill:antiquewhite;stroke:black;stroke-width:1");
        appendChild(rect);

        int headWidth = percentage * (width - 2) / 100; //exclude borders

        Rect rectHead = new Rect(headWidth, height - 2); //exclude borders
        rectHead.setCoord(1, 1);
        rectHead.setStyle("fill:coral");
        appendChild(rectHead);
    }
}
