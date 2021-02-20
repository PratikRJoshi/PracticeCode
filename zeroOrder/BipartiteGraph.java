package zeroOrder;

public class BipartiteGraph {
    public static boolean isBipartite(int[][] graph) {

        // 0 = not visited
        // 1 = visited and red
        // -1 = visited and blue
        int[] visited = new int[graph.length];

        for (int i = 0; i < graph.length; i++) {
            if (visited[i]  == 0 && !dfs(graph, visited, i, 1)){
                return false;
            }
        }
        return true;

    }

    private static boolean dfs(int[][] graph, int[] visited, int vertex, int colour) {
        if (visited[vertex] != 0){
            return visited[vertex] == colour;
        }

        visited[vertex]  = colour;
        for (int v : graph[vertex]){
            if (!dfs(graph, visited, v, -colour)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        int[][] graph = new int[][]{{1,2,3}, {0,2}, {0,1,3}, {0,2}};
        int[][] graph = new int[][]{{1,3}, {0,2}, {1,3}, {0,2}};
        System.out.println(isBipartite(graph));
    }
}
