import java.util.*;

class Edge {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}
class DisjointSet {
    int[] parent, rank;

    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int u) {
        if (parent[u] != u) {
            parent[u] = find(parent[u]); 
        }
        return parent[u];
    }

    public void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU != rootV) {
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }
}

public class  kruskaltime {
    public static List<Edge> kruskal(int n, List<Edge> edges) {
        List<Edge> mst = new ArrayList<>();
        DisjointSet ds = new DisjointSet(n);
        Collections.sort(edges, Comparator.comparingInt(edge -> edge.weight));

        for (Edge edge : edges) {
            int u = edge.src;
            int v = edge.dest;
            if (ds.find(u) != ds.find(v)) {
                ds.union(u, v);
                mst.add(edge); 
            }
        }
        return mst;
    }

    public static List<Edge> generateRandomCompleteGraph(int n) {
        Random random = new Random();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { 
                int weight = random.nextInt(100) + 1; 
                edges.add(new Edge(i, j, weight));
            }
        }
        return edges;
    }
    public static void main(String[] args) {
        int[] vertexSizes = {1000,2000,3000,4000,5000}; 
        for (int n : vertexSizes) {
            List<Edge> edges = generateRandomCompleteGraph(n);
            long startTime = System.nanoTime();
            List<Edge> mst = kruskal(n, edges);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("n: " + n + ", Edges: " + edges.size() + ", Time taken: " + duration + " nanoseconds");
        }
    }
}
