package com.rabarbers.call.singleline;

import java.io.File;
import java.io.InputStream;

public class StringProcessor extends SingleLineProcessor<String> {

    private static final String COMMENT_MARK = "#";

    public StringProcessor(File file) {
        super(file);
    }

    public StringProcessor(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    protected String convert(String string) {
        if (string.startsWith(COMMENT_MARK)) {
            return null;
        }
        return string;
    }
}
