
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DrSinji
 */
public class W9_10ReeseMcCoyKruskal {
    
    public static void main(String[] args) {
        
        ArrayList<Edge> graph[] = new ArrayList[7]; //creates graph for the 7 nodes      
        Edge edges[] = new Edge[11]; //used to create the 11 edges
        
        for (int i = 0; i < 7; i++) //used to be able to store multiple edges for each node
            graph[i] = new ArrayList<>();

        graph[0].add(new Edge(0, 1, 7)); //Creates the graph (from, end, weight)
        graph[0].add(new Edge(0, 3, 5));
        graph[1].add(new Edge(1, 0, 7));
        graph[1].add(new Edge(1, 2, 8));
        graph[1].add(new Edge(1, 4, 7));
        graph[1].add(new Edge(1, 3, 9));
        graph[2].add(new Edge(2, 1, 8));
        graph[2].add(new Edge(2, 4, 5));
        graph[3].add(new Edge(3, 0, 5));
        graph[3].add(new Edge(3, 1, 9));
        graph[3].add(new Edge(3, 4, 15));
        graph[3].add(new Edge(3, 5, 6));
        graph[4].add(new Edge(4, 2, 5));
        graph[4].add(new Edge(4, 1, 7));
        graph[4].add(new Edge(4, 3, 15));
        graph[4].add(new Edge(4, 5, 8));
        graph[4].add(new Edge(4, 6, 9));
        graph[5].add(new Edge(5, 3, 6));
        graph[5].add(new Edge(5, 4, 8));
        graph[5].add(new Edge(5, 6, 11));
        graph[6].add(new Edge(6, 5, 11));
        graph[6].add(new Edge(6, 4, 9));
        
        edges[0] = new Edge(0, 1, 7); //Stores edges in graph
        edges[1] = new Edge(0, 3, 5);
        edges[2] = new Edge(1, 3, 9);
        edges[3] = new Edge(1, 2, 8);
        edges[4] = new Edge(1, 4, 7);
        edges[5] = new Edge(2, 4, 5);
        edges[6] = new Edge(3, 4, 15);
        edges[7] = new Edge(3, 5, 6);
        edges[8] = new Edge(4, 5, 8);
        edges[9] = new Edge(4, 6, 9);
        edges[10] = new Edge(5, 6, 11);
        
        MST(graph, edges); //calls the minimum spanning tree method, sends the graph and edges that were created
    }
    
    static class Subset { //used to be accessed by multiple methods, to find cycles
        int parent;
        int rank;
    }
    
    static class Edge { //defines inner nested methods through outer class of edge     
        int from; //start
        int to; //end
        int weight; //weight
        
        public Edge(int from, int to, int weight) { //initilizes constructor to edge object 
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        public static final Comparator<Edge> comp = new Comparator<Edge>() {
            @Override
            public int compare(Edge one, Edge two) { //compare edge weights and sort
                return Integer.compare(one.weight, two.weight); 
            }
        };
    }
        
    private static int find(Subset subsets[], int i) { //used to find set of vertex i

        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent); //
        return subsets[i].parent;
    }
    
    private static void Union(Subset subsets[], int x, int y) { //finds like elements between two sets, union by rank
        
        int u = find(subsets, x); //through path compression, pg 633, finds subsets of both sets
        int v = find(subsets, y);

        if (subsets[u].rank < subsets[v].rank) { //if v rank bigger, make root
            subsets[u].parent = v;
        } else if (subsets[u].rank > subsets[v].rank) { //if u rank bigger, make root
            subsets[v].parent = u;
        } else {
            subsets[v].parent = u; //if rank same, make root and increment by 1
            subsets[u].rank++;
        }
    }
    private static void MST(ArrayList<Edge> graph[], Edge edges[]) {
        
        Arrays.sort(edges, Edge.comp); //sorts the edges based on their weights
        Edge mst[] = new Edge[graph.length - 1]; //stores the sorted mst in graph
        
        for (int i = 0; i < graph.length - 1; i++) { //initializes edges
            mst[i] = new Edge(-1, -1, -1);
        }
              
        Subset subsets[] = new Subset[graph.length]; //creates v subsets of vertices for edges
        for (int i = 0; i < graph.length; i++) {
            subsets[i] = new Subset();
        }

        for (int i = 0; i < graph.length; i++) { //sets parent and rank of itself
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }
        
        int edgeCount = 0; //initialize number of edges as counter
        for (int i = 0; i < edges.length; i++) { //traverses all edges

            int parent = find(subsets, edges[i].from); //finds parent vertex edge
            int child = find(subsets, edges[i].to); //finds child vertex edge

            if (parent != child) { //if no cycle found between parent and child vertex, add edge to mst. Otherwise discard "cycled" edge
                mst[edgeCount].from = edges[i].from;
                mst[edgeCount].to = edges[i].to;
                mst[edgeCount].weight = edges[i].weight;
                Union(subsets, parent, child);
                edgeCount++;
            }
            
            if (edgeCount == graph.length - 1) { //confirms all verticies have been checked
                break;
            }
        }
        
        for (int i = 0; i < graph.length - 1; i++) { //outputs the minimum spanning tree
            System.out.println("Vertex " + mst[i].from + " to " + mst[i].to + " has a weight of " + mst[i].weight);
        }
    }
}