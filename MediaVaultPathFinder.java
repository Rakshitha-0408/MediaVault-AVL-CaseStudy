import java.util.*;

public class MediaVaultPathFinder {
    private static final int NO_PARENT = -1;

    public static void computeShortestPath(double[][] graph, int startVertex, String[] nodeNames) {
        int nVertices = graph[0].length;
        double[] shortestDistances = new double[nVertices];
        boolean[] added = new boolean[nVertices];

        Arrays.fill(shortestDistances, Double.MAX_VALUE);
        shortestDistances[startVertex] = 0.0;

        int[] parents = new int[nVertices];
        Arrays.fill(parents, NO_PARENT);

        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            double shortestDistance = Double.MAX_VALUE;
            
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            if (nearestVertex == -1) break;
            added[nearestVertex] = true;

            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                double edgeDistance = graph[nearestVertex][vertexIndex];
                
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }

        printResult(startVertex, shortestDistances, parents, nodeNames);
    }

    private static void printResult(int startVertex, double[] distances, int[] parents, String[] names) {
        System.out.println("Computing optimal recommendation links from source: " + names[startVertex]);
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-32s%-32s%-32s\n", "Target Media Vertex", "Dissimilarity Edge Cost", "Optimal Discovery Path");
        
        for (int vertexIndex = 0; vertexIndex < distances.length; vertexIndex++) {
            if (vertexIndex != startVertex) {
                System.out.printf("%-32s%-32s", names[vertexIndex], distances[vertexIndex] + " Units");
                List<Integer> path = new ArrayList<>();
                constructPath(vertexIndex, parents, path);
                
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i) + (i == path.size() - 1 ? "" : " -> "));
                }
                System.out.println();
            } else {
                System.out.printf("%-32s%-32s%-32s\n", names[vertexIndex], "0.0 Units", startVertex);
            }
        }
    }

    private static void constructPath(int currentVertex, int[] parents, List<Integer> path) {
        if (currentVertex == NO_PARENT) {
            return;
        }
        constructPath(parents[currentVertex], parents, path);
        path.add(currentVertex);
    }

    public static void main(String[] args) {
        System.out.println("SHORTEST PATH ENGINE INITIALIZATION (MediaVault Recommendation Routing)\n");
        
        String[] contentNames = {
            "Node 0 (Sci-Fi Base)", 
            "Node 1 (Cyberpunk Thriller)", 
            "Node 2 (Space Opera Space)", 
            "Node 3 (Retro AI Classic)", 
            "Node 4 (Dystopian Anthology)"
        };

        double[][] similarityMatrix = {
            {0, 4, 0, 0, 0},
            {0, 0, 3, 8, 0},
            {0, 0, 0, 0, 3},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };

        computeShortestPath(similarityMatrix, 0, contentNames);
        System.out.println("\nProcess finished with exit code 0");
    }
}