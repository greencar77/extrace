package com.rabarbers.call.publish;

import com.rabarbers.call.html.Node;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Publisher {
    public static final String DATA_FOLDER = "data/";
    public static final String OUTPUT_FOLDER = DATA_FOLDER + "output/";

    protected void writeFileWrapper(String path, StringBuilder stringBuilder) {
        try {
            File file = new File(OUTPUT_FOLDER + path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, stringBuilder.toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeFileWrapper(String path, Node node) {
        try {
            File file = new File(OUTPUT_FOLDER + path);
            System.out.println("Output file: " + file.getAbsolutePath());
            FileUtils.writeByteArrayToFile(file, node.getContent().toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static final String toPath(String packageX) {
        return packageX.replaceAll("\\.", "/");
    }
}
