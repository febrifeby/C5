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
public class C5TreeBuilder 
{
    private static Object[][] dataset = DatabaseReader.getTable();
    
    public static Tree initiateBuild(Node root, Integer amount)
    {
        Object[][] newDataset = new Object[amount][dataset[0].length];
        
        for (int i = 0; i < amount.intValue(); i++)
            newDataset[i] = dataset[i];
        
        Tree tree = null;
	tree = new Tree(build(newDataset, root, "kiri"));
        
        return tree;
    }

    private static Node build(Object[][] table, Node parent, String debug){
        // if data dalam table memiliki kelas yang sama, maka return node leaf        
        Double classOfFirstData = new Double(0);
        for (int i = 0; i < table.length; i++)
        {
            if (i == 0)
                classOfFirstData = new Double((Double)table[i][table[0].length - 2]-1);
            
            if (table.length > 1 && !classOfFirstData.equals((Double)table[i][table[0].length - 2]))
                break;
            else if (i == table.length - 1)
            {
                Node newNode = new Node(Node.TYPE_LEAF, 100 + classOfFirstData.intValue());
                newNode.setParent(parent.getParent());
                return newNode;
            }
        }
        
        // calculate entropy total
        int params[] = new int[5];        
	for (int i = 1; i < table.length; i++)
	{
	    params[((Double)table[i][table[0].length - 2]).intValue() - 1]++;
	}
        int defaultClass = -1;
        int maxClass = -1;
        for (int i = 0; i < params.length; i++)
            if (maxClass < params[i])
            {
                defaultClass = i;
                maxClass = params[i];
            }
        
        if (table.length == 0 || table == null)
        {
            Node newNode = new Node(Node.TYPE_LEAF, defaultClass + 100);
            newNode.setParent(parent.getParent());
            return newNode;
        }

        int tableWidth = table[0].length;
        
	double totalEntropy = calculateEntropy(params, table.length);
        
        // calculate entropy per G
        double[][] entropyPerG = new double[tableWidth - 3][2]; // 0 = no, 1 = yes
        double[] entropyPerGGabungan = new double[tableWidth - 3];
        int[][] paramsPerG0 = new int[tableWidth - 3][5]; // kelas 0 - 4
        int[][] paramsPerG1 = new int[tableWidth - 3][5]; // kelas 0 - 4
        
        for (int j = 0; j < entropyPerG.length; j++)
        {
            int yes = 0;
            int no = 0;
            for (int i = 0; i < table.length; i++)
            {
                if (((Double)table[i][j + 1]).intValue() == 0) {
                    paramsPerG0[j][((Double)table[i][tableWidth - 2]).intValue() - 1]++;
                    no++;
                }
                else {
                    paramsPerG1[j][((Double)table[i][tableWidth - 2]).intValue() - 1]++;
                    yes++;
                }
            }
            entropyPerG[j][0] = calculateEntropy(paramsPerG0[j], table.length);
            entropyPerG[j][1] = calculateEntropy(paramsPerG1[j], table.length);   
            
            // calculate entropy gabungan
            entropyPerGGabungan[j] = calculateEntropy(new int[]{no, yes}, 
                    new double[]{entropyPerG[j][0], entropyPerG[j][1]}, table.length);
        }
                
        // calculate gain and search for highest 
        double gainRatioPerG[] = new double[tableWidth - 3];
        int choosenG = -1;
        double highestGainRatio = -1;
        
        for (int i = 0; i < gainRatioPerG.length; i++)
        {
            gainRatioPerG[i] = calculateGainRatio(totalEntropy, entropyPerGGabungan[i]);
            if (highestGainRatio <= gainRatioPerG[i]){
                highestGainRatio = gainRatioPerG[i];
                choosenG = i;
            }
        }
        
        if (parent.blackListLabel.contains((Integer)choosenG) || choosenG == -1)
        {
            Node newNode = new Node(Node.TYPE_LEAF, defaultClass + 100);
            newNode.setParent(parent.getParent());
            return newNode;
        }
        
        // build new table        
        LinkedList<Object[]> listYes = new LinkedList<>();
        LinkedList<Object[]> listNo = new LinkedList<>();
        Object[][] tableYes = null;
        Object[][] tableNo = null;
        
        for (int i = 0; i < table.length; i++)
        {
            if (((Double)table[i][choosenG + 1]).intValue() == 1)
                listYes.add(table[i]);
            else
                listNo.add(table[i]);
        }
        
        tableYes = new Object[listYes.size()][tableWidth];
        tableNo = new Object[listNo.size()][tableWidth];
        
        for (int i = 0; i < listYes.size(); i++)
            tableYes[i] = listYes.get(i);
        
        for (int i = 0; i < listNo.size(); i++)
            tableNo[i] = listNo.get(i);
        
        parent.setLabel(choosenG);
        
	// let the recursive plays its role!!!
        parent.blackListLabel.add(choosenG);
        Node newParent = new Node(Node.TYPE_CLASSIFIER, parent.getLabel());
        newParent.setParent(parent);
        if (parent.blackListLabel != null)
            for (Integer i : parent.blackListLabel)
                newParent.blackListLabel.add(i);
        
	if (tableYes != null) parent.setLeft(build(tableYes, newParent, "kiri"));
	if (tableNo != null) parent.setRight(build(tableNo, newParent.clone(), "kanan"));
        return parent.clone();
    }
    
    private static double calculateEntropy(int[] params, double totalCase)
    {
        double sum = 0;
	for (int i = 0; i < params.length; i++)
	{
            if (params[i] != 0)
                sum += ((params[i]/totalCase) * -1)*Math.log(params[i]/totalCase)/Math.log(2.0);
	}	
        return sum;
    }
    
    /*
    * used to combine entropy among classes
    */
    private static double calculateEntropy(int[] params, double[] entropy, double totalCase)
    {
        double sum = 0;
        
        for (int i = 0; i < params.length; i++)
        {
            sum += (params[i]/totalCase) * entropy[i];
        }
        
	return sum;
    }
    
    private static double calculateGainRatio(double totalEntropy, double attributeEntropy)
    {
	return (totalEntropy - attributeEntropy);
    }
    
    public static void main(String[] args)
    {
//        System.out.println(initiateBuild(new Node(Node.TYPE_CLASSIFIER, 0)));
    }
}




