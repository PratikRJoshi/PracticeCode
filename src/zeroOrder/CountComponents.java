package zeroOrder;

public class CountComponents {
    private int countComponents(int n, int[][] edges) {
        /*UnionFind unionFind = new UnionFind(n);
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];

            unionFind.union(x, y);
        }

        return unionFind.getCount();*/
        return 0;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {{0, 1}, {1, 2}, {3, 4}};

        CountComponents countComponents = new CountComponents();
        int components = countComponents.countComponents(n, edges);
        System.out.println(components);
    }
}
