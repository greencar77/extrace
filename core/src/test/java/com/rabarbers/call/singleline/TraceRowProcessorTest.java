package com.rabarbers.call.singleline;

import com.rabarbers.call.domain.row.MethodRow;
import com.rabarbers.call.domain.row.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TraceRowProcessorTest {

    @Test
    public void convertToList_convertToListStream() {
        List<MethodRow> expected = new ArrayList<>();
        expected.add(new MethodRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new MethodRow(2, "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new MethodRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("  com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render").append("\n");
        sb.append("    com.rabarbers.verbalmodeler.sim.Publisher#printPage(int, com.rabarbers.verbalmodeler.sim.story.Story)").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));

        TraceRowProcessor traceRowProcessor = new TraceRowProcessor(is, null);
        List<Row> result = traceRowProcessor.readAll();

        assertEquals(expected, result);
    }

    @Test
    public void convertToList_convertToListFile() {
        List<MethodRow> expected = new ArrayList<>();
        expected.add(new MethodRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new MethodRow(2, "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new MethodRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        File file = new File("src/test/resources/calls.txt");

        TraceRowProcessor traceRowProcessor = new TraceRowProcessor(file, null);
        List<Row> result = traceRowProcessor.readAll();

        assertEquals(expected, result);
    }

    @Test
    public void convertToList_withBlanks() {
        List<Row> expected = new ArrayList<>();
        expected.add(new MethodRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        TraceRowProcessor traceRowProcessor = new TraceRowProcessor(is, null);
        List<Row> result = traceRowProcessor.readAll();

        assertEquals(expected, result);
    }

}