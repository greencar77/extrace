package com.rabarbers.extrace.kbweld;

import com.rabarbers.call.AbstractApp;
import com.rabarbers.call.RowTransformer;

public class App extends AbstractApp {

    public static void main(String[] args) {
        new App().run();
    }

    @Override
    protected void loadAliases() {
        suite.getAliases().put("stream", RowTransformer.UNKNOWN);
    }

    @Override
    protected void publishCustom() {
    }

    @Override
    protected void registerPatternProducers() {
    }

    @Override
    protected void registerPatternImageProducers() {
    }
}
