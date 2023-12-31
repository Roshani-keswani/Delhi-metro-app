import java.util.*;
import java.io.*;

public class metro {
	public class Vertex 
	{
		HashMap<String, Integer> nbrs = new HashMap<>();
	}

	static HashMap<String, Vertex> vtces;

	public metro() 
	{
		vtces = new HashMap<>();
	}

	public int numVetex() 
	{
		return this.vtces.size();
	}

	public boolean containsVertex(String vname) 
	{
		return this.vtces.containsKey(vname);
	}

	public void addVertex(String vname) 
	{
		Vertex vtx = new Vertex();
		vtces.put(vname, vtx);
	}

	public void removeVertex(String vname) 
	{
		Vertex vtx = vtces.get(vname);
		ArrayList<String> keys = new ArrayList<>(vtx.nbrs.keySet());

		for (String key : keys) 
		{
			Vertex nbrVtx = vtces.get(key);
			nbrVtx.nbrs.remove(vname);
		}

		vtces.remove(vname);
	}

	public int numEdges() 
	{
		ArrayList<String> keys = new ArrayList<>(vtces.keySet());
		int count = 0;

		for (String key : keys) 
		{
			Vertex vtx = vtces.get(key);
			count = count + vtx.nbrs.size();
		}

		return count / 2;
	}

	public boolean containsEdge(String vname1, String vname2) 
	{
		Vertex vtx1 = vtces.get(vname1);
		Vertex vtx2 = vtces.get(vname2);
		
		if (vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vname2)) {
			return false;
		}

