package com.rabarbers.htmlgen.html.widget.timeline;

import com.rabarbers.htmlgen.html.Br;
import com.rabarbers.htmlgen.html.Element;
import com.rabarbers.htmlgen.html.page.HtmlPage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@RunWith(JUnit4.class)
public class TimelineTest {

    private static final String OUTPUT_FOLDER = "target/";

    @Test
    public void test() {
        Period period = new Period(100, 30, 50);

        testElement(period);
    }

    @Test
    public void testMultiple() {
        String name = "multiple";
        HtmlPage root = new HtmlPage(name);

        root.getBody().appendChild(new Period(100, 20, 50));
        root.getBody().appendChild(new Br());
        root.getBody().appendChild(new Br());
        root.getBody().appendChild(new Period(100, 20, 0));
        root.getBody().appendChild(new Br());
        root.getBody().appendChild(new Br());
        root.getBody().appendChild(new Period(100, 20, 100));
        root.getBody().appendChild(new Br());
        root.getBody().appendChild(new Br());
        root.getBody().appendChild(new Period(100, 20, 10));
        root.getBody().appendChild(new Br());
        root.getBody().appendChild(new Br());

        writeFileWrapper(name + ".html", root);
    }

    private void testElement(Element element) {
        String name = element.getClass().getSimpleName();
        HtmlPage root = new HtmlPage(name);

        root.getBody().appendChild(element);

        writeFileWrapper(name + ".html", root);
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
