package com.rabarbers.call;

import com.rabarbers.call.domain.Suite;
import com.rabarbers.call.domain.Trace;
import com.rabarbers.call.domain.call.Statement;
import com.rabarbers.call.domain.call.StubCall;

import java.util.Stack;

public class GraphManager {

    public void extractCallTree(Trace trace) {

        Statement root = new StubCall(-1, "root");
        trace.setRootStatement(root);

        Stack<Statement> stack = new Stack();
        stack.push(root);
        trace.getCalls().forEach(c -> {
            if (c.getDepth() + 2 > stack.size()) {
                if (c.getDepth() + 2 != stack.size() + 1) {
                    throw new RuntimeException("Trace: " + trace.getName() + " " + c.toString());
                }
            } else if (c.getDepth() + 2 == stack.size()) { //sibling
                stack.pop();
            } else {
                //backtrack
                for (int i = stack.size(); i != c.getDepth() + 2; i--) {
                    stack.pop();
                }
            }

            Statement ancestor = stack.peek();
            stack.push(c);
            bind(ancestor, c);
        });
    }

    private void bind(Statement parent, Statement child) {
        parent.getChildren().add(child);
        child.setParent(parent);
    }
}
