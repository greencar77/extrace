package com.rabarbers.call.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterListBuilder {
    private List<Item> items = new ArrayList<>();

    public FilterListBuilder append(String title, ClassFilter filter) {
        items.add(new Item(title, filter));
        return this;
    }

    public List<Item> build() {
        return items;
    }

    public static class Item {
        private String title;
        private ClassFilter filter;

        public Item(String title, ClassFilter filter) {
            this.title = title;
            this.filter = filter;
        }

        public String getTitle() {
            return title;
        }

        public ClassFilter getFilter() {
            return filter;
        }
    }
}
