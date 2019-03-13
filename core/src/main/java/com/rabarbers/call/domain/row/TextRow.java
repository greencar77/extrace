package com.rabarbers.call.domain.row;

public class TextRow extends Row {
    private String content;

    public TextRow(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "TextRow{" +
                "content='" + content + '\'' +
                '}';
    }
}
