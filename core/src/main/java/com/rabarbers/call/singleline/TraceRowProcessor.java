package com.rabarbers.call.singleline;

import com.rabarbers.call.RowTransformer;
import com.rabarbers.call.domain.row.Row;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public class TraceRowProcessor extends SingleLineProcessor<Row> {

    private Map<String, String> aliases;

    public TraceRowProcessor(File file, Map<String, String> aliases) {
        super(file);
        this.aliases = aliases;
    }

    public TraceRowProcessor(InputStream inputStream, Map<String, String> aliases) {
        super(inputStream);
        this.aliases = aliases;
    }

    @Override
    protected Row convert(String string) {
        return RowTransformer.convertToCallWithWhitespace(string, aliases);
    }
}
