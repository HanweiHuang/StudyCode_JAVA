package harvey.version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class findmdr {
	public static void main(String args[]){
		//int range = 250;
		String path = "/Users/harvey850331/Documents/workspace/comp9332ass/src/harvey/version/input.txt";
		File file = new File(path);
		BufferedReader bufferreader = null;
		String line = null;
		
		String range = null;
		String node_x = null;
		String node_y = null;
		
		ArrayList<node> arraylist = new ArrayList<node>();
		//String sp_line[] = new String[3];
		try {
			//read input file
			 bufferreader = new BufferedReader(new FileReader(file));
			 
			 //put node into arraylist
			 while((line = bufferreader.readLine())!=null){
				// System.out.println(line);
				String sp_line[] =  line.split("\t");
				if(sp_line[1].equals("0")&&sp_line[2].equals("0")){
					range=sp_line[0];
				}
				node no = new node();
				//for(int i=0;i<sp_line.length;i++){
					no.setRange(range);
					no.setId(sp_line[0]);
					no.setNode_x(sp_line[1]);
					no.setNode_y(sp_line[2]);
					//System.out.println(sp_line[0]);
				//}
				arraylist.add(no); 
			 }//while
			 //System.out.println(arraylist.get(29).getRange());
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//matrix(arraylist.get(2),arraylist.get(16));
 	//*************************************read the file and put node into the list************************//	
		for(int i=1;i<arraylist.size();i++){
			 node node1 = arraylist.get(i);
			for(int j=1;j<arraylist.size();j++){
				if(i==j){continue;}
				
			     node node2 = arraylist.get(j);
			     int neigh = matrix(node1,node2);
			    // System.out.println("the neigher"+neigh);
			     if(neigh == 1){
				  node1.getNeighbours().add(node2);
			     }//node1.getNeighbours().add(node2.getId());
			}
		 }//for1
		
		//***************************iterator every node find neighbours for each node*******************
		//test******
		 //neighbours = arraylist.get(1).getNeighbours();
		 
//		for(int i=0;i<arraylist.get(3).getNeighbours().size();i++){
//		 	 System.out.println( arraylist.get(3).getNeighbours().get(i).getId());
//		 }
		 
		 //matrix(arraylist.get(2),arraylist.get(29));
		 //System.out.println(matrix(arraylist.get(2),arraylist.get(29)));
		//*******
		//
		 //************************
		 //iterator every neighbour of i note, if i is the bighest one, selece itsself
		 //as MDR set state = MDR
		 //else go to hops calculation.
		 //*************************
		 ArrayList<node> neighbours;
		 int Rmax = 0;
		 Queue<node> queue;
		 //bian li every node from 1 to 30
		for(int i=1;i<arraylist.size();i++){
			//get node
			//max = i;
			int[] hops;
			node node_temp = arraylist.get(i);
			neighbours = node_temp.getNeighbours();
			Rmax = findMaxInNeighbours(node_temp);
			if(Rmax<i){
				//******************************select itself as MDR*************** 
				node_temp.setState("MDR");
				//initial hops, actually no hops here, because it select itself as MDR
				hops = new int[31];
				for(int ini=0;ini<hops.length;ini++){
					hops[ini] = -1;
				}
				//store initial hops
				node_temp.setA(hops);
			}
			//**********************************hops algorithm************************
			else{
				//rmax
				node rmax = arraylist.get(Rmax);
     			//System.out.println("fffff"+Rmax);
				//initial hops
		    	hops = new int[31];
		    	for(int h=0;h<hops.length;h++){
		    		if(h == Rmax){
		    			hops[h] = 0;
		    		}
		    		else{
		    			hops[h] = 1000;
		    		}
		    	}
		    	//test hops
//		    	for(int t=0;t<hops.length;t++){
//		    		System.out.println("hops: "+ t +" "+hops[t]);
//		    	}

		    	//set queue for BSF algorithm
		    	queue = new LinkedList<node>();
		     	queue.offer(rmax);
			    node m;
			    node v_i;
			    while((m = queue.poll())!=null){
		    		for(int v=0;v<node_temp.getNeighbours().size();v++){
		    			   int m_id = Integer.parseInt(m.getId());
		    			   v_i = node_temp.getNeighbours().get(v);
		    			   int v_i_id = Integer.parseInt(v_i.getId());
		    			   int cost_result = c(m,node_temp,v_i);
			     		   if(cost_result==1){
			     		      //System.out.println("fuck"+hops[Integer.parseInt(v_i.getId())]);
			     			   if(hops[v_i_id]>hops[m_id]+1){
			     				   hops[v_i_id] = hops[m_id]+1;
			     				  // System.out.println(hops[v_i_id]);
			     				   queue.offer(v_i);
			     			   }
			     		   }//first if
				    }
		    	}
			    
			    //test changed hops
//			    for(int t=0;t<hops.length;t++){
//		    		System.out.println("hops: "+ t +" "+hops[t]);
//		    	}
//			    System.out.println("****************** "+i);
			  
			    //store hops to int[] a 
			    node_temp.setA(hops);
			   
			    //set MDR level
			    for(int f=0;f<neighbours.size();f++){
			    	node neighbor_hops = neighbours.get(f);
			    	int index =Integer.parseInt(neighbor_hops.getId());
			    	if(hops[index]>3){
			    		node_temp.setState("MDR");
			    		//add dependent neighbor
			    		
			    		break;
			    	}
			    	else{
			    		node_temp.setState("no_MDR");
			    	}
			    }
			    
	    	}//end of "else" for select hops algorithm
		}//end MDR select
		//****************************
		
		//***************************set for dp algorithm**********************
		for(int dp=1;dp<arraylist.size();dp++){
			int []hops = arraylist.get(dp).getA();
			node current = arraylist.get(dp);
			//System.out.println("hops"+hops[30]);
			for(int nei=0;nei<current.getNeighbours().size();nei++){
				node current_neighbour = current.getNeighbours().get(nei);
				int index = Integer.parseInt(current_neighbour.getId());
				if(hops[index]>3 && arraylist.get(index).getState().equals("MDR")){
					current.getDependent_neighbors().add(arraylist.get(index));
				}
				if(hops[index]==0){
					current.getDependent_neighbors().add(arraylist.get(index));
				}
			}
		}
		
		//now we have found all MDR and no MDR nodes with DN.
//		for(int i=1;i<arraylist.size();i++){
//			if(arraylist.get(i).getState().equals("MDR")){
//				System.out.println(arraylist.get(i).getId()+" ");
//				for(int nei=0;nei<arraylist.get(i).getDependent_neighbors().size();nei++){
//				System.out.println("DN "+arraylist.get(i).getDependent_neighbors().get(nei).getId());
//				}	
//			}
//		}
//		System.out.println();
		
		//finally we should find no_MDR's parent; 
		//*******************tomorrow*********************
		for(int i=1;i<arraylist.size();i++){
			if(arraylist.get(i).getState().equals("no_MDR")){
			  //no mdr node
			  node current_no_mdr = arraylist.get(i);
			  double distance = Double.parseDouble(current_no_mdr.getRange());
			  //System.out.println("hhhhhhhhdhddhdhdhdh"+distance);
			  for(int nei=0;nei<current_no_mdr.getNeighbours().size();nei++){
				 //no mdr's neighbor node 
				 node current_neigh = current_no_mdr.getNeighbours().get(nei);
				 if(current_neigh.getState().equals("MDR")){
					 double rel = distance_compare(current_no_mdr,current_neigh);
					 if(rel<distance){distance = rel; current_no_mdr.setParent(current_neigh);}
				 }
			  }
		    }
			else if(arraylist.get(i).getState().equals("MDR")){
				node current_mdr = arraylist.get(i);
				current_mdr.setParent(current_mdr);
			}
		}
		//System.out.println("2 d parent: .... "+arraylist.get(4).getParent().getId());
		
		//output here.......
		System.out.println("NodeID"+"\t"+"x"+"\t"+"y"+"\t"+"MDR"+"\t"+"Connection");
		for(int i=1;i<arraylist.size();i++){
			node current = arraylist.get(i);
			//1
			System.out.print(current.getId()+"\t");
			//2
			System.out.print(current.getNode_x()+"\t");
			//3
			System.out.print(current.getNode_y()+"\t");
			
			//4
			if(current.getState().equals("MDR")){
				System.out.print(1+"\t");
			}
			else{
				System.out.print(0+"\t");
			}
			//5
			
			
			//7
			if(current.getState().equals("MDR")){
				for(int j=0;j<current.getDependent_neighbors().size();j++){
					System.out.print(current.getDependent_neighbors().get(j).getId()+",");
					
				}
			}else{
				System.out.print(current.getParent().getId()+"\t");
			}
			System.out.println();
		}
	}
	
	public static int matrix(node no1, node no2){
		double x1 = Double.parseDouble(no1.getNode_x());
		double x2 = Double.parseDouble(no2.getNode_x());
		//System.out.println("node1 "+x1);
		//System.out.println("node2 "+x2);
		double y1 = Float.parseFloat(no1.getNode_y());
		double y2 = Float.parseFloat(no2.getNode_y());
		//System.out.println("node1_y "+y1);
		//System.out.println("node2_y "+y2);
		int neigh = 0;
		
		double dis = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		//System.out.println("dis is=: "+ dis);
		int range1 = Integer.parseInt(no1.getRange());
		double range2 = Double.parseDouble(no2.getRange());
		//System.out.print("&&&&&&&&&&&+"+range1);
		
		if(dis<range1){
			neigh = 1;
		}
		else{
			neigh = 0;
		}
		return neigh;
	}
	
	public static int findMaxInNeighbours(node no){
		ArrayList<node> neighs = no.getNeighbours();
		int Rmax=0;
		int length = neighs.size();
		for(int i=0;i<length;i++){
			int record = Integer.parseInt(neighs.get(i).getId());
			if(record>Rmax){
				Rmax = record;}
		}
		return Rmax;
	}
	
	public static int c(node u,node i,node v){
		
		//Hashtable<String, String> cost = new Hashtable<String, String>();
		
		int u_id = Integer.parseInt(u.getId());
		int i_id = Integer.parseInt(i.getId());
		//int v_id = Integer.parseInt(v.getId());
		int re = matrix(u,v);
		if(u_id>i_id && re==1){
			return 1;
		}
		else{
			return  0;
		}
		
	}
	
	public static double distance_compare(node i,node m){
		double distance;
		double x_i = Double.parseDouble(i.getNode_x());
		double y_i = Double.parseDouble(i.getNode_y());
		double x_m = Double.parseDouble(m.getNode_x());
		double y_m = Double.parseDouble(m.getNode_y());
		
		distance = Math.sqrt((x_i-x_m)*(x_i-x_m)+(y_i-y_m)*(y_i-y_m));
		return distance;
	}
}
