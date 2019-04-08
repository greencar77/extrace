package com.rabarbers.call.html.svg;

import com.rabarbers.call.html.Element;
import com.rabarbers.call.html.page.HtmlPage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@RunWith(JUnit4.class)
public class SvgGenTest {

    private static final String OUTPUT_FOLDER = "target/";

    @Test
    public void simpleSvg() {
        HtmlPage root = new HtmlPage("a");

        Svg svg = new Svg(200, 100);
        root.getBody().appendChild(svg);

        Rect rect = new Rect(30, 30);
        rect.setCoord(5, 5);
        rect.setStyle("fill:blue;stroke:black;stroke-width:1");
        svg.appendChild(rect);

        writeFileWrapper("a.html", root);
    }

    protected void writeFileWrapper(String path, Element element) {
        try {
            File file = new File(OUTPUT_FOLDER + path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, element.getContent().toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
