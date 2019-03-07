package com.rabarbers.call.domain;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CallRowTest extends TestCase {
    @Test
    public void testEquals() {
        CallRow callRow1 = new CallRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()");
        CallRow callRow2 = new CallRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()");
        assertEquals(callRow1, callRow1);
    }
}