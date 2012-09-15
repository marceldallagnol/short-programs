/*Causa problema de memoria com grafos grandes. Rodar com
"java -Xmx768m -Xss128m SCC 'arquivo'". Arquivo deve ter 2
numeros por linha, correspondentes a extremidade de cada aresta
(direcionada). Computa os N_BIGGEST maiores SCCs do grafo e imprime
o total de SCCs*/

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class SCC
{
   private static int t;
   private static int[] nodeOrder;
   private static boolean[] markedNodes;
   private static intList[] dGraph, rGraph;
   
   public static void main(String[] args) throws Exception
	{
	   LineNumberReader file = null ;         //Read lines from file
      String line;                           //Current line
      final int NUM_NODES = 875714;
      final int N_BIGGEST = 5;
      intList current, SCCSizes;
      String[] edge;                   //Current line, divided
      int i,j, numSCC = 0;
      
      dGraph = new intList[NUM_NODES];
      rGraph = new intList[NUM_NODES];
      markedNodes = new boolean[NUM_NODES];
      nodeOrder = new int[NUM_NODES];
      for(i = 0; i < NUM_NODES; i++)
      {
         dGraph[i] = new intList();
         rGraph[i] = new intList();
      }
      SCCSizes = new intList();

      if(args.length != 1)                //Check for program input
      {
         System.out.println("Wrong input format! Input is 'java SCC file'.");
         System.exit(-1);
      }
      try                                 //Set up file reference
      {
         file = new LineNumberReader( new FileReader(new File(args[0])));
      }
      catch(FileNotFoundException e)      //Check for file name validity
      {
         System.out.println("File '" + args[0] + "' does not exist!");
         System.exit(-1);
      }

      line = file.readLine();
      /*Le arquivo, coloca as arestas no grafo direto e invertido*/
      while(line != null)
      {  
         edge = line.split(" +");
         dGraph[Integer.parseInt(edge[0])-1].add(Integer.parseInt(edge[1])-1);
         rGraph[Integer.parseInt(edge[1])-1].add(Integer.parseInt(edge[0])-1);
         line = file.readLine();
      }
      /*Primeira passada de DFS*/
      for(i = 0; i < NUM_NODES; i++)
      {
         if(!markedNodes[i])
         {
            reverseDFS(i);
         }
      }
      
      markedNodes = new boolean[NUM_NODES];
      /*Segunda passada de DFS, conta o numero em cada bloco
        (t) e o total (numSCC).*/
      for(i = NUM_NODES-1; i >= 0; i--)
      {
         t = 0;
         if(!markedNodes[nodeOrder[i]])
         {
            directDFS(nodeOrder[i]);
            SCCSizes.add(t);
            numSCC++;
         }
      }
      /*Rearranja e imprime os N_BIGGEST primeiros*/
      Arrays.sort(SCCSizes.list);
      for(i = SCCSizes.list.length-1; i > SCCSizes.list.length-N_BIGGEST-1; i--)
         System.out.println(SCCSizes.list[i]);
      System.out.println("\n" + numSCC);
	}
	/*Segundo DFS aplicado*/
   public static void directDFS(int node)
   {
      int i;
      
      t++;
      markedNodes[node] = true;
      for(i = 0; i < dGraph[node].size; i++)
      {
         if(!markedNodes[dGraph[node].list[i]])
            directDFS(dGraph[node].list[i]);
      }
   }
   /*Primeiro DFS aplicado*/
   public static void reverseDFS(int node)
   {
      int i;
      
      markedNodes[node] = true;
      for(i = 0; i < rGraph[node].size; i++)
      {
         if(!markedNodes[rGraph[node].list[i]])
            reverseDFS(rGraph[node].list[i]);
      }
      nodeOrder[t++] = node;
   }
}
/*Estrutura lista de ints*/
class intList
{
   public int size, capacity, number;
   public int[] list;
   private static final int LIST_BLOCK = 10;
   
   public intList()
   {
      list = new int[LIST_BLOCK];
      capacity = LIST_BLOCK;
   }
   
   public void add(int n)
   {
      if(size + 1 == capacity)
      {
         int[] auxList = new int[capacity + LIST_BLOCK];
         for(int i = 0; i < size; i++)
            auxList[i] = list[i];
         list = auxList;
         capacity += LIST_BLOCK;
      }
      list[size++] = n;
   }
}
