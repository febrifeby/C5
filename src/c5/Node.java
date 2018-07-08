/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c5;

import java.util.LinkedList;

/**
 *
 * 
 */
public class Node {
    
    public static final int TYPE_LEAF = 1;
    public static final int TYPE_CLASSIFIER = 0;
    
    // these value are just some random number
    // classifier label is integer 1 - 88
    // leaf label is:
    public static final int LABEL_LEAF_PARANOID = 110;
    public static final int LABEL_LEAF_HEBREFENIK = 120;
    public static final int LABEL_LEAF_KATATONIK = 130;
    public static final int LABEL_LEAF_UNDIFFERENTIATED = 140;
    public static final int LABEL_LEAF_SIMPLEKS = 150;
    
    private int typeOfNode;
    private int label;
    private Node left;
    private Node right;
    private Node parent;
    private boolean explored = false;

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }
    
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public LinkedList<Integer> blackListLabel = new LinkedList<Integer>();

    public Node(int typeOfNode, int label){
        this(typeOfNode, label, null, null);
    }
    
    public Node(int typeOfNode, int label, Node left, Node right)
    {
	this.typeOfNode = typeOfNode;
	this.label = label;
	this.left = left;
	this.right = right;
    }

    public int getTypeOfNode()
    {
	return typeOfNode;
    }

    public int getLabel()
    {
	return label;
    }

    public Node getLeft()
    {
	return left;
    }

    public Node getRight()
    {
	return right;
    }

    public void setLeft(Node left)
    {
	this.left = left;
    }

    public void setRight(Node right)
    {
	this.right = right;
    }

    public void setTypeOfNode(int typeOfNode) {
        this.typeOfNode = typeOfNode;
    }

    public void setLabel(int label) {
        this.label = label;
    }
    
    public Node clone() {
        Node newNode = new Node(this.typeOfNode, this.label, this.left, this.right);
        newNode.blackListLabel = this.blackListLabel;
        newNode.explored = this.explored;
        newNode.parent = this.parent;
        return newNode;
    }
    
    
}
