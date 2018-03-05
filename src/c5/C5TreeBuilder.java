/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c5;

import java.util.ArrayList;

/**
 *
 * @author dyahayu
 */
public class C5TreeBuilder 
{
    private Node root;
    private ArrayList<ArrayList> dataset;

    public C5TreeBuilder()
    {
	dataset = new ArrayList();
	
	populateDataset();
	initiateBuild();
    }
    
    public void initiateBuild()
    {
	if ( root == null ) {
	    build(this.dataset);
	}
    }
    
    public Node search()
    {
	return null;
    }
    
    
    // this method only for development use
    private void populateDataset()
    {
	ArrayList e = new ArrayList();
	e.add(true);
	e.add(true);
	e.add(0);
	dataset.add((ArrayList)e.clone());
	e.clear();
	
	e.add(true);
	e.add(false);
	e.add(1);
	dataset.add((ArrayList)e.clone());
	e.clear();
	
	e.add(false);
	e.add(false);
	e.add(1);
	dataset.add((ArrayList)e.clone());
	e.clear();
	
	e.add(false);
	e.add(true);
	e.add(0);
	dataset.add((ArrayList)e.clone());
	e.clear();
    }
    
    private Node build(ArrayList<ArrayList> dataset)
    {
	int params[] = new int[2];
	
	for (int i = 0; i < dataset.size(); i++)
	{
	    params[(int)dataset.get(i).get(2)]++;
	}
	double totalEntropy = calculateEntropy(params, dataset.size());
	
	
	
	
	
	
	return null;
    }
    
    /*
    * used for calculate entropy separately
    * params[] amount of each class in dataset
    */
    private double calculateEntropy(int[] params, double totalCase)
    {
	double sum = 0;
	for (int i = 0; i < params.length; i++)
	{
	    sum += ((params[i]/totalCase) * -1)*Math.log(params[i]/totalCase)/Math.log(2.0);
	}
	
	return sum;
    }
    
    /*
    * used to combine entropy among classes
    */
    private double calculateEntropy(double[] params, double[] entropy, double totalCase)
    {
	return 0;
    }
}
