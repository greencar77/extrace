package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public abstract class HtmlPublisher extends Publisher {

    protected static final String COMMON_FOLDER = "common/";
    public static final String HTML_FOLDER = "html/";
    public static final String BR = "<br/>\n";

    protected String classLink(ClassX classX, String caption, String backtrack) {
        return "<a class=\"callclass\" href=\""
                + (backtrack == null? "": backtrack)
                + "classes/"
                + toPath(classX.getPackageX()) + "/" + classX.getName() + ".html" + "\">" + caption + "</a>";
    }

    @Override
    protected String getPublisherFolder() {
        return HTML_FOLDER;
    }

    public void addCommonResource(String sourcePath, String targetPath) {
        try {
            File targetFile = new File(OUTPUT_FOLDER + getPublisherFolder() + COMMON_FOLDER + targetPath);
            System.out.println("Copy resource: " + sourcePath + " - " + targetFile.getAbsolutePath());
            FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream(sourcePath),
                    targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
