
import java.io.*; 
import java.util.Scanner;
import java.util.Stack;
 
class ShowFile { 
	static double stDis[][] = new double [601][601];
	static double demands[] = new double [600];
	static boolean isIncluded[] = new boolean[601];
	static boolean isVisited[] = new boolean[601];
	static Stack<Integer> visitedStack = new Stack<Integer>();
	
  public static void main(String args[])  
    throws IOException 
  {  
    FileInputStream fin; 
 
    /*try { 
      fin = new FileInputStream("New Text Document.txt"); 
    } catch(FileNotFoundException e) { 
      System.out.println("File Not Found"); 
      return; 
    } catch(ArrayIndexOutOfBoundsException e) { 
      System.out.println("Usage: ShowFile File"); 
      return; 
    } 
 
    // read characters until EOF is encountered 
    do { 
      i = fin.read();
      if(i != -1) System.out.print((char) i); 
    } while(i != -1); 
 
    fin.close();*/
    Scanner scanner1 = new Scanner(new File("t2.txt"));
    //int [] tall = new int [100];
    //i = 0;
    int lCnt = -1;
    double sAmt = 0; 
    while(scanner1.hasNext()){
    	String s = scanner1.next();
    	if(s.length() == 0)
    		continue;
    	else if(s.length() >= 4)
    		lCnt++;
    	else if(!s.startsWith("-") && s != null && !s.startsWith("DC"))
    	{
    		double d = Double.parseDouble(s);
    		demands[lCnt] = d;
    		//System.out.println(Double.parseDouble(s));
    		sAmt += d;
    	}
    }
    
    Scanner scanner2 = new Scanner(new File("New Text Document.txt"));
    
    
    scanner2.next();
    int i = 0;
    int j = 0;
    while(scanner2.hasNext()){
    	String s = scanner2.next();
    	if(s.length() == 0)
    		continue;
    	else if(s.length() >= 4);
    		//lCnt++;
    	else if(!s.startsWith("-") && s != null && !s.startsWith("DC"))
    	{
    		double d = Double.parseDouble(s);
    		stDis[i][j] = d;
    		//System.out.println(Double.parseDouble(s));
    		//sAmt += d;
    		j++;
    	}
    	else if(s.startsWith("-"))
    		stDis[i][j++] = 0;
    	
    	if(j == 601)
    	{
    		i++;
    		//System.out.println(i);
    		j = 0;
    	}
    }
    
    /*for( i = 0; i < 601; i++)
    {
    	for(j = 0; j < 601; j++)
    	{
    		System.out.print(stDis[i][j] + " " );
    	}
    	System.out.println();
    }*/
    //System.out.println(sAmt + " , " + lCnt);
    findMinCost();
  } 
  
  public static void findMinCost()
  {
	  
	  double load = 0;
	  double kmTraversed = 0;
	  int no_of_stops = 0;
	  boolean flag = true;
	  for( int i = 0; i < 601; i++)
	  {
		  isIncluded[i] = false;
		  isVisited[i] = false;
	  }
	  while(flag)
	  {
		  visitedStack.push(600);
		  while( !visitedStack.isEmpty() )
		  {
			  int v = getMinUnVisited( visitedStack.peek() );
		  
			  if(  v == 600 && no_of_stops == 20 )
				  break;
			  else if( kmTraversed > 65 || load > 1000 || no_of_stops >= 20 || v == 600 )
			  {
				  int lst_clr = visitedStack.pop();
				  no_of_stops--;
				  if( lst_clr < 600 )
					  load -= demands[lst_clr];
				  kmTraversed -= stDis[visitedStack.peek()][lst_clr];
			  }
			  else if( no_of_stops == 19 )
			  {
				  visitedStack.push(600);
				  break;
			  }
			  else
			  {
				  isVisited[v] = true;
				  no_of_stops++;
				  if( v  < 600 )
					  load += demands[v];
				  kmTraversed += stDis[visitedStack.peek()][v];
				  visitedStack.push(v);
			  }
		  //System.out.println( v );
		  }
	  
		  Stack<Integer> printStack = new Stack<Integer>();
		  while( !visitedStack.isEmpty() )
		  {
			  printStack.push(visitedStack.pop());
		  }
		  while( !printStack.isEmpty() )
		  {
			  System.out.println( printStack.pop());
		  }
		  for( int i = 0; i < 601; i++ )
		  {
			  if( !isIncluded[i] )
			  {
				  flag = true;
				  break;
			  }
			  else
				  flag = false;
		  }
	  }
	  System.out.println( kmTraversed );
  }
  
  public static int getMinUnVisited( int i )
  {
	  
	  Double minDis = Double.MAX_VALUE;
	  int neededVart = 0;
	  for( int j = 0; j < 601; j++ )
	  {
		  if( stDis[i][j] > 0 && !isVisited[j] && !isIncluded[j] && stDis[i][j] < minDis)
		  {
			  minDis = stDis[i][j];
			  neededVart = j;
		  }
	  }
	  return neededVart;
  }
}