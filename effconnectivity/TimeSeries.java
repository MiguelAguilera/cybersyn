package effconnectivity;

import java.util.*;
import java.io.*;

public class TimeSeries
{

	public ArrayList<Double> X = new ArrayList<Double> ();
	
	public TimeSeries(String File1, boolean Log)  {
		X=LoadData(File1, Log);
	}



	private ArrayList<Double> LoadData(String FileName, boolean Log) {  
	   
		ArrayList<Double> x1 = new ArrayList<Double>();
		try {
            
			File f = new File(FileName);
			Scanner in = new Scanner(f); 

	   		
			int i = 0;
			double xi;
           	while (in.hasNextDouble()) {
				xi=in.nextDouble();
				if (Log)
					xi=Math.log(xi+1);
				x1.add(xi);
				i++;
            }
	    
			
		} catch (FileNotFoundException e) {
            System.out.println("Fichero no existe");
		} catch (IOException e) {
            System.out.println("Error I/O");
		}
            
		return x1;
		
	}
	
	
	public ArrayList<Double> Delay(int delay, int maxdelay) {
	
		ArrayList<Double> x1 = new ArrayList<Double>(X);
		
		for (int j=0;j<(maxdelay-delay);j++)
			x1.remove(x1.size()-1);
			
		for (int j=0;j<delay;j++)
			x1.remove(0);
			
		return x1;
	
	}


	





}


