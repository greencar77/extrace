package com.rabarbers.call.domain.scenario;

import java.util.ArrayList;
import java.util.List;

public abstract class Scenario {
    private String id;
    private String title;
    private List<AbstractStep> steps = new ArrayList<>();

    public Scenario(String id, String title) {
        this.id = id;
        this.title = title;

        define();
    }

    public List<AbstractStep> getSteps() {
        return steps;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    protected abstract void define();

    protected void add(AbstractStep step) {
        steps.add(step);
    }

    protected void addMethodStep(String signature) {
        MethodStep step = new MethodStep();
        steps.add(step);
    }
}
