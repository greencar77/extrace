package com.rabarbers.call;

import com.rabarbers.call.domain.CallRow;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class CallRowTransformerTest extends TestCase {

    @Test
    public void convertToCallTrimmed_simple() {
        CallRow callRow = CallTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", callRow.getPackageName());
        assertEquals("HeapRenderer", callRow.getClassName());
        assertEquals("render", callRow.getMethodName());
        assertEquals("()", callRow.getSignatureX());
    }

    @Test
    public void convertToCallTrimmed_alias() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("stream", "...");
        aliases.put("class_init", "...");
        CallRow callRow = CallTransformer.convertToCallTrimmed("stream", aliases);

        assertNull(callRow);
    }

    @Test
    public void convertToCallTrimmed_simpleConstructorFromClass() {
        //copied from class
        CallRow callRow = CallTransformer.convertToCallTrimmed("com.rabarbers.callRow.Alpha.Alpha", null);

        assertEquals("com.rabarbers.callRow", callRow.getPackageName());
        assertEquals("Alpha", callRow.getClassName());
        assertEquals("Alpha", callRow.getMethodName());
        assertEquals("()", callRow.getSignatureX());
    }


    @Test
    public void convertToCallTrimmed_simpleConstructorFromInvocation() {
        //copied from class (for example, from Beta)
        CallRow callRow = CallTransformer.convertToCallTrimmed("com.rabarbers.callRow.Alpha", null);

        assertEquals("com.rabarbers.callRow", callRow.getPackageName());
        assertEquals("Alpha", callRow.getClassName());
        assertEquals("Alpha", callRow.getMethodName());
        assertEquals("()", callRow.getSignatureX());
    }


    @Test
    public void convertToCallTrimmed_simpleRightPadded() {
        CallRow callRow = CallTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render    ", null);

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", callRow.getPackageName());
        assertEquals("HeapRenderer", callRow.getClassName());
        assertEquals("render", callRow.getMethodName());
        assertEquals("()", callRow.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indent() {
        CallRow callRow = CallTransformer.convertToCallWithWhitespace("    com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals(2, callRow.getDepth());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", callRow.getPackageName());
        assertEquals("HeapRenderer", callRow.getClassName());
        assertEquals("render", callRow.getMethodName());
        assertEquals("()", callRow.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indentOnlyWhitespace() {
        CallRow callRow = CallTransformer.convertToCallWithWhitespace("    ", null);
        assertNull(callRow);
    }

    @Test
    public void convertToCallWithWhitespace_indentTabs() {
        CallRow callRow = CallTransformer.convertToCallWithWhitespace("\tcom.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals(1, callRow.getDepth());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", callRow.getPackageName());
        assertEquals("HeapRenderer", callRow.getClassName());
        assertEquals("render", callRow.getMethodName());
        assertEquals("()", callRow.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indentMixedTabsWithSpaces() {
        CallRow callRow = CallTransformer.convertToCallWithWhitespace("\t\t\t\t\tcom.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals(5, callRow.getDepth());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", callRow.getPackageName());
        assertEquals("HeapRenderer", callRow.getClassName());
        assertEquals("render", callRow.getMethodName());
        assertEquals("()", callRow.getSignatureX());
    }

    @Test
    public void convertToList_convertToListStream() {
        List<CallRow> expected = new ArrayList<>();
        expected.add(new CallRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new CallRow(2, "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new CallRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("  com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render").append("\n");
        sb.append("    com.rabarbers.verbalmodeler.sim.Publisher#printPage(int, com.rabarbers.verbalmodeler.sim.story.Story)").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        List<CallRow> result = CallTransformer.convertToList(is, null);

        assertEquals(expected, result);
    }

    @Test
    public void convertToList_convertToListFile() {
        List<CallRow> expected = new ArrayList<>();
        expected.add(new CallRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new CallRow(2, "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new CallRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        File file = new File("src/test/resources/calls.txt");

        List<CallRow> result = CallTransformer.convertToList(file, null);

        assertEquals(expected, result);
    }

    @Test
    public void convertToFile_fileToFile() {
        File file = new File("src/test/resources/calls.txt");

        File result = CallTransformer.convertToFile(file, "target/calls_converted.txt", null);

        String expected;
        String actual;
        try {
            expected = FileUtils.readFileToString(new File("src/test/resources/calls_converted.txt"));
            actual = FileUtils.readFileToString(new File("target/calls_converted.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void convertToFile_fileToFileWithEmpty() {
        File file = new File("src/test/resources/calls_empty.txt");

        File result = CallTransformer.convertToFile(file, "target/calls_empty_converted.txt", null);

        String expected;
        String actual;
        try {
            expected = FileUtils.readFileToString(new File("src/test/resources/calls_empty_converted.txt"));
            actual = FileUtils.readFileToString(new File("target/calls_empty_converted.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void convertToCallTrimmed_emptyLine() {
        CallRow callRow = CallTransformer.convertToCallTrimmed("", null);
        assertNull(callRow);
    }

    @Test
    public void convertToCallTrimmed_unknown() {
        CallRow callRow = CallTransformer.convertToCallTrimmed("...", null);
        assertNull(callRow);
    }

    @Test
    public void convertToCallTrimmed_unknownPadded() {
        CallRow callRow = CallTransformer.convertToCallTrimmed("  ...  ", null);
        assertNull(callRow);
    }

    @Test
    public void convertToCallWithWhitespace_convertToCallWithWhitespace_unknownPaddedTabs() {
        CallRow callRow = CallTransformer.convertToCallWithWhitespace("\t...\t\t\t\t", null);
        assertNull(callRow);
    }

    @Test
    public void convertToCallWithWhitespace_unknownPaddedTabsStart() {
        CallRow callRow = CallTransformer.convertToCallWithWhitespace("\t...", null);
        assertNull(callRow);
    }

    @Test
    public void convertToCallTrimmed_commentMark() {
        CallRow callRow = CallTransformer.convertToCallTrimmed("    //aa", null);
        assertNull(callRow);
    }

    @Test
    public void convertToCallTrimmed_emptyLinetabs() {
        CallRow callRow = CallTransformer.convertToCallTrimmed("\t\t\t", null); //From Excel
        assertNull(callRow);
    }

    @Test
    public void convertToList_withBlanks() {
        List<CallRow> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);
        expected.add(new CallRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        List<CallRow> result = CallTransformer.convertToList(is, null);

        assertEquals(expected, result);
    }
}