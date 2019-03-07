package com.rabarbers.call;

import com.rabarbers.call.domain.CallRow;
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
        List<CallRow> callRows = CallTransformer.convertToList(file, suite.getAliases());
        suite.getTraces().add(new Trace(file.getName(), callRows));
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
        Map<String, ClassX> domainClasses = suite.getDomain().getClasses();
        Map<String, MethodX> domainMethods = suite.getDomain().getMethods();

        suite.getTraces()
                .forEach(trace -> {
                    trace.getCallRows().stream()
                            .filter(c -> c != null)
                            .forEach(call -> {
                                //class global
                                ClassX classX;
                                if (!domainClasses.containsKey(call.getClassFullName())) {
                                    classX = new ClassX(call);
                                    domainClasses.put(classX.getFullName(), classX);
                                } else {
                                    classX = domainClasses.get(call.getClassFullName());
                                }
                                bind(trace, classX);

                                //method global
                                MethodX methodX;
                                if (!domainMethods.containsKey(call.getMethodGlobalId())) {
                                    methodX = new MethodX(call);
                                    domainMethods.put(call.getMethodGlobalId(), methodX);
                                    bind(methodX, classX);
                                } else {
                                    methodX = domainMethods.get(call.getMethodGlobalId());
                                }
                                bind(trace, methodX);
                                bind(call, methodX);

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

    private void bind(CallRow callRow, MethodX methodX) {
        callRow.setMethodX(methodX);
    }

    private void bind(MethodX methodX, ClassX classX) {
        methodX.setClassX(classX);
    }
}
