
package effconnectivity;

import java.util.*;
import java.io.*;

public class ProbDensity
{

	public int N;
	public int[] Nelements;
	public double[] PDF;
	public int bins;
	public int shifts;
	public ArrayList<ArrayList<Double>> axes = new ArrayList<ArrayList<Double>>();

//	Class constructor
	public ProbDensity(ArrayList<Integer> Dimensions, int bins1, int shifts1) {
	
		bins=bins1;
		shifts=shifts1;
		N=1;
		Nelements = new int[Dimensions.size()];
		for (int i=0; i< Dimensions.size();i++) {
			N=N*Dimensions.get(i);
			Nelements[i]=Dimensions.get(i);
		}
		// Generate PDF as a 1 dimensonal array
		PDF = new double[N];
	}
	
//	Compute the product of the elements of an array
	private int ArrayProd(int[] x) {
		int prod=1;
		for (int i=0; i<x.length; i++) {
			prod = prod*x[i];
		}
		return prod;
	}
	
//	Convert N-dimensonal index into one-dimensional index
	public int indexNDto1D(int[] inds) {
		int ind = inds[0];
//		System.out.println(Nelements.length);
		if (Nelements.length>1) {
			for (int i=1; i<inds.length; i++) {
				ind= ind + inds[i]*ArrayProd(Arrays.copyOfRange(Nelements, 0, i));
			}
		}
		return ind;
	}
	
//	Get PDF value with an N-dimensional index
	public void set(int[] inds, double value) {
		PDF[indexNDto1D(inds)]=value;
    }
    
//    Set PDF value with an N-dimensional index
	public double get(int[] inds) {
		return PDF[indexNDto1D(inds)];

    }

}
