package com.rabarbers.htmlgen.html;

import java.util.List;

public class Table extends Element {
    public Table() {
        super("table");
    }

    public void addHeader(List<String> header) {
        Tr tr = new Tr();
        header.forEach(h -> {
            Th cell = new Th();
            cell.appendChildContent(h);
            tr.appendChild(cell);
        });
        appendChild(tr);
    }

    public void addRow(List<Object> row) {
        Tr tr = new Tr();
        row.forEach(h -> {
            Td cell = new Td();
            if (h instanceof String) {
                cell.appendChildContent((String) h);
            } else if (h instanceof Element) {
                cell.appendChild((Element) h);
            } else {
                throw new RuntimeException(h.getClass().getCanonicalName());
            }
            tr.appendChild(cell);
        });
        appendChild(tr);
    }
}