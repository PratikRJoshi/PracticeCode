package zeroOrder;

import java.util.*;

public class GraphValidTree {

    public boolean validTree(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        boolean[] visited = new boolean[n];

        for(int i = 0; i < n; i++){
            graph.add(new ArrayList<>());
        }

        for(int[] edge : edges){
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        visited[0] = true;

        while(!q.isEmpty()){
            int current = q.poll();
            for(int i : graph.get(current)){
                if(visited[i]){
                    continue;
                } else {
                    visited[i] = true;
                    q.offer(i);
                }
            }
        }

        for(boolean v : visited){
            if(!v)
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {{0,1},{0,2},{0,3},{1,4}};
        GraphValidTree graphValidTree = new GraphValidTree();
        System.out.println(graphValidTree.validTree(n, edges));
    }
}
