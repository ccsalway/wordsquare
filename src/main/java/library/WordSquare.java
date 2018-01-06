package library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

public class WordSquare {

    private final String DICTIONARY = "dictionary.txt";

    private int gridSize;
    private String charset;
    private Trie trie;
    private boolean findAll;
    private List<List<String>> results;  // holds all found wordsquares

    public WordSquare(int gridSize, String charset) throws IOException {
        this.gridSize = gridSize;
        this.charset = getDistinctChars(charset); // we only need distinct characters
        trie = new Trie();
        checkParameters();
        loadWords();
    }

    private String getDistinctChars(String charset) {
        Set<Character> set = new HashSet<>(charset.length());
        for (char c : charset.toLowerCase().toCharArray()) {
            set.add(c);  // does not add if char exists
        }
        StringBuilder sb = new StringBuilder();
        for (char c : set) {
            sb.append(c);
        }
        return sb.toString();
    }

    private void checkParameters() {
        if (gridSize < 2) {
            throw new IllegalArgumentException("Minimum grid size is 2");
        }
        if (charset.length() < 1) {
            throw new IllegalArgumentException("Charset is empty");
        }
        if (!charset.matches("[a-z]+")) {
            throw new IllegalArgumentException("Charset must only contain letters");
        }
    }

    private void loadWords() throws IOException {
        // pre-compile distinct characters into regex for faster processing
        Pattern pattern = Pattern.compile("[" + charset + "]+");

        // load dictionary words into Trie - for best Trie performance, make sure dictionary is in alphabetical order
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(DICTIONARY)) {
            if (stream == null) {
                throw new IOException("File not found");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
                String dictWord;
                while ((dictWord = br.readLine()) != null) {
                    if (dictWord.length() == gridSize) {            // we only want words the size of the grid
                        if (pattern.matcher(dictWord).matches()) {  // we only want words that contain (distinct) charset
                            trie.insert(dictWord);
                        }
                    }
                }
            }
        }
    }

    private void findSquares(int row, int col, Trie.Node[] rows) {
        if (row == rows.length) {
            // we got to the end of the rows, must be a wordsquare, add it to results
            List<String> result = new ArrayList<>(rows.length);
            for (Trie.Node node : rows) {
                result.add(node.value);
            }
            results.add(result);
        } else if (col < rows.length) {
            Trie.Node curRow = rows[row];
            Trie.Node curCol = rows[col];
            for (int i = 0; i < 26; i++) {
                if (curRow.children[i] != null && curCol.children[i] != null) {
                    rows[row] = curRow.children[i];
                    rows[col] = curCol.children[i];
                    findSquares(row, col + 1, rows);
                    // resets 'rows' to the parent
                    rows[row] = curRow;
                    rows[col] = curCol;
                    // set to true for multiple results
                    if (!findAll) {
                        break;
                    }
                }
            }
        } else {
            // row found, move on to the next row, stepped in one column
            findSquares(row + 1, row + 1, rows); // move diagonally in
        }
    }

    private List<List<String>> run(boolean findAll) {
        this.findAll = findAll;
        this.results = new ArrayList<>();

        Trie.Node[] rows = new Trie.Node[this.gridSize];
        Arrays.fill(rows, this.trie.getRoot());  // places Trie root onto each row
        findSquares(0, 0, rows);

        return this.results;
    }

    public List<String> findOne() {
        return run(false).get(0);
    }

    public List<List<String>> findAll() {
        return run(true);
    }

}
