package zeroOrder;

public class Trie {
    TrieNodes root;

    public Trie(){
        this.root = new TrieNodes();
    }

    public void insert(String word){
        TrieNodes temp = root;
        for (char c : word.toCharArray()){
            if (temp.children[c - 'a'] == null){
                temp.children[c - 'a'] = new TrieNodes(c);
            }
            temp = temp.children[c - 'a'];
        }
        temp.isWord = true;
    }

    public boolean search(String word){
        TrieNodes temp = root;
        for (char c : word.toCharArray()){
            if (temp.children[c - 'a'] == null){
                return false;
            }
            temp = temp.children[c - 'a'];
        }

        return temp.isWord;
    }

    public boolean startsWith(String prefix){
        TrieNodes temp = root;
        for (char c : prefix.toCharArray()){
            if (temp.children[c - 'a'] == null){
                return false;
            }
            temp = temp.children[c - 'a'];
        }
        return true;
    }

    public static void main(String[] args) {

    }
}

class TrieNodes {
    char c;
    boolean isWord;
    TrieNodes[] children = new TrieNodes[26];

    public TrieNodes(){}

    public TrieNodes(char c){
        this.c = c;
    }
}