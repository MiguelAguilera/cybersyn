
package effconnectivity;

import java.util.*;
import java.io.*;
import java.text.*;


public class Forum 
{

	private ArrayList<Long> TimeStamps = new ArrayList<Long>(); //Post timestamps
	private ArrayList<Integer> F = new ArrayList<Integer>();	//Post forum id
	private ArrayList<String> Name = new ArrayList<String>();	//Post forum name	
	private ArrayList<Integer> indsF;	//List of forum ids
	private ArrayList<String> namesF = new ArrayList<String>();	//List of forum ids
	public ArrayList<Integer> indsF_written = new ArrayList<Integer>();	//List of forum ids
	public ArrayList<String> namesF_written = new ArrayList<String>();	//List of forum ids


	public Forum(String FileName, int step, int minPosts) {
		
		LoadData(FileName);
		WriteData(step, minPosts);
		
		
	}

//	Load data from files
	private void LoadData(String FileName) {  
		try {
            
			File f = new File(FileName);
			Scanner in = new Scanner(f); 

//			// Load headers
//		    in.useDelimiter("\\n");
//		    in.next();
		    
		    //Load fields
			in.useDelimiter(",|\\n");
			String date1;
			Date date2 = new Date();

			
				while (in.hasNext()) {
					Name.add(in.next());							//ca.Name
					F.add(in.nextInt()); 				//d.CategoryID
//					System.out.println(F.get(F.size()-1));
					in.nextInt();						//DiscussionID
					in.nextInt();						//InsertUserID
									
//					TimeStamps.add(in.nextInt());		//DateInserted
//					System.out.println(x1.get(x1.size()-1));
					date1=in.next();
//					date1 = date1.replaceAll("\"","");
			
					try {
							date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date1);
						} catch (ParseException ex) {
								  ex.printStackTrace(); // or log it using a logging framework
						}
					TimeStamps.add(date2.getTime()/1000L);
					
			}
			
			// Get unique forum_ids
			Set<Integer> indsF1 = new HashSet<Integer>(F);
			indsF= new ArrayList<Integer> (indsF1);
			Collections.sort(indsF);
			
//			System.out.println(Name.size());
			
			for (int indF:indsF) {
				boolean found = false;
				int ind=0;
				while (found==false) {
					if (indF==F.get(ind)) {

						namesF.add(Name.get(ind));
						found=true;
					}
					ind++;
				}
			}
			

            } catch (FileNotFoundException e) {
            System.out.println("Fichero no existe");
            } catch (IOException e) {
            System.out.println("Error I/O");
            }
            

    	}
    	
	private void WriteData(int step, int minPosts) {
		

		// Period of analysis of 1 month from actual instant
		long Period = 3600*24*30;

		Date date = new Date();
		long maxTimeStamp = date.getTime()/1000L;
		long minTimeStamp = maxTimeStamp - Period;

		
//		System.out.println(date.getTime()/1000L);

		File Ffile;
		
		int[] x ;				//time series of number of posts per period
		int N = (int)Math.ceil(((double)maxTimeStamp-(double)minTimeStamp+1)/(double)step);
		int countPosts;			//counter of total posts per forum
		int indT;				//index of x for each TimeStamp
		
		
		
		
		// Extract time series for each forum and write to file
		for (int ind=0;ind<indsF.size();ind++) {
			
			//Initialize time series and post count
			x = new int[N];
			countPosts=0;
			
//			
			for (int i=0;i<F.size();i++) {
			
				if(indsF.get(ind)==F.get(i)) {
//					System.out.println(F.get(i));
					countPosts++;
					indT=(int)Math.ceil(( (double)TimeStamps.get(i)-(double)minTimeStamp+1)/(double)step);
					x[indT-1]=x[indT-1]+1;
					}
			}
			
			// Write to file if forum has minimum amount of posts
			if (countPosts >= minPosts) {
				
				indsF_written.add(indsF.get(ind));
				namesF_written.add(namesF.get(ind));
				
				try {
					Ffile= new File("data/forum/" + Integer.toString(indsF.get(ind)) + ".txt");
					PrintStream ps = new PrintStream(Ffile);
				
					for (int i=0;i<N;i++)
//						ps.println(Math.log((double) x[i]+1));
						ps.println(x[i]);
					ps.close();
				} catch (FileNotFoundException e) {
					System.out.println("Fichero no existe");
				} catch (IOException e) {
					System.out.println("Error I/O");
				}
				
			

			}
			
//			System.out.println(countPosts);

		}

	}

}
