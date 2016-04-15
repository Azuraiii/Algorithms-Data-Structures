 // Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

import java.io.*;
import java.util.Scanner;

class Heap
{
    private int[] heapArr;	   // heap array
    private int[] heapPos;	   // hPos[h[k]] == k
    private int[] dist;    // dist[v] = priority of v

    private int heapSize;         // heap size
   
    // The heap constructor gets passed from the Graph:
    //    1. maximum heap size
    //    2. reference to the dist[] array
    //    3. reference to the hPos[] array
    public Heap(int maxSize, int[] _dist, int[] _heapPos) 
    {
        heapSize = 0;
        heapArr = new int[maxSize + 1];
        dist = _dist;
        heapPos = _heapPos;
    }

	//checking to see if heap is empty, is empty then heapsize is 0
    public boolean isEmpty() {	
        return heapSize == 0;
    }

	   	
	public void siftUP(int ele) {
			
			//take the vertex at element k (current position)
			int vertice = heapArr[ele];
			
			// Put dummy node at the top of the heap
			heapArr[0] = 0;
			//set distance 0 to be the smallest value to compare
			dist[0] = Integer.MIN_VALUE;
			
			// while the distance value at the element no we are at,
			// is less than distance value at element / 2
			// so dividing out way up the list to insert the element at the correct place
			while(dist[vertice] < dist[ heapArr[ele / 2] ]) {
				
				//if not found, then we sift up the value to next value 
				//in temp array called heapPostion 
				 heapArr[ele] = heapArr[ele / 2];
				 heapPos[ heapArr[ele] ] = ele;
				// then we divide the element by 2 to go to next level up
				 ele = ele / 2;
			}
			//once we exit the while loop
			// then we insert the vertex into the correct place
			//on the heap array
			heapArr[ele] = vertice;
			heapPos[vertice] = ele;
			
			//test();
		}
		
		
	public void test() {
		
		System.out.println("\n");
		for (int i = 0; i < heapArr.length; i++) {
			
			if ( heapArr[i] != 0) {
				System.out.print( toChar( heapArr[i]) );
				System.out.print(",");
			}
		}
		
	}
	
	private char toChar(int u) {
			return(char)(u + 64);
		}
		
		
		public void siftDown(int ele) {
			
			int vertice, j;
			vertice = heapArr[ele];
			j = 2 * ele;
			
			while(j <= heapSize) {
			
				if((j + 1 <= heapSize ) && dist[ heapArr[j] ] > dist[ heapArr[j + 1] ]) {
					//then go to next element
					j++;
				}
				// if the distance of the vertex element we are on is greater
				// than the vertex we are sifting down then we stop 
				if(dist[ heapArr[j] ] >= dist[vertice]) {
					break;
				}
				//if not, then we continue down the list
				heapArr[ele] = heapArr[j];
				ele = j;
			   j = ele * 2;
			}
			//once we found the right slot, where the next value is greater than 
			//the value we are sifting down
			heapArr[ele] = vertice;
			heapPos[vertice] = ele;
		}


    public void insert( int x) 
    {
		//adding new vertex to the heap array to the end of the array
		//then sift up the vertex to correct place
        heapArr[++heapSize ] = x;
        siftUP(heapSize); 
    }


    public int remove() 
    {   
		// to remove an element from array
		//put that element to 0 then sift down
        int vertice = heapArr[1];
        heapPos[vertice] = 0; // v is no longer in heap
        heapArr[heapSize + 1 ] = 0;  // put null node into empty spot
        
        heapArr[1] = heapArr[heapSize--];
        siftDown(1);
        
        return vertice;
    }

}

class Graph {
	
	public class Node {
		public Node next;
		public int vert;
		public int wgt;
	}
	
	
	
	private int Vertice, Edge;
	private Node[] adj;
	private Node z;
	private int[] mst;
		
		
	// default constructor
    public Graph(String graphFile)  throws IOException {
    	
    	int u, v;
       int e, wgt;
       Node t;

       FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	           
       String splits = " +";
		String line = reader.readLine();        
       String[] parts = line.split(splits);
       System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        Vertice = Integer.parseInt(parts[0]);
        Edge = Integer.parseInt(parts[1]);

        z = new Node(); 
        z.next = z;

        //visited = new int[V+1];
        adj = new Node[Vertice + 1];

        for(v = 1; v <= Vertice; ++v) 
		{
           adj[v] = new Node();               
        }

        System.out.println("Reading edges from text file");
        
        for(e = 1; e <= Edge; ++e) {

            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));    
            
