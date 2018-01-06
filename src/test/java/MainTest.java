import library.WordSquare;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    private final ByteArrayOutputStream sysErr = new ByteArrayOutputStream();

    @BeforeAll
    public void setUpStreams() {
        System.setErr(new PrintStream(sysErr));
    }

    @AfterAll
    public void cleanUpStreams() {
        System.setErr(null);
    }

    @BeforeEach
    public void clearConsole() {
        sysErr.reset();
    }

    //--------------------------------

    @Test
    void testArgsEmpty() {
        /* empty arguments */
        Main.main(new String[0]);
        assertEquals("Arguments required: Integer, String" + System.lineSeparator(), sysErr.toString());
    }

    @Test
    void testArgsIncomplete() {
        /* incomplete arguments */
        Main.main(new String[]{"2"});
        assertEquals("Arguments required: Integer, String" + System.lineSeparator(), sysErr.toString());
    }

    @Test
    void testArgsInteger() {
        /* invalid integer */
        Main.main(new String[]{"a", "abc"});
        assertEquals("First argument must be an Integer" + System.lineSeparator(), sysErr.toString());
    }

    @Test
    void testArgsString() {
        /* empty string */
        Main.main(new String[]{"2", ""});
        assertEquals("Charset is empty" + System.lineSeparator(), sysErr.toString());
    }

    @Test
    void testArgsSize() {
        /* grid size too small */
        Main.main(new String[]{"1", "aa"});
        assertEquals("Minimum grid size is 2" + System.lineSeparator(), sysErr.toString());
    }

    @Test
    void testArgsCharset() {
        /* grid size too small */
        Main.main(new String[]{"4", "abc$"});
        assertEquals("Charset must only contain letters" + System.lineSeparator(), sysErr.toString());
    }

    /**
     * Code Challenge tests
     */

    @Test
    public void testWordSquare1() throws IOException {
        WordSquare wordSquare = new WordSquare(4, "eeeeddoonnnsssrv");
        List<List<String>> squares = wordSquare.findAll();
        assertTrue(squares.contains(new ArrayList(Arrays.asList("rose", "oven", "send", "ends"))));
    }

    @Test
    public void testWordSquare2() throws IOException {
        WordSquare wordSquare = new WordSquare(4, "aaccdeeeemmnnnoo");
        List<List<String>> squares = wordSquare.findAll();
        assertTrue(squares.contains(new ArrayList(Arrays.asList("moan", "once", "acme", "need"))));
    }

    @Test
    public void testWordSquare3() throws IOException {
        WordSquare wordSquare = new WordSquare(5, "aaaeeeefhhmoonssrrrrttttw");
        List<List<String>> squares = wordSquare.findAll();
        assertTrue(squares.contains(new ArrayList(Arrays.asList("feast", "earth", "armor", "stone", "threw"))));
    }

    @Test
    public void testWordSquare4() throws IOException {
        WordSquare wordSquare = new WordSquare(5, "aabbeeeeeeeehmosrrrruttvv");
        List<List<String>> squares = wordSquare.findAll();
        assertTrue(squares.contains(new ArrayList(Arrays.asList("heart", "ember", "above", "revue", "trees"))));
    }

    @Test
    public void testWordSquare5() throws IOException {
        WordSquare wordSquare = new WordSquare(7, "aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy");
        List<List<String>> squares = wordSquare.findAll();
        assertTrue(squares.contains(new ArrayList(Arrays.asList("bravado", "renamed", "analogy", "valuers", "amoebas", "degrade", "odyssey"))));
    }

    /* Long running - circa 30s */
    /*
    @Test
    public void testWordSquare6() throws IOException {
        WordSquare wordSquare = new WordSquare(8, "abcdefghijklmnopqrstuvwxzy");
        List<List<String>> squares = wordSquare.findAll();
        assertTrue(squares.contains(new ArrayList(Arrays.asList("carboras", "aperient", "recaller", "brassica", "oilseeds", "relievos", "anecdote", "strasses"))));
    }
    */
}
