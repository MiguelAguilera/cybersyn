package effconnectivity;

import java.util.*;
import java.io.*;


public class Xcorr
{

	public ArrayList<Double> X1 = new ArrayList<Double> ();
	public ArrayList<Double> X2 = new ArrayList<Double> ();
	public ArrayList<Double> X = new ArrayList<Double> ();


	public Xcorr(String File1, String File2 ) {
	
		X1=LoadData(File1);
		X2=LoadData(File2);
		
		X=CrossCorrelation(ZeroMeanNormalizedTransform(X1),ZeroMeanNormalizedTransform(X2));
	}

	private ArrayList<Double> LoadData(String FileName) {  
	   
		ArrayList<Double> x1 = new ArrayList<Double>();
		try {
            
			File f = new File(FileName);
			Scanner in = new Scanner(f); 

	   		
			int i = 0;
           	while (in.hasNextDouble()) {
				x1.add(in.nextDouble());
	       		//System.out.println(x1.get(i));
				i++;
            }
	    
//	    int indx1=x1.size();
	    //System.out.println(indx1);
//	    X.add(x1);
		} catch (FileNotFoundException e) {
            System.out.println("Fichero no existe");
		} catch (IOException e) {
            System.out.println("Error I/O");
		}
            
		return x1;
		
	}

//    private double getMax(ArrayList<Double> x) {
//	Collections.sort(x); // Sort the arraylist
//	return x.get(x.size()-1);
//    }
//    public double getMin(ArrayList<Double> x) {
//	Collections.sort(x); // Sort the arraylist
//	return x.get(0);
//    }

	private double calculateAverage(ArrayList<Double> x) {
		double sum = 0;
		if(!x.isEmpty()) {
			for (double xi : x) {
				sum += xi;
			}
			return sum / x.size();
		}
      	return sum;
	}
	
	
	private double AbsMean(ArrayList<Double> x) {
		double sum = 0;
		if(!x.isEmpty()) {
			for (double xi : x) {
				sum += xi*xi;
			}
			return (sum / x.size());
		}
      	return sum;
	}


	private ArrayList<Double> ZeroMeanNormalizedTransform(ArrayList<Double> x) {
		double norm = 0;
		double x_mean = calculateAverage(x);
		if(!x.isEmpty()) {
			for (double xi : x) {
				norm += (xi-x_mean)*(xi-x_mean);
			}
			norm = Math.sqrt(norm / x.size());
		}
		ArrayList<Double> y = new ArrayList<Double>();
		if(norm != 0) {
			for (double xi : x) {
				y.add((xi-x_mean) / norm);
			}
		}     
		return y;
    }


	private double SumMult(ArrayList<Double> x, ArrayList<Double> y) {
		double sum=0;
		for (int i=0; i<(x.size()); i++) {
			sum += x.get(i)*y.get(i);
		}
		return sum;
	}

	private ArrayList<Double> CrossCorrelation(ArrayList<Double> x, ArrayList<Double> y) {
	
		ArrayList<Double> Xc = new ArrayList<Double> ();


		for (int i=0; i<(x.size()-1); i++) {
			
			Xc.add(SumMult(new ArrayList<Double>(x.subList(0, i+1)),new ArrayList<Double>(y.subList(y.size()-1-i,y.size())))  / x.size());
		}
	
		Xc.add(SumMult(x,y) / x.size());
		
		for (int i=0; i<(x.size()-1); i++) {
			Xc.add(SumMult(new ArrayList<Double>(x.subList(1+i, x.size())),new ArrayList<Double>(y.subList(0,y.size()-i))) / x.size());
		}

		return Xc;
    }


}


