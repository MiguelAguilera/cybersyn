
package effconnectivity;

import java.util.*;
import java.io.*;

public class Connectivity 
{

	
	
	
	public static void main(String[] args) {

		int mode = 0;
		int bins = 0;
		int shifts = 0;
		int m=0;
		
		if (args.length > 0) {
			try {
				mode = Integer.parseInt(args[0]);
				if (mode == 2 || mode == 3) {
					bins = Integer.parseInt(args[1]);
					shifts = Integer.parseInt(args[2]);
				}
				if (mode == 3) {
					m = Integer.parseInt(args[3]);
				}
	
				
			} catch (NumberFormatException e) {
				System.err.println("Arguments" + " must correspond to any of the following options");
            	System.err.println("1: Functional connectivity: cross-correlation");
            	System.err.println("2: Functional connectivity: mutual information");
            	System.err.println("3: Effective connectivity: transfer entropy");
				System.exit(1);
				System.exit(1);
			}
		}

		int Nmin = 20;
		
		long Tspan = 3600*24*30*3;
		int sampling = 3600*6;
		

		Forum foro = new Forum("data/forum/forums.csv",sampling,Nmin,Tspan);	
				
		ArrayList<Integer> nums = foro.indsF_written;
		
		ArrayList<String> Forums = foro.namesF_written;

				

			int Nf = nums.size();

			double[][] A = new double[Nf][Nf];
		
			int num1, num2;
		
//			for (int i=0;i<Nf;i++) {
//				num1=nums.get(i);
//				System.out.print(";");
//				System.out.print(Forums.get(i));
//			}
//			System.out.println();	
//				
//		
//			for (int i=0;i<Nf;i++) {
//				num1=nums.get(i);
//				System.out.print(Forums.get(i));	
//	//			System.out.print(";");
//			
//				for (int j=0;j<Nf;j++) {

//					num2=nums.get(j);

//		
//					String File1 = "data/forum/" + Integer.toString(num1) + ".txt";
//					String File2 = "data/forum/" + Integer.toString(num2) + ".txt";
//					 

//					boolean Log=true;

//					TimeSeries X1= new TimeSeries(File1,Log);
//					TimeSeries X2= new TimeSeries(File2,Log);
//				
//				
//        switch (mode) {
//            case 1:  // Cross correlation 
//            		 Xcorr Xc = new Xcorr(X1.X, X2.X);
//					 A[i][j]=Xc.X.get((Xc.X.size()-1)/2);
//                     break;
//            case 2:  // MutualInformation
//					 A[i][j]=MutualInformation.get(X1, X2,bins,shifts);
//                     break;
//            case 3:  // Transfer Entropy
//					 TransferEntropy TransfEnt = new TransferEntropy(X1, X2, bins,shifts,m);
//					 A[i][j]=TransferEntropy.get(X1, X2, bins,shifts,m);
//                     break;
//            default: System.err.println("Arguments" + " must correspond to any of the following options");
//            		 System.err.println("1: Functional connectivity: cross-correlation");
//            		 System.err.println("2: Functional connectivity: mutual information");
//            		 System.err.println("3: Effective connectivity: transfer entropy");
//					 System.exit(1);
//                     break;
//        }
//					
//					
//					System.out.print(";");
//					if ((A[i][j] < 0) || (i==j))
//						System.out.print(0.0);
//					else
//						System.out.print(A[i][j]);
//				
//				}
//				System.out.println();
//			
//			
//			

//			}
		

	
	}



}
