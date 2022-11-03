package jazari.machine_learning.classifiers;

// The following java code is based on a multi-layer 
// Back Propagation Neural Network Class (BackPropagation.class)
//
// Created by Anthony J. Papagelis & Dong Soo Kim
//
//  DateCreated:	15 September, 2001
// Last Update:	14 October, 2001

import java.util.*;
import java.io.*;

public class Node {
	public	float 	Output;		
	// Output signal from current node

	public	float 	Weight[];		
	// Vector of weights from previous nodes to current node

	public	float	Threshold;	
	// Node Threshold /Bias

	public	float	WeightDiff[];	
	// Weight difference between the nth and the (n-1) iteration

	public	float	ThresholdDiff;	
	// Threshold difference between the nth and the (n-1) iteration

	public	float	SignalError;	
	// Output signal error

    	// InitialiseWeights function assigns a randomly 
	// generated number, between -1 and 1, to the 
	// Threshold and Weights to the current node
	private void InitialiseWeights() {
		Threshold = -1+2*(float)Math.random();	    	
		// Initialise threshold nodes with a random 
		// number between -1 and 1

		ThresholdDiff = 0;				
		// Initially, ThresholdDiff is assigned to 0 so 
		// that the Momentum term can work during the 1st 
		// iteration

        	for(int i = 0; i < Weight.length; i++) {
			Weight[i]= -1+2*(float)Math.random();	
			// Initialise all weight inputs with a 
			// random number between -1 and 1

			WeightDiff[i] = 0;			
			// Initially, WeightDiff is assigned to 0 
			// so that the Momentum term can work during 
			// the 1st iteration
		}
	}

	public Node (int NumberOfNodes) {
		Weight = new float[NumberOfNodes];		
		// Create an array of Weight with the same 
		// size as the vector of inputs to the node

		WeightDiff = new float[NumberOfNodes];	
		// Create an array of weightDiff with the same 
		// size as the vector of inputs to the node

		InitialiseWeights();				
		// Initialise the Weights and Thresholds to the node
	}

	// added by DSK
	public float[] get_weights() { return Weight; }
	public float get_output() { return Output; }
};