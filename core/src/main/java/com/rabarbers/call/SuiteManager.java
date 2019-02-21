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

    public void appendTraceFromFolder(Suite suite, File file) {
        Arrays.stream(file.listFiles())
                .filter(f -> !f.isDirectory())
                .forEach(f -> {
                    appendTrace(suite, f);
                });
    }

    public void extractDomain(Suite suite) {
        Map<String, ClassX> classes = suite.getDomain().getClasses();

        suite.getTraces()
                .forEach(trace -> {
                    trace.getCalls().stream()
                            .filter(c -> c != null)
                            .forEach(call -> {
                                ClassX classX;
                                if (!classes.containsKey(call.getClassFullName())) {
                                    classX = new ClassX(call);
                                    classes.put(classX.getFullName(), classX);
                                } else {
                                    classX = classes.get(call.getClassFullName());
                                }

                                if (!classX.getMethods().containsKey(call.getMethodLocalId())) {
                                    MethodX methodX = new MethodX(call);
                                    classX.getMethods().put(methodX.getMethodLocalId(), methodX);
                                }
                            });
                });

    }
}
