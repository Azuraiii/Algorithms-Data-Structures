class Heap {
  private
	int[] a, hPos;
	int N;
	static int maxH = 100;

// three constructors
public Heap() {
   N = 0;
   a = new int[maxH+1];
   hPos = new int[maxH+1];
}

public Heap(int size) {
   N = 0;
   a = new int[size + 1];
}

public void insert( int x) {
   a[++N] = x;
   hPos[x] = N;
   siftUp( N);
}

public void siftUp( int k) {
   int v = a[k];
   a[0] = Integer.MAX_VALUE;

   while( v > a[k/2]) {
      a[k] = a[k/2];
      hPos[a[k]] = k;
      k = k/2;
   }
   a[k] = v;
   hPos[v] = k;
}

public void siftDown( int k) {
 int v  = a[k];
     while(k <= N/2)//while the node at pos k has a left child numbers
     {
         System.out.println("siftDown: " + v);
         int j = 2*k;
         if( j < N || a[j] < a[j+1])
         {
             j++;
         }
         if(v >= a[j])
         {
             break;
         }
         a[k] = a[j];
         k = j;
    }
    a[k] = v;
    hPos[v] = k;
}//end siftDown

public int remove() {
   int v = a[1];
   a[1] = a[N--];
   hPos[v] = a[1];
   siftDown(1);
   System.out.println("Removing element: " + v);
   return v;
}

public boolean isEmpty()
   {
       if(N == 0)
           return true;
       else 
           return false;
   }

public void display() {
	
   System.out.println("\n\nThe tree structure of the heaps is:");
   System.out.println( a[1] );
   for(int i = 1; i<= N/2; i = i * 2) {
      for(int j = 2*i; j < 4*i && j <= N; ++j)
         System.out.print( a[j] + "  ");
       System.out.println();
   }
}

public static void main(String args[]) {

   Heap h = new Heap();
   int r; double x;

   // insert random numbers between 0 and 99 into heap
   for(int i = 1; i <= 20; ++i)  {
	  x =  (Math.random()*100.0);
      r = (int) x; 
      System.out.println("Inserting " + r);
      h.insert(r);
	  h.display();
   }
      
   //implement the remove and siftDown() methods
   while(!h.isEmpty())
   {
       h.display();
       h.remove();
   }
   
}

} // end of Heap class

