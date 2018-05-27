/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c5;

/**
 *
 * @author dyahayu
 */
public class Node {
    
    public static final int TYPE_LEAF = 1;
    public static final int TYPE_CLASSIFIER = 0;
    
    private int typeOfNode;
    private String label;
    private Node left;
    private Node right;

    public Node(int typeOfNode, String label, Node left, Node right)
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

    public String getLabel()
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

          
}
