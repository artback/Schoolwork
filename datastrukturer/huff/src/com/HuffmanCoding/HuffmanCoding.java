package com.HuffmanCoding;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JOptionPane;


public class HuffmanCoding
{

    // Inner classes HuffmanNode and HuffmanLeaf where HuffmanLeaf is inherited from HuffmanNode
    // A HuffmanNode contains only frequency
    // A HuffmanLeaf contains frequency, character and the code for the character (as a String)
    // All nodes have references to its left and right child
    // It is okay to use public for members in private inner classes

    private  class HuffmanNode implements Comparable<HuffmanNode> {
        int freq;
        HuffmanNode left;
        HuffmanNode right;
        HuffmanNode(){
            this.freq=0;
            this.right=null;
            this.left=null;
        }
        HuffmanNode(int freq,HuffmanNode left, HuffmanNode right){
            this.freq=freq;
            this.left=left;
            this.right=right;
        }
        HuffmanNode(HuffmanNode other){
            this.left=new HuffmanNode(other.left);
            this.right=new HuffmanNode(other.right);
            this.freq=other.freq;
        }

        @Override
        public int compareTo(HuffmanNode other) {
            return other.freq-this.freq;
        }
    }
    private class HuffmanLeaf extends HuffmanNode {
        char ch;
        String code;
        HuffmanLeaf(){
            this.freq=0;
            this.left=null;
            this.right=null;
            this.code="";
        }
        HuffmanLeaf(int freq, char ch){
            this.freq=freq;
            this.ch=ch;
            this.left=null;
            this.right=null;
            this.code="";
        }
    }
    public HuffmanCoding() {

    }
    private void createFreqTable(String theString, int[] frequency) {
        // calculates the number of occurrences of each character in theString
        // subscript for frequency is the UniCode/Ascii of the letter
        for (int i = 0; i < theString.length(); i++) {
            int ch = (int)theString.charAt(i);
            frequency[ch]++;
        }
    }
    public String code(String toCode) {
        // generates the code (in 1 and 0) for the text in toCode
        // and returns the String that contains the "coded" text

        int[] frequency = new int[255];
        createFreqTable(toCode, frequency);
        Vector<HuffmanLeaf> allLeafs = createLeafs(frequency); // always contains the references to all leafs
        Vector<HuffmanNode> allTrees = copyOfLeafs(allLeafs); // starts out with all leafs but the content will
        // change during the algorithm to build the final HuffmanTre
        HuffmanNode root = createHuffmanTree(allTrees); // root will get the reference to the root of the HuffmanTree
        generateCodes(root, ""); // generates the code for each character

        return getCodedString(toCode, allLeafs); // return the coded text in toCode using allLeafs

    }
    private Vector<HuffmanLeaf> createLeafs(int[] frequency) {
        // creates and return a Vector containing Huffman leafs for each character
        // that has more than 0 occurrences
        Vector<HuffmanLeaf> vec = new Vector<>();
        char ch;
        int freq=0;
        for (int i = 0; i < 255; i++) {
            if(frequency[i] > 0) {
                freq = frequency[i];
                ch = (char)i;
                vec.addElement(new HuffmanLeaf(freq, ch));
            }
        }
        return vec;
    }
    private Vector<HuffmanNode> copyOfLeafs(Vector<HuffmanLeaf> allLeafs) {
        //creates and returns a Vector containing the same (references to) Huffman leafs as in allLeafs
        Vector<HuffmanNode> vec = new Vector<>(allLeafs);
        return vec;
    }
    private HuffmanNode createHuffmanTree(Vector<HuffmanNode> allTrees) {
        // given all trees (in Vector allTrees), constructs the final huffmanTree
        // and return the reference to the root node
        while(allTrees.size() > 1) {
            Collections.sort(allTrees);
            HuffmanNode left = allTrees.elementAt(allTrees.size() - 1);
            HuffmanNode right = allTrees.elementAt(allTrees.size() - 2);
            allTrees.remove(allTrees.size() - 1);
            allTrees.remove(allTrees.size() - 1);
            allTrees.add(new HuffmanNode((left.freq + right.freq), left, right));
        }
           return allTrees.elementAt(0);
    }
    private void generateCodes(HuffmanNode theRoot, String currentCode) {
        // recursive method for generating codes for each leaf
        //add "0" for left and "1" for right tree
        if(theRoot.left != null ){
          generateCodes(theRoot.left,currentCode+"0");
        }
        else{
            HuffmanLeaf leaf =(HuffmanLeaf)theRoot;
            leaf.code = currentCode;
        }
        if(theRoot.right !=null ){
            generateCodes(theRoot.right,currentCode+"1");
        }
        else{
            HuffmanLeaf leaf =(HuffmanLeaf)theRoot;
            leaf.code = currentCode;
        }

    }
    private String getCodedString(String toCode, Vector<HuffmanLeaf> allLeafs) {
        // given the text to code and all leafs where each leaf contains the character and the code for the character
        // creates the code for the text and returns it
        String s="";
        for (int i = 0; i < toCode.length() ; i++) {
            for (int j = 0; j < allLeafs.size(); j++) {
               if(toCode.charAt(i) == (allLeafs.elementAt(j).ch)){
                  s += allLeafs.elementAt(j).code;
               }
            }
        }
        return s;
    }
    public static void main(String[] args) {
        HuffmanCoding huffman = new HuffmanCoding();
        String text = JOptionPane.showInputDialog(null, "Input the text you want to code: ", "Huffman coding", JOptionPane.QUESTION_MESSAGE);
        String codedText = huffman.code(text);

        JOptionPane.showMessageDialog(null, "The code for \n" + text + "\nis\n" + codedText, "Huffman coding", JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }
}
