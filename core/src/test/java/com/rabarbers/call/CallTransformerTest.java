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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class CallTransformerTest extends TestCase {

    @Test
    public void convertToCallTrimmed_simple() {
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageName());
        assertEquals("HeapRenderer", call.getClassName());
        assertEquals("render", call.getMethodName());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void convertToCallTrimmed_alias() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("stream", "...");
        Call call = CallTransformer.convertToCallTrimmed("stream", aliases);

        assertNull(call);
    }

    @Test
    public void convertToCallTrimmed_simpleConstructorFromClass() {
        //copied from class
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.call.Alpha.Alpha", null);

        assertEquals("com.rabarbers.call", call.getPackageName());
        assertEquals("Alpha", call.getClassName());
        assertEquals("Alpha", call.getMethodName());
        assertEquals("()", call.getSignatureX());
    }


    @Test
    public void convertToCallTrimmed_simpleConstructorFromInvocation() {
        //copied from class (for example, from Beta)
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.call.Alpha", null);

        assertEquals("com.rabarbers.call", call.getPackageName());
        assertEquals("Alpha", call.getClassName());
        assertEquals("Alpha", call.getMethodName());
        assertEquals("()", call.getSignatureX());
    }


    @Test
    public void convertToCallTrimmed_simpleRightPadded() {
        Call call = CallTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render    ", null);

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageName());
        assertEquals("HeapRenderer", call.getClassName());
        assertEquals("render", call.getMethodName());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indent() {
        Call call = CallTransformer.convertToCallWithWhitespace("    com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals("    ", call.getIndent());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageName());
        assertEquals("HeapRenderer", call.getClassName());
        assertEquals("render", call.getMethodName());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indentOnlyWhitespace() {
        Call call = CallTransformer.convertToCallWithWhitespace("    ", null);
        assertNull(call);
    }

    @Test
    public void convertToCallWithWhitespace_indentTabs() {
        Call call = CallTransformer.convertToCallWithWhitespace("\tcom.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals("\t", call.getIndent());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageName());
        assertEquals("HeapRenderer", call.getClassName());
        assertEquals("render", call.getMethodName());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indentMixedTabsWithSpaces() {
        Call call = CallTransformer.convertToCallWithWhitespace("\t\t   com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals("\t\t   ", call.getIndent());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", call.getPackageName());
        assertEquals("HeapRenderer", call.getClassName());
        assertEquals("render", call.getMethodName());
        assertEquals("()", call.getSignatureX());
    }

    @Test
    public void convertToList_convertToListStream() {
        List<Call> expected = new ArrayList<>();
        expected.add(new Call("  ", "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new Call("    ", "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new Call("      ", "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("  com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render").append("\n");
        sb.append("    com.rabarbers.verbalmodeler.sim.Publisher#printPage(int, com.rabarbers.verbalmodeler.sim.story.Story)").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        List<Call> result = CallTransformer.convertToList(is, null);

        assertEquals(expected, result);
    }

    @Test
    public void convertToList_convertToListFile() {
        List<Call> expected = new ArrayList<>();
        expected.add(new Call("  ", "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new Call("    ", "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new Call("      ", "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        File file = new File("src/test/resources/calls.txt");

        List<Call> result = CallTransformer.convertToList(file, null);

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
        Call call = CallTransformer.convertToCallTrimmed("", null);
        assertNull(call);
    }

    @Test
    public void convertToCallTrimmed_unknown() {
        Call call = CallTransformer.convertToCallTrimmed("...", null);
        assertNull(call);
    }

    @Test
    public void convertToCallTrimmed_unknownPadded() {
        Call call = CallTransformer.convertToCallTrimmed("  ...  ", null);
        assertNull(call);
    }

    @Test
    public void convertToCallWithWhitespace_convertToCallWithWhitespace_unknownPaddedTabs() {
        Call call = CallTransformer.convertToCallWithWhitespace("\t...\t\t\t\t", null);
        assertNull(call);
    }

    @Test
    public void convertToCallWithWhitespace_unknownPaddedTabsStart() {
        Call call = CallTransformer.convertToCallWithWhitespace("\t...", null);
        assertNull(call);
    }

    @Test
    public void convertToCallTrimmed_commentMark() {
        Call call = CallTransformer.convertToCallTrimmed("    //aa", null);
        assertNull(call);
    }

    @Test
    public void convertToCallTrimmed_emptyLinetabs() {
        Call call = CallTransformer.convertToCallTrimmed("\t\t\t", null); //From Excel
        assertNull(call);
    }

    @Test
    public void convertToList_withBlanks() {
        List<Call> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);
        expected.add(new Call("      ", "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        List<Call> result = CallTransformer.convertToList(is, null);

        assertEquals(expected, result);
    }
}