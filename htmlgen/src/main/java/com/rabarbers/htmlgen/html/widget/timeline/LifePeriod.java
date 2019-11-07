package com.rabarbers.htmlgen.html.widget.timeline;

import com.rabarbers.htmlgen.html.svg.Rect;

public class LifePeriod extends Period {
    public LifePeriod(int width, int height, int lifeAge, int age) {

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
