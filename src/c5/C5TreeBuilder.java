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
	    build(this.dataset, root);
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
    
    private Node build(ArrayList<ArrayList> dataset, Node parentNode)
    {
	// if condition to create more node fulfilled
	
	
	int params[] = new int[2];
	
	for (int i = 0; i < dataset.size(); i++)
	{
	    params[(int)dataset.get(i).get(2)]++;
	}
	double totalEntropy = calculateEntropy(params, dataset.size());
	
	// kesal ya
	int cases = 0;
	params[0] = params[1] = 0;
	for (int i = 0; i < dataset.size(); i++)
	{
	    if ((boolean)dataset.get(i).get(0))
	    {
		params[(int)dataset.get(i).get(2)]++;
		cases++;
	    }
	    
	}
	double attr1YesEntropy = calculateEntropy(params, cases);
	
	// kesal tidak
	params[0] = params[1] = 0;
	cases = 0;
	for (int i = 0; i < dataset.size(); i++)
	{
	    if (!(boolean)dataset.get(i).get(0))
	    {
		params[(int)dataset.get(i).get(2)]++;
		cases++;
	    }
	    
	}
	double attr1NoEntropy = calculateEntropy(params, cases);
	
	// kesal gabungan
	params[0] = params[1] = 0;
	for (int i = 0; i < dataset.size(); i++)
	{
	    if (!(boolean)dataset.get(i).get(0))
	    {
		params[0]++;
	    }
	    else
	    {
		params[1]++;
	    }
	    
	}
	double attr1Gabungan = calculateEntropy(
		params,
		new double[]{attr1YesEntropy, attr1NoEntropy}, 
		dataset.size());
	
	// senang ya
	params[0] = params[1] = 0;
	cases = 0;
	for (int i = 0; i < dataset.size(); i++)
	{
	    if ((boolean)dataset.get(i).get(1))
	    {
		params[(int)dataset.get(i).get(2)]++;
		cases++;
	    }
	    
	}
	double attr2YesEntropy = calculateEntropy(params, cases);
	
	// senang tidak
	params[0] = params[1] = 0;
	cases = 0;
	for (int i = 0; i < dataset.size(); i++)
	{
	    if (!(boolean)dataset.get(i).get(1))
	    {
		params[(int)dataset.get(i).get(2)]++;
		cases++;
	    }
	    
	}
	double attr2NoEntropy = calculateEntropy(params, cases);
	
	// senang gabungan
	params[0] = params[1] = 0;
	for (int i = 0; i < dataset.size(); i++)
	{
	    if (!(boolean)dataset.get(i).get(1))
	    {
		params[0]++;
	    }
	    else
	    {
		params[1]++;
	    }
	    
	}
	
	double attr2Gabungan = calculateEntropy(
		params,
		new double[]{attr2YesEntropy, attr2NoEntropy}, 
		dataset.size());
	
	// gain
	double gainAttr1 = calculateGain(totalEntropy, attr1Gabungan);
	double gainAttr2 = calculateGain(totalEntropy, attr2Gabungan);
	
	// search for highest gain
	
	
	// let the recursive plays its role!!!
	parentNode.setLeft(build(dataset, new Node()));
	parentNode.setRight(build(dataset, new Node()));
	
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
    private double calculateEntropy(int[] params, double[] entropy, double totalCase)
    {
	return 0;
    }
    
    private double calculateGain(double totalEntropy, double attributeEntropy)
    {
	return totalEntropy - attributeEntropy;
    }
}
