package com.rabarbers.call.html.page;

import com.rabarbers.call.html.Body;
import com.rabarbers.call.html.Head;
import com.rabarbers.call.html.Html;
import com.rabarbers.call.html.Title;

import java.util.ArrayList;

public class HtmlPage extends Html {
    private Head head;
    private Body body;

    public HtmlPage(String title) {
        this();
        head.appendChild(new Title(title));
    }

    public HtmlPage() {
        this.addHead(new Head());
        this.addBody(new Body());
    }

    public Body getBody() {
        return body;
    }

    public Head getHead() {
        return head;
    }

    public void addHead(Head head) {
        this.head = head;
        this.children.add(head);
    }

    public void addBody(Body body) {
        this.body = body;
        this.children.add(body);
    }
}