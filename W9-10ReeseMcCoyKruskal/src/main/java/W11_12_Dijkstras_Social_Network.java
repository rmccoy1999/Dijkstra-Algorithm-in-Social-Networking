
import java.lang.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DrSinji
 */

public class W11_12_Dijkstras_Social_Network {
    
    int initial_vertex = 6; 

    private void Social_Network(int graph[][], int source) { //implements adjacent matrix
        int path[] = new int[initial_vertex]; //path[i] array that is printed
  
        Boolean shortestPath[] = new Boolean[initial_vertex]; //will be true if vertex i is shortest path from source to i
  
        for (int v = 0; v < initial_vertex; v++) { 
            path[v] = Integer.MAX_VALUE; //initializes values to infinity
            shortestPath[v] = false; 
        } 
        path[source] = 0; //source distance
  
        for (int iteration = 0; iteration < initial_vertex-1; iteration++) { //finds the shortest path for all verticies
            int s = shortestPath(path, shortestPath); //always equal to source since verticies haven't been checked yet 
            shortestPath[s] = true; //sets checked vertex as done
  
            for (int v = 0; v < initial_vertex; v++) { 
                if (!shortestPath[v] && graph[s][v] != 0 && path[s] != Integer.MAX_VALUE && path[s] + graph[s][v] < path[v]) {
                    path[v] = path[s] + graph[s][v]; //updates path only if not in shortestPath. Total weight of path from source to vertex is smaller then current value of path
                }
            }
        } 
        printer(path); //prints array
    }  
    
    private int shortestPath(int path[], Boolean subSet[]) { 
        int minimum = Integer.MAX_VALUE, minimum_index = -1; //sets minimum value
  
        for (int vertex = 0; vertex < initial_vertex; vertex++) 
            if (subSet[vertex] == false && path[vertex] <= minimum) { 
                minimum = path[vertex]; 
                minimum_index = vertex; 
            } 
        return minimum_index; 
    } 
  
    private void printer(int path[]) { 
        System.out.println("Vertex \t\t Distance from Source"); 
        for (int i = 0; i < initial_vertex; i++) 
            System.out.println(i + " \t\t " + path[i]); //prints the path of array
        relationGuess(path);
        
    }
    
    private void relationGuess(int path[]) {
        int smallest = Integer.MAX_VALUE;
        for(Integer element : path){
            if((smallest>element) && (element!= 0)) {
                smallest = element; //finds smallest value or shortest path out of all verticies               
            } 
        }
        System.out.println("Are you related to " + smallest + "?"); //based on shortest path value, it would predict if someone is related to you
        interests(path);
    }
    
    private void interests(int path[]) { //this is where a 3rd dimension comes into play since its an additional depth of data
        ArrayList<String> interest = new ArrayList<>();
        for(Integer element : path){ //element trying to find hobby
           if(element == 7) { //for simplicity, 7 represents an interest say "hunting". Since there is a match, it would reccomend similar products affiliate based (tree stand), or groups/organizations (Izaak Walton League, for example
               interest.add("Hunting");
               System.out.println("Sponsored: We thought this would interest you. " + interest.toString()); //this would suggest a related advertisement
           } 
        }   
    }

    public static void main(String[] args) { 
        //creates the graph
        int networkGraph[][] = new int[][] {{0, 3, 0, 2, 0, 5}, 
                                           {9, 0, 4, 10, 0, 0}, 
                                           {0, 8, 0, 7, 0, 4}, 
                                           {6, 0, 6, 0, 0, 15}, 
                                           {0, 0, 18, 0, 3, 13}, 
                                           {0, 19, 14, 0, 1, 0}}; 
        W11_12_Dijkstras_Social_Network f = new W11_12_Dijkstras_Social_Network(); 
        f.Social_Network(networkGraph, 0);     
    }
} 
