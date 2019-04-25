package com.rabarbers.htmlgen.html.page;

import com.rabarbers.htmlgen.html.Body;
import com.rabarbers.htmlgen.html.Head;
import com.rabarbers.htmlgen.html.Html;
import com.rabarbers.htmlgen.html.Link;
import com.rabarbers.htmlgen.html.Title;

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

    public void addCssLink(String href) {
        getHead().appendChild(createCssLink(href));
    }

    public Link createCssLink(String href) {
        Link link = new Link();
        link.setAttr("rel", "stylesheet");
        link.setAttr("type", "text/css");
        link.setAttr("href", href);
        return link;
    }
}
