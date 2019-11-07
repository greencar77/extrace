package com.rabarbers.htmlgen.html.svg;

import com.rabarbers.htmlgen.html.Br;
import com.rabarbers.htmlgen.html.Element;
import com.rabarbers.htmlgen.html.InnerHtml;
import com.rabarbers.htmlgen.html.Table;
import com.rabarbers.htmlgen.html.page.HtmlPage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@RunWith(JUnit4.class)
public class MiscTest {

    private static final String OUTPUT_FOLDER = "target/";

    @Test
    public void simple() {
        HtmlPage root = new HtmlPage("a");

        Table table = new Table(root.getBody());
        root.getBody().appendChild(new InnerHtml("eee"));
        root.getBody().appendChild(new Br());

        writeFileWrapper("miscSimple.html", root);
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

