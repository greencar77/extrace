package com.rabarbers.call.pattern;

import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Call;

public class PatternUtils {
    public static boolean containsClass(Trace trace, String classShortName) {
        return trace.getClasses().stream().filter(c -> c.getName().equals(classShortName)).findAny().isPresent();
    }

    public static boolean containsMethodTransition(Trace trace, String methodFrom, String methodTo) {
        return trace.getCalls().stream()
                .filter(c -> c instanceof Call && ((Call) c).getMethod().getMethodGlobalId().equals(methodTo))
                .findAny()
                .isPresent();
    }


    public static boolean containsMethodTransition(Call topCall, String methodFrom, String methodTo) {
        if (topCall.getMethod().getMethodGlobalId().equals(methodFrom)) {
            return topCall.getChildren().stream()
                    .filter(c -> c instanceof Call && ((Call) c).getMethod().getMethodGlobalId().equals(methodTo))
                    .findAny()
                    .isPresent();
        } else {
            return topCall.getChildren().stream()
                    .filter(c -> c instanceof Call && containsMethodTransition((Call) c, methodFrom, methodTo))
                    .findAny()
                    .isPresent();
        }
    }
}
