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
    
    @Override
    public String toString()
    {
        Node pointer = head;
        String temp = "Root: " + pointer.getLabel() + "\n";
        pointer = pointer.getLeft();
        int height = 1;
        
//        while (pointer.getTypeOfNode() == Node.TYPE_CLASSIFIER)
//        {   
//            String space = "";
//            for (int i = 0; i < height; i++) space += "    ";
//            temp += pointer.getLabel() + "\n" + space + "Left: ";
//            pointer = pointer.getLeft();
//            height++;
//        }
        while (pointer != null)
        {
            if (pointer.getLeft() != null && !pointer.getLeft().isExplored())
            {   
                String space = "";
                for (int i = 0; i < height; i++) space += "    ";
                temp += space + "Left: " + pointer.getLabel() + "\n";
                pointer = pointer.getLeft();
                pointer.setExplored(true);
                height++;
                continue;
            }
            
            if (pointer.getRight() != null && !pointer.getRight().isExplored())
            {   
                String space = "";
                for (int i = 0; i < height; i++) space += "    ";
                temp += space + "Right: " + pointer.getLabel() + "\n";
                pointer = pointer.getRight();
                pointer.setExplored(true);
                height++;
                continue;
            }
            height--;
            pointer = pointer.getParent();
        }
        return temp;
    }
}
