package com.rabarbers.call;

import com.rabarbers.call.domain.Call;
import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.domain.Suite;
import com.rabarbers.call.domain.Trace;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SuiteManager {
    public void appendTrace(Suite suite, File file) {
        System.out.println("Append trace: " + file.getAbsoluteFile());
        List<Call> calls = CallTransformer.convertToList(file, suite.getAliases());
        suite.getTraces().add(new Trace(file.getName(), calls));
    }

    public void appendTraceFromFolder(Suite suite, String folderPath) {
        appendTraceFromFolder(suite, new File(folderPath));
    }

    public void appendTraceFromFolder(Suite suite, File file) {
        Arrays.stream(file.listFiles())
                .filter(f -> !f.isDirectory())
                .forEach(f -> {
                    appendTrace(suite, f);
                });
    }

    public void extractDomain(Suite suite) {
        Map<String, ClassX> classes = suite.getDomain().getClasses();
        Map<String, MethodX> methods = suite.getDomain().getMethods();

        suite.getTraces()
                .forEach(trace -> {
                    trace.getCalls().stream()
                            .filter(c -> c != null)
                            .forEach(call -> {
                                //class global
                                ClassX classX;
                                if (!classes.containsKey(call.getClassFullName())) {
                                    classX = new ClassX(call);
                                    classes.put(classX.getFullName(), classX);
                                } else {
                                    classX = classes.get(call.getClassFullName());
                                }
                                bind(trace, classX);

                                //method global
                                MethodX methodX;
                                if (!methods.containsKey(call.getMethodGlobalId())) {
                                    methodX = new MethodX(call);
                                    methods.put(call.getMethodGlobalId(), methodX);
                                } else {
                                    methodX = methods.get(call.getMethodGlobalId());
                                }
                                bind(trace, methodX);

                                //method local
                                if (!classX.getMethods().containsKey(call.getMethodLocalId())) {
                                    classX.getMethods().put(methodX.getMethodLocalId(), methodX);
                                }
                            });
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
}
