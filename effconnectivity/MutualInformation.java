
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
	
	public MutualInformation(TimeSeries X1 , TimeSeries X2 , double bins, double shifts) {


		
		
//		Compute probability density function of x
		ArrayList<ArrayList<Double>> Xseries1 = new ArrayList<ArrayList<Double>> ();
		Xseries1.add(X1.X);

		PX = ASHM.GeneratePDF(Xseries1, bins, shifts);
		
		Xseries1.set(0,X2.X);
		PY = ASHM.GeneratePDF(Xseries1, bins, shifts);
		

		
//		Compute joint probability density function of x and y

		Xseries1.set(0,X1.X);
		Xseries1.add(X2.X);
		
		PXY = ASHM.GeneratePDF(Xseries1, bins, shifts);
		

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
