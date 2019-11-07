package com.rabarbers.htmlgen.html;

import com.rabarbers.htmlgen.html.page.HtmlPage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class TableTest {

    private static final String OUTPUT_FOLDER = "target/";

    @Test
    public void simple() {
        HtmlPage root = new HtmlPage("a");

        Table table = new Table(root.getBody());

        Td td1 = new Td(table);
        td1.appendChildContent("aaa");
        Td td2 = new Td(table);
        td2.appendChildContent("bbb");
        List<Object> row = new ArrayList<>();
        row.add("");
        row.add(td2);
        table.addRow(row);

        writeFileWrapper("table.html", root);
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
