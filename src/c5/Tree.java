/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c5;

/**
 *
 * @author Nously
 */
public class Tree {
    
    private Node head;

    public Tree() {
    }

    public Tree(Node head) {
        this.head = head;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }
    
    public int classify() {
        return 0;
    }
    
    public String output()
    {
        String temp = "";
        Node pointer = head;
        
        while (pointer.getTypeOfNode() == Node.TYPE_CLASSIFIER)
        {   
            temp += pointer.getLabel() + " ";
            pointer = pointer.getRight();
        }
        temp += pointer.getLabel();
        
        return temp;
    }
}
