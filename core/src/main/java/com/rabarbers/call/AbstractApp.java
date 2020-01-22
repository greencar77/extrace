package com.rabarbers.call;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.Suite;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.Statement;
import com.rabarbers.call.domain.call.StatementAttributeName;
import com.rabarbers.call.pattern.Pattern;
import com.rabarbers.call.pattern.image.PatternImageProducer;
import com.rabarbers.call.publish.HtmlDomainPublisher;
import com.rabarbers.call.publish.TxtDomainPublisher;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rabarbers.call.publish.Publisher.DATA_FOLDER;

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
        deriveTraces();
        addAccessAttributes();
        loadClassHierarchies();
        processPatterns();
        loadScenarios();

        publishStandard();
        publishCustom();
    }

    protected void createSuite() {
        suite = new Suite();
        loadClassInfo();
        loadAliases();
        registerPatternProducers();
        registerPatternImageProducers();
        suiteManager.loadForbidden(suite);

        suiteManager.appendTraceFromFolder(suite, "traces");
    }

    private void loadClassInfo() {
        Map<String, ClassX> domainClassMap = suite.getDomain().getClasses();

        getClassNames().forEach(cn -> {
            suiteManager.registerClass(domainClassMap, cn);
                }
        );
    }

    protected abstract List<String> getClassNames();

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
            System.out.println(prod.getClass().getSimpleName() + " " + prod.getPatterns().size());
        });
    }

    protected void register(Class<? extends Pattern> classReg, PatternImageProducer<?> producer) {
        patternImageProducerMap.put(classReg, producer);
    }

    protected void deriveTraces() {
    }

    protected void loadClassHierarchies() {
        File hierarchiesFolder = new File(DATA_FOLDER + "input/class_hierarchies");
        if (hierarchiesFolder.exists()) {
            Arrays.stream(hierarchiesFolder.listFiles())
                    .filter(f -> !f.isDirectory())
                    .forEach(hf -> {
                        appendHierarchy(suite, hf);
                    });
        }
    }

    private void appendHierarchy(Suite suite, File hierarchyFile) {
        //TODO
    }

    protected void addAccessAttributes() {
        suite.getDomain().getTraces().stream()
                .forEach(t -> addAccessAttributes(t));
    }

    protected void addAccessAttributes(Trace trace) {
        trace.getRootStatement().getChildren().stream()
                .forEach(child -> traverse(trace.getRootStatement(), child));
    }

    private void traverse(Statement statementUpper, Statement statementLower) {
        if (statementUpper instanceof Call && statementLower instanceof Call) {
            Call upper = (Call) statementUpper;
            Call lower = (Call) statementLower;
            if (!upper.getMethod().getClassX().equals(lower.getMethod().getClassX())) {
                lower.getMethod().addAttributeValue(StatementAttributeName.INTERCLASS.name(), "true");
            }
        }

        statementLower.getChildren().stream()
                .forEach(child -> traverse(statementLower, child));
    }

    protected void loadScenarios() {
    }
}
