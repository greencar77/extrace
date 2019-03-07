package com.rabarbers.call.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Suite {
    public static final int TAB_SIZE = 2;

    private Domain domain = new Domain();
    private Map<String, String> aliases = new HashMap<>();

    public Domain getDomain() {
        return domain;
    }

    public Map<String, String> getAliases() {
        return aliases;
    }
}
