package library;

public class Trie {

    class Node {
        Node[] children = new Node[26]; // support only alphabet [a-z]
        String value = null;
    }

    private Node root = new Node();

    Node getRoot() {
        return root;
    }

    void insert(String string) {
        Node node = this.root;
        for (int i = 0, len = string.length(); i < len; i++) {
            int c = string.charAt(i) - 97;  // re-align character to 'a' = 97 in ASCII
            if (node.children[c] == null) {
                node.children[c] = new Node();
            }
            node = node.children[c];
        }
        node.value = string;
    }

    /* Not used
    Node find(String key) {
        Node node = this.root;
        for (int c : key.toCharArray()) {
            if ((node = node.children[c]) == null) {
                return null;
            }
        }
        return node;
    }
    */
}
