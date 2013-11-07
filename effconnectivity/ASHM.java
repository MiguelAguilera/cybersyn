
package effconnectivity;


import java.util.*;
import java.io.*;


public class ASHM
{

	private ArrayList<ArrayList<Double>> Xseries = new ArrayList<ArrayList<Double>>();
	private int Nseries;
	private ArrayList<ArrayList<Double>> Xaxes = new ArrayList<ArrayList<Double>>();
	private ArrayList<Integer> Dimensions = new ArrayList<Integer>();	
	private int Ndim;	
	private double bins;
	private double shifts;
	private ProbDensity Density;
	private double ksum;
	
//	Class constructor
	public ASHM(ArrayList<String> FileNames, int[] Delays, double bins1, double shifts1) {
		
		bins=bins1;
		shifts=shifts1;
		
//		List DelaysList = Arrays.asList(ArrayUtils.toObject(Delays));
		int maxdelay=Delays[0];
		for (int i=0; i<Delays.length;i++)
			if (Delays[i]>maxdelay)
				maxdelay=Delays[i];
		
		//	Load data
		for (int i=0; i<FileNames.size(); i++) {
			LoadData(FileNames.get(i), Delays[i], maxdelay);
		}
		Nseries=Xseries.get(0).size();

		// Compute number of dimensions
		for (ArrayList<Double> xi : Xseries) {
			Dimensions.add((int)bins+2*((int)shifts-1));
		}
		
		Ndim = Dimensions.size();
		
//		// Print Information
//		System.out.print(Ndim);
//		System.out.print(" dimensions: ");	
//		for (int d : Dimensions) {
//			System.out.print(d);
//			System.out.print(" ");
//		}
//		System.out.println();
//		System.out.print("Series length: ");
//		System.out.println(Nseries);
		
		//	Generate axes of Probabilidy Density Function	
		GenerateAxes();
		
		//	Initialize Probabilidy Density Function	
		Density = new ProbDensity(Dimensions,(int)bins,(int)shifts);
		
		Density.axes = Xaxes;
		ksum=0;
		int[]pos = new int[Ndim];
		for (int i=0; i< Math.pow(1 + 2*(shifts-1),Ndim); i++) {
			double ksum1=1.0;
			for (int j=0; j<Ndim;j++) {
				ksum1 *= shiftPonderation(pos[j]);
			}
			pos[0]+=1;
			for (int k=0; k<(Ndim-1);k++) {
				if(pos[k] == (1+2*(shifts-1))) {
					pos[k]=0;
					pos[k+1] += 1;
				}
			}
			if(pos[Ndim-1] == (1+2*(shifts-1))) {
				pos[Ndim-1]=0;
				pos[0] += 1;
			}
			ksum += ksum1;
		}
	
	}
	
//	Load data from files in ArrayList X
	private void LoadData(String FileName, int delay, int maxdelay) {  
		try {
            
		File f = new File(FileName);
		Scanner in = new Scanner(f); 

		ArrayList<Double> x1 = new ArrayList<Double>();
             
		int i = 0;
			while (in.hasNextDouble()) {
				x1.add(in.nextDouble());
				i++;
		}
		
		for (int j=0;j<(maxdelay-delay);j++)
			x1.remove(x1.size()-1);
			
		for (int j=0;j<delay;j++)
			x1.remove(0);
	    
		int indx1=x1.size();
		Xseries.add(x1);

            } catch (FileNotFoundException e) {
            System.out.println("Fichero no existe");
            } catch (IOException e) {
            System.out.println("Error I/O");
            }
    	}


//	Generate Axes of the Probability Density Function in ArrayList Xaxes
	private void GenerateAxes() {
		double h =  1 / bins;

		for (ArrayList<Double> Xi : Xseries) {
			
			ArrayList<Double> X1 = new ArrayList<Double> ();
			

			double dX=(Collections.max(Xi)-Collections.min(Xi))*h;
			double margin = dX/100;	// this margin avoids having the minimum value of Xseries being equally distant from two bins
			
			double xi = Collections.min(Xi) + (dX/2-(shifts-1)*dX) - margin/2;
			for (int i=0; i<(bins+2*(shifts-1)); i++) {
				X1.add(xi);
				xi += dX + margin/bins;
			}
			Xaxes.add(X1);
		}
	}
	
//	Update PDF with element i

