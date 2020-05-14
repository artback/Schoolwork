package com.HuffmanCoding;

import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;


public class HuffmanCoding 
{

	// Inner classes HuffmanNode and HuffmanLeaf where HuffmanLeaf is inherited from HuffmanNode
	// A HuffmanNode contains only frequency
	// A HuffmanLeaf contains frequency, character and the code for the character (as a String)
	// All nodes have references to its left and right child
	// It is okay to use public for members in private inner classes 
	
	private  class HuffmanNode // for You to complete according to above
	{
   	   int freq;
	   HuffmanNode Left;
	   HuffmanNode Right;
		HuffmanNode(){
		  this.freq=0;
		  this.Right=null;
		  this.Left=null;
		}
	   HuffmanNode(int freq,HuffmanNode Left, HuffmanNode Right){
		  this.freq=freq;
		  this.Left=Left;
		  this.Right=Right;
	   }
	}
	
	private class HuffmanLeaf extends HuffmanNode // for You to complete according to above
	{
      char ch;
	  String code;
		HuffmanLeaf(){
		 this.freq=0;
         this.Left=null;
		 this.Right=null;
	     this.code="";
		}
		HuffmanLeaf(int freq, char ch){
			this.freq=freq;
			this.ch=ch;
		}
	}

	public HuffmanCoding() {

	}
	
	private void createFreqTable(String theString, int[] frequency)
	{
		// calculates the number of occurrences of each character in theString
		// subscript for frequency is the UniCode/Asccii of the letter
		// Tip: type conversion to int
		int nextEmpty =0;
		for (int i = 0; i < theString.length()-1; i++) {
			int ch = (int)theString.charAt(i);
            if(Arrays.asList(frequency).contains(ch) ){
              int index = Arrays.asList(frequency).indexOf(ch);
				frequency[index-1] = frequency[index-1]+1;
		     }
			  else{
               frequency[nextEmpty] = 1;
				frequency[nextEmpty+1] = ch;
		      }
		}
	}
	public String code(String toCode)
	{
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
	
	private Vector<HuffmanLeaf> createLeafs(int[] frequency)
	{
		// creates and return a Vector containing Huffman leafs for each character
		// that has more than 0 occurrences
		Vector<HuffmanLeaf> vec = new Vector<HuffmanLeaf>();
		int index =0;
		char ch;
		int freq =0;
		while (index < frequency.length-1){
			freq = frequency[index];
			ch = (char)frequency[index+1];
			index+=2;
		}
		return vec;
	}
	
	private Vector<HuffmanNode> copyOfLeafs(Vector<HuffmanLeaf> allLeafs)
	{
		//creates and returns a Vector containing the same (references to) Huffman leafs as in allLeafs
		Vector<HuffmanNode> = a
		return (Vector<HuffmanNode>)allLeafs.clone();
	}
	
	private HuffmanNode createHuffmanTree(Vector<HuffmanNode> allTrees) {
		// given all trees (in Vector allTrees), constructs the final huffmanTree
		// and return the reference to the root node
		int smalest = allTrees.elementAt(0).freq;
		int nextSmalest = allTrees.elementAt(0).freq;
		int smalestIndex = 0;
		int nextSmalestIndex = 0;
		for (int j = 0; j < allTrees.size(); j++) {
			for (int i = 0; i < allTrees.size(); i++) {
				int freq = allTrees.elementAt(i).freq;
				if (smalest > freq) {
					smalest = freq;
					smalestIndex = i;
				}
				else if (freq > smalest && nextSmalest > freq) {
					nextSmalest = freq;
					nextSmalestIndex = i;
				}
			}
			allTrees.elementAt(smalestIndex).Left= allTrees.elementAt(smalestIndex);
			allTrees.elementAt(smalestIndex).Right= allTrees.elementAt(nextSmalestIndex);
			allTrees.elementAt(smalestIndex).freq = smalest+nextSmalest;
			allTrees.remove(nextSmalestIndex);
		}
		return allTrees.elementAt(0);
	}
	
	
	
	private void generateCodes(HuffmanNode theRoot, String currentCode)
	{
		// recursive method for generating codes for each leaf 
		// add "0" for left and "1" for right tree
	
	}
	
	private String getCodedString(String toCode, Vector<HuffmanLeaf> allLeafs)
	{
		// given the text to code and all leafs where each leaf contains the character and the code for the character
		// creates the code for the text and returns it
		return null;
	}
	
	
	// it is a good idea to add some extra "helper" methods to take care of
	// some smaller but well defined tasks
	
	public static void main(String[] args)
	{
		HuffmanCoding huffman = new HuffmanCoding();
		String text = JOptionPane.showInputDialog(null, "Input the text you want to code: ", "Huffman coding", JOptionPane.QUESTION_MESSAGE);
		
		String codedText = huffman.code(text);
		
		JOptionPane.showMessageDialog(null, "The code for \n" + text + "\nis\n" + codedText, "Huffman coding", JOptionPane.INFORMATION_MESSAGE);
		
		System.exit(0);
	}
}
