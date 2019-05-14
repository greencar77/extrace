package com.rabarbers.call;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.domain.Suite;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.Statement;
import com.rabarbers.call.domain.call.StubCall;
import com.rabarbers.call.domain.row.MethodRow;
import com.rabarbers.call.domain.row.Row;
import com.rabarbers.call.domain.row.TextRow;
import com.rabarbers.call.filter.ClassFilter;
import com.rabarbers.call.singleline.SingleLineProcessor;
import com.rabarbers.call.singleline.StringProcessor;
import com.rabarbers.call.singleline.TraceRowProcessor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rabarbers.call.publish.Publisher.DATA_FOLDER;

public class SuiteManager {

    private GraphManager graphManager = new GraphManager();

    public void appendTrace(Suite suite, File file) {
        System.out.println("Append trace: " + file.getAbsoluteFile());
        SingleLineProcessor reader = new TraceRowProcessor(file, suite.getAliases());
        List<Row> rows = reader.readAll();
        suite.getDomain().getTraces().add(createTrace(suite, file.getName(), rows));
    }

    private Trace createTrace(Suite suite, String name, List<Row> callRows) {
        Map<String, ClassX> domainClasses = suite.getDomain().getClasses();
        Map<String, MethodX> domainMethods = suite.getDomain().getMethods();

        Trace result = new Trace(name.substring(0, name.lastIndexOf(".")));

        List<Statement> statements = callRows.stream()
                .filter(c -> c != null)
                .map(call -> {
                    if (call instanceof MethodRow) {
                        MethodRow methodRow = (MethodRow) call;
                        ClassX classX = registerClass(domainClasses, result, methodRow);
                        if (suite.getForbiddenClasses().contains(classX.getFullName())) {
                            throw new RuntimeException("Forbidden class: " + classX.getFullName() + " trace: " + result.getName());
                        }
                        MethodX methodX = registerMethod(domainMethods, result, classX, methodRow);
                        return new Call(call.getDepth(), methodX);
                    } else if (call instanceof TextRow) {
                        return new StubCall(call.getDepth(), ((TextRow) call).getContent());
                    } else {
                        throw new RuntimeException(call.getClass().getCanonicalName());
                    }
                })
                .collect(Collectors.toList());

        result.setStatements(statements);
        result.setCallCount(TraceUtils.calculateCallCount(statements));

        graphManager.extractCallTree(result);

        return result;
    }

    private ClassX registerClass(Map<String, ClassX> domainClasses, Trace trace, MethodRow call) {
        ClassX resultClass;
        if (!domainClasses.containsKey(call.getClassFullName())) {
            resultClass = new ClassX(call);
            domainClasses.put(resultClass.getFullName(), resultClass);
        } else {
            resultClass = domainClasses.get(call.getClassFullName());
        }
        bind(trace, resultClass);

        return resultClass;
    }

    private MethodX registerMethod(Map<String, MethodX> domainMethods, Trace trace, ClassX classX, MethodRow call) {
        MethodX resultMethod;
        if (!domainMethods.containsKey(call.getMethodGlobalId())) {
            resultMethod = new MethodX(call);
            domainMethods.put(call.getMethodGlobalId(), resultMethod);
            bind(resultMethod, classX);
        } else {
            resultMethod = domainMethods.get(call.getMethodGlobalId());
        }
        bind(trace, resultMethod);

        //method local
        if (!classX.getMethods().containsKey(call.getMethodLocalId())) {
            classX.getMethods().put(resultMethod.getMethodLocalId(), resultMethod);
        }

        return resultMethod;
    }

    public void appendTraceFromFolder(Suite suite, String folderPath) {
        appendTraceFromFolder(suite, new File(DATA_FOLDER + folderPath));
    }

    public void appendTraceFromFolder(Suite suite, File file) {
        Arrays.stream(file.listFiles())
                .filter(f -> !f.isDirectory())
                .forEach(f -> {
                    appendTrace(suite, f);
                });
    }

    private void bind(Trace trace, ClassX classX) {
        trace.getClasses().add(classX);
        classX.getTraces().add(trace);
    }

    private void bind(Trace trace, MethodX methodX) {
        trace.getMethods().add(methodX);
        methodX.getTraces().add(trace);
    }

    private void bind(MethodX methodX, ClassX classX) {
        methodX.setClassX(classX);
    }

    public void loadForbidden(Suite suite) {
        StringProcessor stringProcessor = new StringProcessor(new File(DATA_FOLDER + "input/interface.txt"));
        Set<String> forbiddenClasses = new HashSet<>();
        List<String> forbidden = stringProcessor.readAll();
        if (forbidden != null) {
            forbiddenClasses.addAll(forbidden);
        }
        suite.setForbiddenClasses(forbiddenClasses);
    }

    public List<ClassX> filter(Collection<ClassX> classes, ClassFilter filter) {
        return classes.stream()
                .filter(filter::match)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<ClassX> filter(Collection<ClassX> classes, ClassFilter... filterArr) {
        Collection<ClassX> portion = classes;
        for (ClassFilter filter: filterArr) {
            portion = portion.stream()
                    .filter(filter::match)
                    .sorted()
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(portion);
    }

    public Pair<List<ClassX>,List<ClassX>> split(Collection<ClassX> classes, ClassFilter... filterArr) {
        Collection<ClassX> portion = filter(classes, filterArr);

        List<ClassX> tail = new ArrayList<>(classes);
        tail.removeAll(portion);

        return Pair.of(new ArrayList<>(portion), tail);
    }
}
