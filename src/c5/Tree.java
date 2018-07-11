/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c5;

import org.apache.poi.hdgf.pointers.Pointer;

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
    
    public int test(Object[] data) {
        
        Node evaluatedNode = this.head;
        int classOfData = 0;
        
        while (evaluatedNode != null)
        {
//            evaluatedNode = evaluatedNode.getLeft();
            if (evaluatedNode.getLabel() >= 100)
            {
                classOfData = evaluatedNode.getLabel();
                evaluatedNode = null;
            }
            else if (((Double)data[1+evaluatedNode.getLabel()]).floatValue() == 1.0)
                evaluatedNode = evaluatedNode.getLeft();
            else if (((Double)data[1+evaluatedNode.getLabel()]).floatValue() == 0.0)
                evaluatedNode = evaluatedNode.getRight();
        }
        return classOfData;
    }
    
    @Override
    public String toString()
    {
        Node pointer = head;
        String temp = "Root: " + pointer.getLabel() + "\n";
        pointer.setExplored(true);
        int height = 0;
        
        while (pointer != null)
        {
            if (pointer.getLeft() != null && !pointer.getLeft().isExplored())
            {   
                pointer = pointer.getLeft();
                height++;
                String space = "";
                for (int i = 0; i < height; i++) space += "    ";
                temp += space + "Left: " + pointer.getLabel() + ";Parent: " + pointer.getParent().getLabel() + "\n";
                pointer.setExplored(true);
                continue;
            }
            
            if (pointer.getRight() != null && !pointer.getRight().isExplored())
            {   
                pointer = pointer.getRight();
                height++;
                String space = "";
                for (int i = 0; i < height; i++) space += "    ";
                temp += space + "Right: " + pointer.getLabel() + ";Parent: " + pointer.getParent().getLabel() + "\n";
                pointer.setExplored(true);
                continue;
            }
            
            if ((pointer.getRight() == null && pointer.getLeft() == null) || 
                    (pointer.getRight().isExplored() && pointer.getLeft().isExplored())) {
                height--;
                pointer = pointer.getParent();
            }
        }
        return temp;
    }
}
