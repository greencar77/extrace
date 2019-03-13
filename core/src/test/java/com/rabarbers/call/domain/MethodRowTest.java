package com.rabarbers.call.domain;

import com.rabarbers.call.domain.row.MethodRow;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MethodRowTest extends TestCase {
    @Test
    public void testEquals() {
        MethodRow methodRow1 = new MethodRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()");
        MethodRow methodRow2 = new MethodRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()");
        assertEquals(methodRow1, methodRow1);
    }
}