package com.rabarbers.call.domain.call;

public class StubCall extends Statement {
    private String content;

    public StubCall(int depth, String content) {
        super(depth);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "StubCall{" +
                "content='" + content + '\'' +
                '}';
    }
}
