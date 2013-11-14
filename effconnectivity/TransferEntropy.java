
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
	
	public TransferEntropy(TimeSeries X1 , TimeSeries X2, int m, double bins, double shifts) {

//		Compute probability density function of yn+1, xn, yn
		ArrayList<ArrayList<Double>> Xseries1 = new ArrayList<ArrayList<Double>> ();
		

		int[] delays = new int[1+2*m];

		Xseries1.add(X2.Delay(0,m));
		
		for(int i=0;i<m;i++) {
			Xseries1.add(X1.Delay(i+1,m));
		}
		for(int i=0;i<m;i++) {
			Xseries1.add(X2.Delay(i+1,m));
		}
		
		PY1XY = ASHM.GeneratePDF(Xseries1, bins, shifts);
			

//		Compute probability density function of yn
		Xseries1.clear();
		delays = new int[m];
		for(int i=0;i<m;i++) {
			Xseries1.add(X2.Delay(i+1,m));
		}
		
		PY = ASHM.GeneratePDF(Xseries1, bins, shifts);
		
//		Compute probability density function of yn+1 yn
		Xseries1.clear();
		Xseries1.add(X2.Delay(0,m));
		delays = new int[1+m];
		for(int i=0;i<m;i++) {
			Xseries1.add(X2.Delay(i+1,m));
		}
		
		PY1Y = ASHM.GeneratePDF(Xseries1, bins, shifts);
		
		
//		Compute probability density function of xn yn
		Xseries1.clear();
		delays = new int[2*m];
		for(int i=0;i<m;i++) {
			Xseries1.add(X1.Delay(i+1,m));
		}
		for(int i=0;i<m;i++) {
			Xseries1.add(X2.Delay(i+1,m));
		}
		
		PXY = ASHM.GeneratePDF(Xseries1, bins, shifts);
		

		
		double PI;
		
		int[] pos = new int[1+2*m];
		
		int N=(int)Math.pow((int)bins + 2*((int)shifts-1),2*m+1);
		
		int[] posy = new int[m];
		int[] posy1y = new int[1+m];
		int[] posxy = new int[2*m];
		
		for (int i=0; i<N; i++) {

			// Compute indexes for PDFs
			System.arraycopy(pos, m+1, posy, 0, m);
			System.arraycopy(pos, m+1, posy1y, 1, m);
			posy1y[0]=pos[0];
			System.arraycopy(pos, 1, posxy, 0, 2*m);
			
			
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
		

	}
	

}
