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
		
		boolean Log=true;
		
		ArrayList<File> fileNames = new ArrayList<File> ();
		ArrayList<TimeSeries> Series = new ArrayList<TimeSeries> ();
		String s1;
		
		try {	
			File f = new File("effconnectivity/data/files");
			Scanner in = new Scanner(f);
			in.useDelimiter("\\n");
			while (in.hasNext()) {
				fileNames.add(new File("effconnectivity/data/"+in.next()));
		    }
		    
		} catch (FileNotFoundException e) {
            System.out.println("Fichero no existe");
		} catch (IOException e) {
            System.out.println("Error I/O");
		}
        
        TimeSeries x1;
        for (File fileName:fileNames) {
        	Series.add(new TimeSeries(fileName,Log));
        }

		int Nf = Series.size();

		double[][] A = new double[Nf][Nf];
		
		int num1, num2;	
			
		for (TimeSeries serie:Series) {
			System.out.print(";");
			System.out.print(serie.name);			
		}
		System.out.println();		
		
		for (int i=0;i<Nf;i++) {
			System.out.print(Series.get(i).name);
			
			for (int j=0;j<Nf;j++) {


			TimeSeries X1 = Series.get(i);
			TimeSeries X2 = Series.get(j);
				
				
		    switch (mode) {
		        case 1:  // Cross correlation 
		        		 Xcorr Xc = new Xcorr(X1.X, X2.X);
						 A[i][j]=Xc.X.get((Xc.X.size()-1)/2);
		                 break;
		        case 2:  // MutualInformation
						 A[i][j]=MutualInformation.get(X1, X2,bins,shifts);
		                 break;
		        case 3:  // Transfer Entropy
						 TransferEntropy TransfEnt = new TransferEntropy(X1, X2, bins,shifts,m);
						 A[i][j]=TransferEntropy.get(X1, X2, bins,shifts,m);
		                 break;
		        default: System.err.println("Arguments" + " must correspond to any of the following options");
		        		 System.err.println("1: Functional connectivity: cross-correlation");
		        		 System.err.println("2: Functional connectivity: mutual information");
		        		 System.err.println("3: Effective connectivity: transfer entropy");
						 System.exit(1);
		                 break;
		    }
					
					
					System.out.print(";");
					if ((A[i][j] < 0) || (i==j))
						System.out.print(0.0);
					else
						System.out.print(A[i][j]);
				
				}
				System.out.println();
			
			}
		

	
	}



}