            t = new Node();
            t.wgt = wgt;
            t.vert = v;
            t.next = adj[u].next;
            adj[u].next = t;

            t = new Node();
            t.wgt = wgt;
            t.vert = u;
            t.next = adj[v].next;
            adj[v].next = t;
        }

     }
	
	
	
	private char toChar(int u) {
		return(char)(u + 64);
	}
	
	
	 // method to display the graph representation
    public void display() {

        int v;
        Node n;
        
        System.out.println("");

        for(v = 1; v <= Vertice; ++v) {

            System.out.print("adj[" + toChar(v) + "]" );
            
            for(n = adj[v].next; n != null; n = n.next) {

                System.out.print(" -> | " + toChar(n.vert) + " | " + n.wgt + " |");    
            }

            System.out.println("");
        }
    }
	
	
	public int[] MST_Prim(int s) {
		
		int v, u;
		int wgt, wgtSum = 0;
		int[] dist, parent, hPos;
		Node t;
		
		// The distance from node to node
		dist = new int[Vertice + 1];
		
		
		// The parent node
		parent = new int[Vertice + 1];
		
		
		// Current heap position
		hPos = new int[Vertice + 1];
		
		
		for(v = 1; v<= Vertice; v++) {
			
			// Set the key(distance) to maximum int value
			dist[v] = Integer.MAX_VALUE;
			//init all elements to be 0
			parent[v] = 0;
			
			hPos[v] = 0;
		}
		
		//create a new heap with size of Vertice + 1
		Heap heap = new Heap(Vertice + 1, dist, hPos);
		//then insert the first element into the heap
		heap.insert(s);
		
		
		// Set distance to 0
		dist[s] = 0;

		
		while(!heap.isEmpty()) {
			// Remove from the heap
			v = heap.remove();
			//System.out.println("REMOVE");
			
			if (parent[v] != 0 ) {
				
				System.out.println("Adding edge " + toChar(parent[v]) + " - " + dist[v] + " - "+ toChar(v));
			}

			
			// Calculates the sum of the Weights
			wgtSum += dist[v];
			
			// Prevent duplicates
			dist[v] = -dist[v];
			
			
			//loop while parent node still has children
			for(t = adj[v].next; t != null; t = t.next) {
	
				//if the weight of the vertice we are on is less
				// than the value in the distance array at the elemnent of the vertice we are on
				if( t.wgt < dist[t.vert] ) {
					
					//then we add the weight that is smaller than the value in the distance
					//and add the connect node to the parent array
					dist[t.vert] = t.wgt;
					parent[t.vert] = v;
					
					// If the vertex is empty, insert the next vertex
					if(hPos[t.vert] == 0) {
						
						//System.out.println("INSERT");
						heap.insert(t.vert);
						
					} else {
						
						//System.out.println("SIFT UP");
						heap.siftUP(hPos[t.vert]);
						
					}
				}
			}
			
			
			System.out.println("\nWeight " + wgtSum);
			
			
		}
		//return the array parent which has all the connect edges
		return parent;
	}
	
	
	public void showMST(int[] mst) {
		
		System.out.println("Minimum spanning tree parent array is: ");
		
		//looping through the vertices and displaying the current vertice and what it is connected too
		for(int v = 1; v <= Vertice; ++v) {
			
			if (mst[v] != 0) {
				
				System.out.println(toChar(v) + "" + toChar(mst[v]));
			}
			
			
		}
	}
	
}

public class PrimLists {
	
    public static void main(String args[]) throws IOException {
		
		int[] mst;
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter in Text File");
		String fname = in.nextLine();
		
		System.out.println("Enter in Starting Vertex");
		int s = in.nextInt();
		
		Graph graph = new Graph(fname);
		
		graph.display();
		
		mst = graph.MST_Prim(s);
		
		graph.showMST(mst);
		
		System.out.println("Finished");
		
	}
    
    
}
