package com.rabarbers.call.domain;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CallTest extends TestCase {
    @Test
    public void testEquals() {
        Call call1 = new Call(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()");
        Call call2 = new Call(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()");
        assertEquals(call1, call1);
    }
}