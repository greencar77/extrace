package com.rabarbers.call;

import com.rabarbers.call.domain.Suite;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.pattern.Pattern;
import com.rabarbers.call.pattern.image.PatternImageProducer;
import com.rabarbers.call.publish.HtmlDomainPublisher;
import com.rabarbers.call.publish.TxtDomainPublisher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractApp {

    protected SuiteManager suiteManager;
    protected HtmlDomainPublisher htmlPublisher;
    private TxtDomainPublisher txtPublisher;

    private Map<Class<? extends Pattern>, PatternImageProducer<?>> patternImageProducerMap = new HashMap<>();

    protected Suite suite;

    public AbstractApp() {
        this(new SuiteManager(), new HtmlDomainPublisher(), new TxtDomainPublisher());
    }

    public AbstractApp(SuiteManager suiteManager, HtmlDomainPublisher htmlPublisher, TxtDomainPublisher txtPublisher) {
        this.suiteManager = suiteManager;
        this.htmlPublisher = htmlPublisher;
        this.txtPublisher = txtPublisher;
    }

    public void run() {
        createSuite();
        processPatterns();

        publishStandard();
        publishCustom();
    }

    protected void createSuite() {
        suite = new Suite();
        loadAliases();
        registerPatternProducers();
        suiteManager.loadForbidden(suite);

        suiteManager.appendTraceFromFolder(suite, "traces");
    }

    protected void loadAliases() {
    }

    protected void publishCustom() {
    }

    protected void registerPatternProducers() {
    }

    protected void registerPatternImageProducers() {
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
        htmlPublisher.publishTraceDetails(suite.getDomain(), null, patternImageProducerMap);
    }

    protected void processPatterns() {
        suite.getPatternProducers().forEach(prod -> {
            Set<Trace> traces = suite.getDomain().getTraces().stream().filter(prod::match).collect(Collectors.toSet());
            traces.forEach(t -> {
                Pattern pattern = prod.produce(t);
                if (prod.getPatterns().containsKey(pattern.getId())) {
                    pattern = prod.getPatterns().get(pattern.getId());
                } else {
                    prod.getPatterns().put(pattern.getId(), pattern);
                    pattern.setPatternProducer(prod);
                }
                pattern.getTraces().add(t);
                t.getPatterns().add(pattern);
            });
        });
    }

    protected void register(Class<? extends Pattern> classReg, PatternImageProducer<?> producer) {
        patternImageProducerMap.put(classReg, producer);
    }
}
