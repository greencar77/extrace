package com.rabarbers.htmlgen;

import com.rabarbers.htmlgen.html.Element;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class CustomFileUtils {

    public static void writeFileWrapper(String path, StringBuilder stringBuilder) {
        try {
            File file = new File(path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, stringBuilder.toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFileWrapper(String path, Element element) {
        try {
            File file = new File(path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, element.getContent().toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
