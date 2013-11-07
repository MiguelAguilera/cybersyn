
package effconnectivity;

import java.util.*;
import java.io.*;

public class TransferEntropy 
{

	private ProbDensity PY1XY;
	private ProbDensity PY;
	private ProbDensity PY1Y;
	private ProbDensity PXY;
	public double TE;

	
	private double bins;
	private double shifts;
	
	public TransferEntropy(ArrayList<String> FileNames, int m, double bins, double shifts) {

//		Compute probability density function of yn+1, xn, yn
//		System.out.println("Py1xy");
		ArrayList<String> Files = new ArrayList<String> ();
		int[] delays = new int[1+2*m];

		Files.add(FileNames.get(1));
		
		for(int i=0;i<m;i++) {
			delays[1+i]=i+1;
			Files.add(FileNames.get(0));
		}
		for(int i=0;i<m;i++) {
			delays[1+m+i]=i+1;
			Files.add(FileNames.get(1));
		}
		
		PY1XY = ASHM.GeneratePDF(Files, delays, bins, shifts);
			

//		Compute probability density function of yn
//		System.out.println("Py");
		Files.clear();
		delays = new int[m];
		for(int i=0;i<m;i++) {
			delays[i]=i+1;
			Files.add(FileNames.get(1));
		}
		
		PY = ASHM.GeneratePDF(Files, delays, bins, shifts);
		
//		Compute probability density function of yn+1 yn
//		System.out.println("Py1y");
		Files.clear();
		Files.add(FileNames.get(1));
		delays = new int[1+m];
		for(int i=0;i<m;i++) {
			delays[1+i]=i+1;
			Files.add(FileNames.get(1));
		}
		
		PY1Y = ASHM.GeneratePDF(Files, delays, bins, shifts);
		
		
//		Compute probability density function of xn yn
//		System.out.println("Pxy");
		Files.clear();
		delays = new int[2*m];
		for(int i=0;i<m;i++) {
			delays[i]=i+1;
			Files.add(FileNames.get(0));
		}
		for(int i=0;i<m;i++) {
			delays[m+i]=i+1;
			Files.add(FileNames.get(1));
		}
		
		PXY = ASHM.GeneratePDF(Files, delays, bins, shifts);
		
//		for(int i=0; i<bins+2*(shifts-1); i++) {
//				int[] pos = {i};
//				System.out.print(PY.get(pos));
//				System.out.print(", ");
//		}
//		System.out.println();
		
		
//		Compute joint probability density function of x and y
//		System.out.println("Pxy");
//		PXY = ASHM.GeneratePDF(FileNames, Delays, bins, shifts);
//		
//		for(int i=0; i<bins+2*(shifts-1); i++) {
//			for(int j=0; j<bins+2*(shifts-1); j++) {
//				int[] pos = {i,j};
//				System.out.print(PXY.get(pos));
//				System.out.print(", ");
//			}
//			System.out.println();
//		}
		
		
		double PI;
		
		int[] pos = new int[1+2*m];
		
		int N=(int)Math.pow((int)bins + 2*((int)shifts-1),2*m+1);
		
//		System.out.println(N);
		
		int[] posy = new int[m];
		int[] posy1y = new int[1+m];
		int[] posxy = new int[2*m];
		
		for (int i=0; i<N; i++) {

//			System.out.println(i);
//			for (int j=0; j<(2*m+1);j++) {
//				System.out.print(pos[j]);
//				System.out.print(", ");
//			}
//			System.out.println();

			// Compute indexes for PDFs
			System.arraycopy(pos, m+1, posy, 0, m);
			System.arraycopy(pos, m+1, posy1y, 1, m);
			posy1y[0]=pos[0];
			System.arraycopy(pos, 1, posxy, 0, 2*m);
			
//			System.out.println(i);
//			for (int j=0; j<(m+1);j++) {
//				System.out.print(posy1y[j]);
//				System.out.print(", ");
//			}
//			System.out.println();

			
			if(PY1XY.get(pos)==0 || PY.get(posy)==0 ) PI=1;
			else {
			
				PI = PY1XY.get(pos)* PY.get(posy)/PY1Y.get(posy1y)/PXY.get(posxy);
			}
			
			TE += PY1XY.get(pos)*Math.log(PI)/Math.log(2);
			
			
		
			// Update position for next index
			pos[0]+=1;
			for (int j=0; j<2*m;j++) {
				if(pos[j] == ((int)bins + 2*((int)shifts-1))) {
					pos[j]=0;
					pos[j+1] += 1;
				}
			}
			if(pos[2*m] == ((int)bins + 2*((int)shifts-1))) {
				pos[2*m]=0;
			}
		}
		

//		pos[0]=0;
//		pos[1]=1;
//		pos[2]=2;
//		pos[3]=3;
//		pos[4]=4;
//		int[] posy = new int[m];
//		System.arraycopy(pos, m+1, posy, 0, m);
//		int[] posy1y = new int[1+m];
//		System.arraycopy(pos, m+1, posy1y, 1, m);
//		posy1y[0]=pos[0];
//		int[] posxy = new int[2*m];
//		System.arraycopy(pos, 1, posxy, 0, 2*m);
//		
//		
//			for (int j=0; j<(1+m);j++) {
//				System.out.print(posy1y[j]);
//				System.out.print(", ");
//			}
//			System.out.println();
		
//		System.arraycopy(pos, m+1, pos1, m+1, 1+2*m);
		
//		System.out.println(pos1.length);
		

	}
	
	
//	public static void main(String[] args) {

//		ArrayList<String> FileNames = new ArrayList<String> ();
////		FileNames.add("data/x2.txt");
////		FileNames.add("data/x4.txt
//		

//		
//		int m=2;
//		
//		TransferEntropy TranfE = new TransferEntropy(FileNames, m, 8, 2);
//		
////		System.out.println("");
////		System.out.println("Mutual Information x-y");
//		System.out.println(TranfE.TE);
////		System.out.println("Entropy of x");
////		System.out.println(MutInfo.HX);
////		System.out.println("Entropy of y");
////		System.out.println(MutInfo.HY);


//		MutualInformation MutInfo = new MutualInformation(FileNames, 25, 2);
//		
//		
//		String File1 = "data/x1.txt";
//		String File2 = "data/x2.txt";


//	
//		Xcorr Xc = new Xcorr(File1, File2);
//	
//		System.out.println(Xc.X);
//	
//	}



}
