
package effconnectivity;

import java.util.*;
import java.io.*;

public class MutualInformation 
{

	private ProbDensity PX;
	private ProbDensity PY;
	private ProbDensity PXY;
	public double MI;
	private double HX;
	private double HY;
	
	private double bins;
	private double shifts;
	
	public MutualInformation(ArrayList<String> FileNames, double bins, double shifts) {

//		MI=0;
		
		int[] Delays = new int[2];
		Delays[0]=0;
		Delays[1]=0;
		
		
//		Compute probability density function of x
//		System.out.println("Px");
		ArrayList<String> File1 = new ArrayList<String> ();
		File1.add(FileNames.get(0));
		int[] delay1 = new int[1];
		delay1[0]=Delays[0];
		PX = ASHM.GeneratePDF(File1, delay1, bins, shifts);
		
//		for(int i=0; i<bins+2*(shifts-1); i++) {
//				int[] pos = {i};
//				System.out.print(PX.get(pos));
//				System.out.print(", ");
//		}
//		System.out.println();
		
//		Compute probability density function of y
//		System.out.println("Py");
		File1.set(0,FileNames.get(1));
		delay1[0]=Delays[1];
		PY = ASHM.GeneratePDF(File1, delay1, bins, shifts);
		
//		for(int i=0; i<bins+2*(shifts-1); i++) {
//				int[] pos = {i};
//				System.out.print(PY.get(pos));
//				System.out.print(", ");
//		}
//		System.out.println();
		
		
//		Compute joint probability density function of x and y
//		System.out.println("Pxy");
		PXY = ASHM.GeneratePDF(FileNames, Delays, bins, shifts);
		
//		for(int i=0; i<bins+2*(shifts-1); i++) {
//			for(int j=0; j<bins+2*(shifts-1); j++) {
//				int[] pos = {i,j};
//				System.out.print(PXY.get(pos));
//				System.out.print(", ");
//			}
//			System.out.println();
//		}
//		
		int N= PX.N;
		
		double PI, PIX, PIY;
		
//		Compute Mutual Information between x and y
		for (int i=0; i<N; i++) {
			for (int j=0; j<N; j++) {
				
//				if(PXY.get(new int[] {i,j})==0 || PX.get(new int[] {i})==0 || PY.get(new int[] {j})==0) PI=1;
				if(PXY.get(new int[] {i,j})==0) PI=1;
				else {
					PI=PXY.get(new int[] {i,j})/PX.get(new int[] {i})/PY.get(new int[] {j});
				}

				MI += PXY.get(new int[] {i,j})*Math.log(PI)/Math.log(2)/(double)N;
			}
		}

//		Compute Entropy of signal x
		for (int i=0; i<N; i++) {
			if (PX.get(new int[] {i})==0) PIX=1;
			else PIX =PX.get(new int[] {i});	
			HX -= PX.get(new int[] {i})*Math.log(PIX)/Math.log(2)/(double)N;
		}
		
//		Compute Entropy of signal y
		for (int j=0; j<N; j++) {
				if (PY.get(new int[] {j})==0) PIY=1;
				else PIY =PY.get(new int[] {j});
				HY -= PY.get(new int[] {j})*Math.log(PIY)/Math.log(2)/(double)N;
		}
		 
		// Normalize Entropy value
//		if (HX>HY) MI=MI/HX;
//		else MI = MI/HY;
		
	}





}
