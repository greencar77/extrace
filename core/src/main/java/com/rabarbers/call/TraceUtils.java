package com.rabarbers.call;

import com.rabarbers.call.domain.ClassX;
import com.rabarbers.call.domain.MethodX;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;
import com.rabarbers.call.domain.call.Statement;
import com.rabarbers.call.pattern.Pattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TraceUtils {
    public static Trace copy(Trace trace) {
        Trace result = new Trace(trace.getName() + "-copy");

        //TODO
//        private List<Statement> calls;
//        private Statement rootStatement;

//        private Set<ClassX> classes = new HashSet<>();
//        private Set<MethodX> methods = new HashSet<>();

        result.setCallCount(calculateCallCount(trace.getStatements()));

        return result;
    }

    public static int calculateCallCount(List<Statement> statements) {
        return (int) statements.stream().filter(c -> c instanceof Call).count();
    }
}
