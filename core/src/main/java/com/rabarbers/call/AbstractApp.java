package com.rabarbers.call;

import com.rabarbers.call.domain.Suite;
import com.rabarbers.call.publish.HtmlDomainPublisher;

public abstract class AbstractApp {

    protected SuiteManager suiteManager;
    protected HtmlDomainPublisher htmlPublisher;

    protected Suite suite;

    public AbstractApp(SuiteManager suiteManager, HtmlDomainPublisher htmlPublisher) {
        this.suiteManager = suiteManager;
        this.htmlPublisher = htmlPublisher;
    }

    public void run() {
        createSuite();

        publishStandard();
        publishCustom();
    }

    protected void createSuite() {
        suite = new Suite();
        loadAliases();
        suiteManager.loadForbidden(suite);

        suiteManager.appendTraceFromFolder(suite, "traces");
    }

    protected void loadAliases() {
    }

    protected void publishCustom() {
    }

    protected void publishStandard() {

        //txt
//        publisher.publishDomainClasses(suite.getDomain(), "domain_classes.txt");
//        publisher.publishDomainClassesWithMethods(suite.getDomain(), "domain_classes_methods.txt");
//        publisher.publishDomainClassesWithMethods(suite.getDomain(), "domain_classes_methods_uwm.txt", UWM_FILTER);
//        publisher.publishDomainClassDetails(suite.getDomain(), null); //UWM_FILTER

        //html
        htmlPublisher.addCommonResource("trace.js", "trace.js");
        htmlPublisher.addCommonResource("main.css", "main.css");
        htmlPublisher.addCommonResource("favicon_t.ico", "favicon_t.ico");

        htmlPublisher.publishClassList(suite.getDomain(), "classes.html", null);
        htmlPublisher.publishDomainClassDetails(suite.getDomain(), null);
        htmlPublisher.publishTraceDetails(suite.getDomain(), null);
    }
}
