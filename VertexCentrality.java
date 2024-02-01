/** Vertex Centrality
 * 
 * Problem: Calculate the vertex centrality of each point v in an weighted directed graph, using the formula
 * for centrality defined as the sum of (n-1)/d(u,v) for each vertex other than v, where d(u,v) is the smallest
 * distance or weight from vertex u to vertex v. n is the number of vertices. Then return the vertex with the 
 * largest centrality value. 
 * 
 * Input Format: The first line is "N M", where there are N vertices and M edges.
 * The following M lines are "U V W", with each positive integer representing the origin, destination, and weight
 * of each edge. There are no self-loops or duplicate edges.
 * 
 * Output Format: A single integer representing the vertex with largest vertex centrality.
 * 
 * Example input with 20 vertices and 9 edges: 
    java VertexCentrality
    20 9
    2 19 82
    14 11 42
    9 18 65
    12 15 80
    1 10 75
    5 16 44
    6 0 53
    17 13 98
    7 4 69
 * 
 * Expected output:
    11
 * 
**/

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class VertexCentrality {
    int vertices;
    LinkedList<Edge> adjList[]; // index is destination vertex
    
    static class Edge {
        int ori; int dest; int wt; // origin, destination, and weight
        public Edge(int o, int d, int w) {
            this.ori = o;
            this.dest = d;
            this.wt = w;
        }
    }
    
    @SuppressWarnings("unchecked")
    public VertexCentrality(int v) {
        vertices = v;
        adjList = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adjList[i] = new LinkedList<Edge>();
        }
    }

    public void addWeightedEdge(int curOrigin, int curDestination, int curWeight) {
        adjList[curDestination].add(new Edge(curOrigin, curDestination, curWeight));
    }

    // calculates and returns a closeness centrality value given a destination vertex
    private double calcCC(int dest) {
        double[] dist = new double[vertices];
        boolean[] inQueue = new boolean[vertices];
        double cc = 0;
        for (int i = 0; i < vertices; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
        }
        dist[dest] = 0;
        
        Queue<Integer> todo = new LinkedList<Integer>();
        todo.add(dest);
        inQueue[dest] = true;
        while (!todo.isEmpty()) {
            int temp = todo.poll();
            inQueue[dest] = false;
            for (Edge i : adjList[temp]) {
                if (dist[i.ori] > dist[i.dest] + i.wt) {
                    dist[i.ori] = dist[i.dest] + i.wt; 
                    if (!inQueue[i.ori]) {
                        todo.add(i.ori);  
                        inQueue[dest] = true;
                    }
                }
            }
        }
        
        for (int i = 0; i < vertices; i++) {
            if (dist[i] > 0) {
                cc += (vertices-1) / dist[i];
            }
        }
        return cc;
    }
        
    public int mostInfluentialVertex() {
        int result = 0;
        double biggestCC = 0;
        for (int i = 0; i < vertices; i++) {
            double temp = calcCC(i);
            if (temp > biggestCC) {
                result = i; 
                biggestCC = temp;
            }
        }        
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int numVertices = Integer.parseInt(firstMultipleInput[0]);
        int numEdges = Integer.parseInt(firstMultipleInput[1]);
        VertexCentrality result = new VertexCentrality(numVertices);

        IntStream.range(0, numEdges).forEach(numEdgesItr -> {
            try {
                String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
                int curOrigin = Integer.parseInt(secondMultipleInput[0]);
                int curDestination = Integer.parseInt(secondMultipleInput[1]);
                int curWeight = Integer.parseInt(secondMultipleInput[2]);

                result.addWeightedEdge(curOrigin, curDestination, curWeight);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        System.out.println(result.mostInfluentialVertex());
        bufferedReader.close();
    }
}