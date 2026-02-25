import java.io.*;
import java.util.*;

public class Trie {

    // node class
    private static class TrieNode {

        boolean isWord;
        HashMap<Character, TrieNode> children = new HashMap<
            Character,
            TrieNode
        >();
    }

    // root node
    private TrieNode root = new TrieNode();

    /**
     * Returns a collection of strings in the trie that are within a Levenshtein distance of dist from the given target string
     * @param target
     * @param dist
     * @return A map where keys are strings in the trie, and the associated value for each string is its Levenshtein distance from the target.
     */
    public HashMap<String, Integer> suggestions(String target, int dist) {
        HashMap<String, Integer> words = new HashMap<String, Integer>();
        
        t(target, dist, dist, this.root, words, "");
        return words;
    }

    /**
     * 
     * @param target
     * @param dist
     * @param node
     * @param words
     * @return 
     */
    private void t(String target, int dist_origin, int dist, TrieNode node, HashMap<String, Integer> words, String word){
        if(target.length() == 0 && node.isWord && dist >= 0){
            int deltaDist = dist_origin - dist;
            if(!words.containsKey(word) || deltaDist < words.get(word)){
                words.put(word, deltaDist);
            }        
        }
        if(dist < 0){
            return ;
        }
        
        // del doesn't rely on c!!
        if(target.length() > 0){
            t(target.substring(1), dist_origin, dist - 1, node, words, word);
        }
        
        Character next = target.length() > 0 ? target.charAt(0) : null;
        for(Character c : node.children.keySet()){
            TrieNode current = node.children.get(c);

            if(next != null && next == c){
                // exact match
                t(target.substring(1), dist_origin, dist, current, words, word + c);
            }
            if(next != null && next != c){
                // substitution
                t(target.substring(1), dist_origin, dist - 1, current, words, word + c);
            }
            // insertion
            t(target, dist_origin, dist - 1, current, words, word + c);
        }
    }

    // method to add a string
    public boolean add(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                TrieNode child = current.children.get(c);
                if (child == null) {
                    child = new TrieNode();
                    current.children.put(c, child);
                }
                current = child;
            }
        }

        if (current.isWord) return false;

        current.isWord = true;
        return true;
    }

    // method to check if a string has been added
    public boolean contains(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                TrieNode child = current.children.get(c);
                if (child == null) {
                    return false;
                }
                current = child;
            }
        }

        return current.isWord;
    }

    // empty constructor
    public Trie() {
        super();
    }

    // constructor to add words from a stream, like standard input
    public Trie(InputStream source) {
        Scanner scan = new Scanner(source);
        addWords(scan);
        scan.close();
    }

    // constructor to add words from a file
    public Trie(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        addWords(scan);
        scan.close();
    }

    // helper function to add words from a scanner
    private void addWords(Scanner scan) {
        while (scan.hasNext()) {
            add(scan.next());
        }
    }

    // main function for testing
    public static void main(String[] args) {
        Trie dictionary;

        // Accpet File Name
        // if (args.length > 0) {
        //     try {
        //         dictionary = new Trie(args[0]);
        //     } catch (FileNotFoundException e) {
        //         System.err.printf(
        //             "could not open file %s for reading\n",
        //             args[0]
        //         );
        //         return;
        //     }
        // } else {
        //     dictionary = new Trie(System.in);
        // }


        try {
            dictionary = new Trie("./lab6/ospd.txt");
        } catch (FileNotFoundException e) {
            System.err.printf(
                "could not open file %s for reading\n",
                "lab6/ospd.txt"
            );
            return;
        }

        System.out.println(dictionary.contains("cat"));
        System.out.println(dictionary.suggestions("cat", 2));
    }
}
