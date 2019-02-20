package com.rabarbers.call;

import com.rabarbers.call.domain.Call;
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
import java.util.List;

@RunWith(JUnit4.class)
public class CallTransformerTest extends TestCase {

    @Test
    public void simple() {
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render");

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageX());
        assertEquals("HeapRenderer", call.getClassX());
        assertEquals("render", call.getMethodX());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void simpleConstructorFromClass() {
        //copied from class
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.call.Alpha.Alpha");

        assertEquals("com.rabarbers.call", call.getPackageX());
        assertEquals("Alpha", call.getClassX());
        assertEquals("Alpha", call.getMethodX());
        assertEquals("()", call.getSignatureX());
    }


    @Test
    public void simpleConstructorFromInvocation() {
        //copied from class (for example, from Beta)
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.call.Alpha");

        assertEquals("com.rabarbers.call", call.getPackageX());
        assertEquals("Alpha", call.getClassX());
        assertEquals("Alpha", call.getMethodX());
        assertEquals("()", call.getSignatureX());
    }


    @Test
    public void simpleRightPadded() {
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render    ");

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageX());
        assertEquals("HeapRenderer", call.getClassX());
        assertEquals("render", call.getMethodX());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void indent() {
        Call call = CallTransformer.convertToCallWithWhitespace("    com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render");

        assertEquals("    ", call.getIndent());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageX());
        assertEquals("HeapRenderer", call.getClassX());
        assertEquals("render", call.getMethodX());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void indentOnlyWhitespace() {
        Call call = CallTransformer.convertToCallWithWhitespace("    ");
        assertNull(call);
    }

    @Test
    public void indentTabs() {
        Call call = CallTransformer.convertToCallWithWhitespace("\tcom.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render");

        assertEquals("\t", call.getIndent());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageX());
        assertEquals("HeapRenderer", call.getClassX());
        assertEquals("render", call.getMethodX());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void indentMixedTabsWithSpaces() {
        Call call = CallTransformer.convertToCallWithWhitespace("\t\t   com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render");

        assertEquals("\t\t   ", call.getIndent());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageX());
        assertEquals("HeapRenderer", call.getClassX());
        assertEquals("render", call.getMethodX());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void convertToListStream() {
        List<Call> expected = new ArrayList<>();
        expected.add(new Call("  ", "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new Call("    ", "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new Call("      ", "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("  com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render").append("\n");
        sb.append("    com.rabarbers.verbalmodeler.sim.Publisher#printPage(int, com.rabarbers.verbalmodeler.sim.story.Story)").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        List<Call> result = CallTransformer.convertToList(is);

        assertEquals(expected, result);
    }

    @Test
    public void convertToListFile() {
        List<Call> expected = new ArrayList<>();
        expected.add(new Call("  ", "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new Call("    ", "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new Call("      ", "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        File file = new File("src/test/resources/calls.txt");

        List<Call> result = CallTransformer.convertToList(file);

        assertEquals(expected, result);
    }

    @Test
    public void fileToFile() {
        File file = new File("src/test/resources/calls.txt");

        File result = CallTransformer.convertToFile(file, "target/calls_converted.txt");

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
    public void fileToFileWithEmpty() {
        File file = new File("src/test/resources/calls_empty.txt");

        File result = CallTransformer.convertToFile(file, "target/calls_empty_converted.txt");

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
    public void emptyLine() {
        Call call = CallTransformer.convertToCallTrimmed("");
        assertNull(call);
    }

    @Test
    public void unknown() {
        Call call = CallTransformer.convertToCallTrimmed("...");
        assertNull(call);
    }

    @Test
    public void unknownPadded() {
        Call call = CallTransformer.convertToCallTrimmed("  ...  ");
        assertNull(call);
    }

    @Test
    public void unknownPaddedTabs() {
        Call call = CallTransformer.convertToCallWithWhitespace("\t...\t\t\t\t");
        assertNull(call);
    }

    @Test
    public void unknownPaddedTabsStart() {
        Call call = CallTransformer.convertToCallWithWhitespace("\t...");
        assertNull(call);
    }

    @Test
    public void commentMark() {
        Call call = CallTransformer.convertToCallTrimmed("    //aa");
        assertNull(call);
    }

    @Test
    public void emptyLinetabs() {
        Call call = CallTransformer.convertToCallTrimmed("\t\t\t"); //From Excel
        assertNull(call);
    }

    @Test
    public void convertToListWithBlanks() {
        List<Call> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);
        expected.add(new Call("      ", "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        List<Call> result = CallTransformer.convertToList(is);

        assertEquals(expected, result);
    }
}