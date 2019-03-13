package com.rabarbers.call;

import com.rabarbers.call.domain.row.MethodRow;
import com.rabarbers.call.domain.row.TextRow;
import com.rabarbers.call.domain.row.Row;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class RowTransformerTest extends TestCase {

    @Test
    public void convertToCallTrimmed_simple() {
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null, 0);

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", methodRow.getPackageName());
        assertEquals("HeapRenderer", methodRow.getClassName());
        assertEquals("render", methodRow.getMethodName());
        assertEquals("()", methodRow.getSignatureX());
    }

    @Test
    public void convertToCallTrimmed_alias() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("stream", "...");
        aliases.put("class_init", "...");
        TextRow row = (TextRow) RowTransformer.convertToCallTrimmed("stream", aliases, 0);

        assertEquals("...", row.getContent());
        assertEquals(0, row.getDepth());
    }

    @Test
    public void convertToCallTrimmed_simpleConstructorFromClass() {
        //copied from class
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallTrimmed("com.rabarbers.methodRow.Alpha.Alpha", null, 0);

        assertEquals("com.rabarbers.methodRow", methodRow.getPackageName());
        assertEquals("Alpha", methodRow.getClassName());
        assertEquals("Alpha", methodRow.getMethodName());
        assertEquals("()", methodRow.getSignatureX());
    }


    @Test
    public void convertToCallTrimmed_simpleConstructorFromInvocation() {
        //copied from class (for example, from Beta)
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallTrimmed("com.rabarbers.methodRow.Alpha", null, 0);

        assertEquals("com.rabarbers.methodRow", methodRow.getPackageName());
        assertEquals("Alpha", methodRow.getClassName());
        assertEquals("Alpha", methodRow.getMethodName());
        assertEquals("()", methodRow.getSignatureX());
    }


    @Test
    public void convertToCallTrimmed_simpleRightPadded() {
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallTrimmed("com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render    ", null, 0);

        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", methodRow.getPackageName());
        assertEquals("HeapRenderer", methodRow.getClassName());
        assertEquals("render", methodRow.getMethodName());
        assertEquals("()", methodRow.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indent() {
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallWithWhitespace("    com.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals(2, methodRow.getDepth());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", methodRow.getPackageName());
        assertEquals("HeapRenderer", methodRow.getClassName());
        assertEquals("render", methodRow.getMethodName());
        assertEquals("()", methodRow.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_comment() {
        TextRow row = (TextRow) RowTransformer.convertToCallWithWhitespace("    //", null);

        assertEquals(2, row.getDepth());
        assertEquals("//", row.getContent());
    }

    @Test
    public void convertToCallWithWhitespace_commentTab() {
        TextRow row = (TextRow) RowTransformer.convertToCallWithWhitespace("\t\t//", null);

        assertEquals(2, row.getDepth());
        assertEquals("//", row.getContent());
    }

    @Test
    public void convertToCallWithWhitespace_indentOnlyWhitespace() {
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallWithWhitespace("    ", null);
        assertNull(methodRow);
    }

    @Test
    public void convertToCallWithWhitespace_indentTabs() {
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallWithWhitespace("\tcom.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals(1, methodRow.getDepth());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", methodRow.getPackageName());
        assertEquals("HeapRenderer", methodRow.getClassName());
        assertEquals("render", methodRow.getMethodName());
        assertEquals("()", methodRow.getSignatureX());
    }

    @Test
    public void convertToCallWithWhitespace_indentMixedTabsWithSpaces() {
        MethodRow methodRow = (MethodRow) RowTransformer.convertToCallWithWhitespace("\t\t\t\t\tcom.rabarbers.verbalmodeler.sim.renderer.HeapRenderer#render", null);

        assertEquals(5, methodRow.getDepth());
        assertEquals("com.rabarbers.verbalmodeler.sim.renderer", methodRow.getPackageName());
        assertEquals("HeapRenderer", methodRow.getClassName());
        assertEquals("render", methodRow.getMethodName());
        assertEquals("()", methodRow.getSignatureX());
    }

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
        List<Row> result = RowTransformer.convertToList(is, null);

        assertEquals(expected, result);
    }

    @Test
    public void convertToList_convertToListFile() {
        List<MethodRow> expected = new ArrayList<>();
        expected.add(new MethodRow(1, "com.rabarbers.verbalmodeler.sim.renderer", "HeapRenderer", "render", "()"));
        expected.add(new MethodRow(2, "com.rabarbers.verbalmodeler.sim", "Publisher", "printPage", "(int, com.rabarbers.verbalmodeler.sim.story.Story)"));
        expected.add(new MethodRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        File file = new File("src/test/resources/calls.txt");

        List<Row> result = RowTransformer.convertToList(file, null);

        assertEquals(expected, result);
    }

    @Test
    public void convertToCallTrimmed_emptyLine() {
        Row row = RowTransformer.convertToCallTrimmed("", null, 0);
        assertNull(row);
    }

    @Test
    public void convertToCallTrimmed_unknown() {
        TextRow row = (TextRow) RowTransformer.convertToCallTrimmed("...", null, 3);

        assertEquals("...", row.getContent());
        assertEquals(3, row.getDepth());
    }

    @Test
    public void convertToCallTrimmed_unknownPadded() {
        TextRow row = (TextRow) RowTransformer.convertToCallTrimmed("  ...  ", null, 5);

        assertEquals("...", row.getContent());
        assertEquals(5, row.getDepth());
    }

    @Test
    public void convertToCallWithWhitespace_convertToCallWithWhitespace_unknownPaddedTabs() {
        TextRow row = (TextRow) RowTransformer.convertToCallWithWhitespace("\t...\t\t\t\t", null);

        assertEquals("...", row.getContent());
        assertEquals(1, row.getDepth());
    }

    @Test
    public void convertToCallWithWhitespace_unknownPaddedTabsStart() {
        TextRow row = (TextRow) RowTransformer.convertToCallWithWhitespace("\t...", null);

        assertEquals("...", row.getContent());
        assertEquals(1, row.getDepth());
    }

    @Test
    public void convertToCallTrimmed_commentMark() {
        TextRow row = (TextRow) RowTransformer.convertToCallTrimmed("//aa", null, 2);

        assertEquals("//aa", row.getContent());
        assertEquals(2, row.getDepth());
    }

    @Test
    public void convertToCallTrimmed_emptyLinetabs() {
        Row row = RowTransformer.convertToCallTrimmed("\t\t\t", null, 0); //From Excel
        assertNull(row);
    }

    @Test
    public void convertToList_withBlanks() {
        List<MethodRow> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);
        expected.add(new MethodRow(3, "com.rabarbers.verbalmodeler.sim", "Publisher", "publish", "()"));

        StringBuilder sb = new StringBuilder();
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("      com.rabarbers.verbalmodeler.sim.Publisher#publish").append("\n");

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        List<Row> result = RowTransformer.convertToList(is, null);

        assertEquals(expected, result);
    }
}