	private void updatePDF(int ind) {
		
		int[] pos0 = new int[Ndim];
		int[] pos1 = new int[Ndim];
		int[] pos = new int[Ndim];
		
		double value;
		double value1;
	
//		Get position of element xi in PDF
		for (int i=0; i<Ndim; i++) {
			pos0[i]=find(Xaxes.get(i), Xseries.get(i).get(ind));
		}
		
//		Update position of xi and all its shifts
		for (int i=0; i< Math.pow(1 + 2*(shifts-1),Ndim); i++) {
			value1=1.0;
			// Calculate ponderation value for each shift
			for (int j=0; j<Ndim;j++) {
				value1 *= shiftPonderation(pos1[j]);
							
				if (shiftPonderation(pos1[j])<0) {
					System.out.println("Error, bad shifting");
					System.out.println(value1);
					System.out.println(shiftPonderation(pos1[j]));
					System.out.println(pos1[j]);
					System.out.println(j);
			}
			}
			// Calculate position of shift
			for (int j=0; j<(Ndim);j++) {
				pos[j]=(pos0[j]) + (pos1[j]-(int)shifts+1);	
				
//				System.out.print(pos0[j]);
//				System.out.print(" - ");
//				System.out.print(pos1[j]);
//				System.out.print(" - ");
//				System.out.println(pos[j]);		
			}
			

			// Update value of PDF
			value = Density.get(pos) + value1/ksum/Nseries;
			Density.set(pos,value);

			
			// Update displacement of position for next shift
			pos1[0]+=1;
			for (int j=0; j<(Ndim-1);j++) {
				if(pos1[j] == (1+2*(shifts-1))) {
					pos1[j]=0;
					if (Ndim>1) pos1[j+1] += 1;
				}
			}
			if(pos1[Ndim-1] == (1+2*(shifts-1))) {
				pos1[Ndim-1]=0;
//				pos1[0] += 1;
			}
		}
		
	}
	
	
//	Compute ponderation of shift
	private double shiftPonderation(int ind) {
		return 1.0-Math.abs(ind+1-shifts)/shifts;
	}
	
	
//	Calculate average value of array
	private double calculateAverage(ArrayList<Double> X) {
		double sum = 0;
		if(!X.isEmpty()) {
			for (double xi : X) {
				sum += xi;
			}
			return sum / X.size();
      	}
      	return sum;
	}
	
	
//	Find closer value in an ArrayList
	private int find(ArrayList<Double> X, double y) {
		ArrayList<Double> diff = new ArrayList<Double> ();
		for (double xi : X)
			diff.add(Math.abs(xi-y));
		double diffmin=Collections.min(diff);
		for (int i = 0; i < diff.size(); i++) {
			if(diff.get(i)==diffmin){
//				System.out.println(i);
//				System.out.println(Collections.min(Xseries.get(0)));
////				System.out.println(diff);
////				System.out.println(diffmin);
//				System.out.println(X);
//				System.out.println(y);
////				System.out.println(Collections.min(Xseries.get(0)));
				return i;
				}
		}
		return -1;
	}
	
	public static ProbDensity GeneratePDF(ArrayList<String> FileNames, int[] Delays, double bins1, double shifts1) {
		
	ASHM Hist = new ASHM(FileNames, Delays, bins1, shifts1);

//	// Print information
//	System.out.print("Size PDF: ");
//	System.out.println(Hist.Density.N);
//	
		for(int i=0; i<Hist.Nseries; i++) {
//			System.out.print("n = ");
//			System.out.println(i);
			Hist.updatePDF(i);
		}
		
	return Hist.Density;
	
	}

}



