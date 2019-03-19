package com.rabarbers.call.publish;

import com.rabarbers.call.domain.ClassX;

public abstract class HtmlPublisher extends Publisher {

    private static final String HTML_FOLDER = "html/";
    public static final String BR = "<br/>\n";

    protected String classLink(ClassX classX, String caption, String backtrack) {
        return "<a href=\""
                + (backtrack == null? "": backtrack)
                + "classes/"
                + toPath(classX.getPackageX()) + "/" + classX.getName() + ".html" + "\">" + caption + "</a>";
    }

    @Override
    protected String getPublisherFolder() {
        return HTML_FOLDER;
    }
}