		return true;
	}

	public void addEdge(String vname1, String vname2, int value) 
	{
		Vertex vtx1 = vtces.get(vname1); 
		Vertex vtx2 = vtces.get(vname2); 

		if (vtx1 == null || vtx2 == null || vtx1.nbrs.containsKey(vname2)) {
			return;
		}

		vtx1.nbrs.put(vname2, value);
		vtx2.nbrs.put(vname1, value);
	}

	public void removeEdge(String vname1, String vname2) 
	{
		Vertex vtx1 = vtces.get(vname1);
		Vertex vtx2 = vtces.get(vname2);
		
		//check if the vertices given or the edge between these vertices exist or not
		if (vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vname2)) {
			return;
		}

		vtx1.nbrs.remove(vname2);
		vtx2.nbrs.remove(vname1);
	}

	public void display_Map() 
	{
		System.out.println("\t Delhi Metro Map");
		System.out.println("\t------------------");
		System.out.println("----------------------------------------------------\n");
		ArrayList<String> keys = new ArrayList<>(vtces.keySet());

		for (String key : keys) 
		{
			String str = key + " =>\n";
			Vertex vtx = vtces.get(key);
			ArrayList<String> vtxnbrs = new ArrayList<>(vtx.nbrs.keySet());
			
			for (String nbr : vtxnbrs)
			{
				str = str + "\t" + nbr + "\t";
							if (nbr.length()<16)
							str = str + "\t";
							if (nbr.length()<8)
							str = str + "\t";
							str = str + vtx.nbrs.get(nbr) + "\n";
			}
			System.out.println(str);
		}
		System.out.println("\t------------------");
		System.out.println("---------------------------------------------------\n");

	}
	
	public void display_Stations() 
	{
		System.out.println("\n***********************************************************************\n");
		ArrayList<String> keys = new ArrayList<>(vtces.keySet());
		int i=1;
		for(String key : keys) 
		{
			System.out.println(i + ". " + key);
			i++;
		}
		System.out.println("\n***********************************************************************\n");
	}
	
	public boolean hasPath(String vname1, String vname2, HashMap<String, Boolean> processed) 
	{
		// DIR EDGE
		if (containsEdge(vname1, vname2)) {
			return true;
		}

		//MARK AS DONE
		processed.put(vname1, true);

		Vertex vtx = vtces.get(vname1);
		ArrayList<String> nbrs = new ArrayList<>(vtx.nbrs.keySet());

		//TRAVERSE THE NBRS OF THE VERTEX
		for (String nbr : nbrs) 
		{

			if (!processed.containsKey(nbr))
				if (hasPath(nbr, vname2, processed))
					return true;
		}

		return false;
	}
	
        private class Pair 
		{
			String vname;
			String psf;
			int min_dis;
			
		}
		
		public String Get_Minimum_Distance(String src, String dst) 
		{
			int min = Integer.MAX_VALUE;
			String ans = "";
			HashMap<String, Boolean> processed = new HashMap<>();
			LinkedList<Pair> stack = new LinkedList<>();

			// create a new pair
			Pair sp = new Pair();
			sp.vname = src;
			sp.psf = src + "  ";
			sp.min_dis = 0;
			
			
			// put the new pair in stack
			stack.addFirst(sp);

			// while stack is not empty keep on doing the work
			while (!stack.isEmpty()) 
			{
				// remove a pair from stack
				Pair rp = stack.removeFirst();

				if (processed.containsKey(rp.vname)) 
				{
					continue;
				}

				// processed put
				processed.put(rp.vname, true);
				
				//if there exists a direct edge b/w removed pair and destination vertex
				if (rp.vname.equals(dst)) 
				{
					int temp = rp.min_dis;
					if(temp<min) {
						ans = rp.psf;
						min = temp;
					}
					continue;
				}

				Vertex rpvtx = vtces.get(rp.vname);
				ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());
 
				for(String nbr : nbrs) 
				{
					// process only unprocessed nbrs
					if (!processed.containsKey(nbr)) {

						// make a new pair of nbr and put in queue
						Pair np = new Pair();
						np.vname = nbr;
						np.psf = rp.psf + nbr + "  ";
						np.min_dis = rp.min_dis + rpvtx.nbrs.get(nbr);
						stack.addFirst(np);
					}
				}
			}
			ans = ans + Integer.toString(min);
			return ans;
		}
	

		public static void Create_Metro_Map(Graph_M g)
		{
			g.addVertex("Noida Sector 62~B");
			g.addVertex("Botanical Garden~B");
			g.addVertex("Yamuna Bank~B");
			g.addVertex("Rajiv Chowk~BY");
			g.addVertex("Vaishali~B");
			g.addVertex("Moti Nagar~B");
			g.addVertex("Janak Puri West~BO");
			g.addVertex("Dwarka Sector 21~B");
			g.addVertex("Huda City Center~Y");
			g.addVertex("Saket~Y");
			g.addVertex("Vishwavidyalaya~Y");
			g.addVertex("Chandni Chowk~Y");
			g.addVertex("New Delhi~YO");
			g.addVertex("AIIMS~Y");
			g.addVertex("Shivaji Stadium~O");
			g.addVertex("DDS Campus~O");
			g.addVertex("IGI Airport~O");
			g.addVertex("Rajouri Garden~BP");
			g.addVertex("Netaji Subhash Place~PR");
			g.addVertex("Punjabi Bagh West~P");
			
			g.addEdge("Noida Sector 62~B", "Botanical Garden~B", 8);
			g.addEdge("Botanical Garden~B", "Yamuna Bank~B", 10);
			g.addEdge("Yamuna Bank~B", "Vaishali~B", 8);
			g.addEdge("Yamuna Bank~B", "Rajiv Chowk~BY", 6);
			g.addEdge("Rajiv Chowk~BY", "Moti Nagar~B", 9);
			g.addEdge("Moti Nagar~B", "Janak Puri West~BO", 7);
			g.addEdge("Janak Puri West~BO", "Dwarka Sector 21~B", 6);
			g.addEdge("Huda City Center~Y", "Saket~Y", 15);
			g.addEdge("Saket~Y", "AIIMS~Y", 6);
			g.addEdge("AIIMS~Y", "Rajiv Chowk~BY", 7);
			g.addEdge("Rajiv Chowk~BY", "New Delhi~YO", 1);
			g.addEdge("New Delhi~YO", "Chandni Chowk~Y", 2);
			g.addEdge("Chandni Chowk~Y", "Vishwavidyalaya~Y", 5);
			g.addEdge("New Delhi~YO", "Shivaji Stadium~O", 2);
			g.addEdge("Shivaji Stadium~O", "DDS Campus~O", 7);
			g.addEdge("DDS Campus~O", "IGI Airport~O", 8);
			g.addEdge("Moti Nagar~B", "Rajouri Garden~BP", 2);
			g.addEdge("Punjabi Bagh West~P", "Rajouri Garden~BP", 2);
			g.addEdge("Punjabi Bagh West~P", "Netaji Subhash Place~PR", 3);
		}
	
		
    public static void main(String[] args) throws IOException
		{
			Graph_M g = new Graph_M();
			Create_Metro_Map(g);
			
			System.out.println("\n\t\t\t****WELCOME TO THE METRO APP*****");
			BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
            while(true)
			{
				System.out.print("\n***********************************************************\n");
				System.out.println("\t\t\t\t~~LIST OF ACTIONS~~\n\n");
				System.out.println("1. LIST ALL THE STATIONS IN THE MAP");
				System.out.println("2. SHOW THE METRO MAP");
				System.out.println("3. GET SHORTEST PATH (DISTANCE WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
				System.out.println("4. EXIT THE MENU");
				
				System.out.print("\nENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 4) : ");
				int choice = -1;
				try {
					choice = Integer.parseInt(inp.readLine());
				} catch(Exception e) {
					// default will handle
				}
				System.out.print("\n***********************************************************\n");
				if(choice == 4)
				{
					System.exit(0);
				}
				switch(choice)
				{
				case 1:
					g.display_Stations();
					break;
			
				case 2:
					g.display_Map();
					break;
				
				case 3:
					System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
					String s1 = inp.readLine();
					String s2 = inp.readLine();
				
					HashMap<String, Boolean> processed2 = new HashMap<>();
					if(!g.containsVertex(s1) || !g.containsVertex(s2) || !g.hasPath(s1, s2, processed2))
						System.out.println("THE INPUTS ARE INVALID");
					else 
					{
						
						System.out.println(g.Get_Minimum_Distance(s1, s2));
					}
					break;
				
               	default:
                    System.out.println("Please enter a valid option! ");
                    System.out.println("The options you can choose are from 1 to 4. ");
                            
				}
			}
			
		}	
	}


