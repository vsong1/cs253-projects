/** Tall Trucks
 * 
 * Note: This is a version of a shortest path problem.
 * Problem: There are N cities, and you want to send trucks from your factory (in city 1) to the other 
 * cities. There are M two-way roads connecting pairs of cities. Each road has a maximum truck height, 
 * determined by bridges and tunnels along the way. For each city V, determine the maximum truck height 
 * that can travel the entire distance from city 1 to city V. Assume that route length is not a concern. 
 * 
 * Input Format: The first line is "N M", where there are N cities and M roads between cities.
 * The following M lines are "U V H", where cities U and V are represented as positive integers <= N, and 
 * H is the maximum height of the road.
 * 
 * Output Format: "A B C ..", where there are N-1 integers separated by spaces, and each integer represents 
 * the maximum height between city 1 and every other city.
 * 
 * Below is an example input with 3 roads between 4 cities: 
    java TruckRoutes
    4 3
    1 2 72
    1 4 96
    2 4 80
 * 
 * Expected output:
    80 0 96
 * 
**/

import java.util.*;
import java.util.Scanner;

public class TruckRoutes {
    static class Edge {
        int to; int height;
        public Edge(int t, int h) {
            this.to = t;
            this.height = h;
        }
    }
    
    static class HeightComparator implements Comparator<Edge> {
        public int compare(Edge e1, Edge e2) {
            if (e1.height < e2.height) return 1;
            else if (e1.height > e2.height) return -1;
            else return 0;
        }
    }
    
    public static int[] tallestTrucks(LinkedList<Edge>[] edges, int l) {
        int[] maxHeight = new int[l];
        PriorityQueue<Edge> todo = new PriorityQueue<Edge>(new HeightComparator());
        
        todo.add(new Edge(1,0));
        while (!todo.isEmpty()) {
            Edge temp = todo.poll();
            // System.out.println(temp.to + " " + temp.height);          
            for (Edge i : edges[temp.to]) {
                if (i.to == 1) continue;
                else if (maxHeight[i.to] == 0 || (i.height>maxHeight[i.to] && (maxHeight[temp.to]>maxHeight[i.to] || temp.to == 1))) {
                    if (i.height < maxHeight[temp.to] || maxHeight[temp.to] == 0) maxHeight[i.to] = i.height; 
                    else maxHeight[i.to] = maxHeight[temp.to]; 
                    
                    if (todo.contains(i)) todo.remove(i);
                    todo.add(i);  
                }
            }
        }
        
        return maxHeight;
    }
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // Scanner input is fast enough for this problem
        Scanner sc = new Scanner(System.in);
        String[] line = sc.nextLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int M = Integer.parseInt(line[1]);
    
        LinkedList<Edge>[] adjList = new LinkedList[N+1]; 
        for (int i = 0; i < N+1; i++) {
            adjList[i] = new LinkedList<Edge>();
        }
        
        // there are N cities (1 through N) and M roads
        for (int m=0; m<M; ++m) {
            line = sc.nextLine().split(" ");
            int U = Integer.parseInt(line[0]);
            int V = Integer.parseInt(line[1]);
            int H = Integer.parseInt(line[2]);
            // notice road between U and V with height limit H

            adjList[U].add(new Edge(V, H));
            adjList[V].add(new Edge(U, H));
        }
        sc.close();
        
        int[] result = tallestTrucks(adjList, N+1);
        for (int i = 2; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        
    }
}