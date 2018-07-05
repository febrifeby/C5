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
    
    public static Tree initiateBuild(Node root)
    {
        Tree tree = null;
	tree = new Tree(build(C5TreeBuilder.dataset, root));
        
        return tree;
    }

    private static Node build(Object[][] table, Node parent){
        int tableWidth = table[0].length;
        
        // if data dalam table memiliki kelas yang sama, maka return node leaf
        Double classOfFirstData = new Double(0);
        for (int i = 0; i < table.length; i++)
        {
            if (i == 0)
                classOfFirstData = new Double((Double)table[i][tableWidth - 2]-1);
            
            if (!classOfFirstData.equals((Double)table[i][tableWidth - 2]))
                break;
            
            return new Node(Node.TYPE_LEAF, classOfFirstData.intValue());
        }
        
        // calculate entropy total
        
        int params[] = new int[5]; // yes or no
	for (int i = 1; i < table.length; i++)
	{
	    params[((Double)table[i][tableWidth - 2]).intValue() - 1]++;
	}
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
            if (highestGainRatio < gainRatioPerG[i]){
                highestGainRatio = gainRatioPerG[i];
                choosenG = i;
            }
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
        
        System.out.println(choosenG);
        
	// let the recursive plays its role!!!
        Node newParent = new Node(Node.TYPE_CLASSIFIER, choosenG);
	parent.setLeft(build(tableYes, newParent));
	parent.setRight(build(tableNo, newParent));
//	
//      return Node with highest Gain
        return parent;
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
	return (totalEntropy - attributeEntropy)/totalEntropy;
    }
    
    public static void main(String[] args)
    {
        initiateBuild(new Node());
    }
}




