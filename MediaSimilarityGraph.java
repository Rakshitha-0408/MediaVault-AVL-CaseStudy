

import java.util.*;

public class MediaSimilarityGraph {
    private int totalVertices;
    private LinkedList<Integer>[] adjList;
    private Map<Integer, String> contentTitles;

    @SuppressWarnings("unchecked")
    public MediaSimilarityGraph(int totalVertices) {
        this.totalVertices = totalVertices;
        this.adjList = new LinkedList[totalVertices];
        for (int i = 0; i < totalVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
        this.contentTitles = new HashMap<>();
    }

    public void addMediaNode(int id, String title) {
        contentTitles.put(id, title);
    }

    public void addSimilarityEdge(int src, int dest) {
        adjList[src].add(dest);
        adjList[dest].add(src); 
    }

    public void executeBFSRecommendation(int startNodeId) {
        System.out.println("\n--- Initiating BFS Discovery from: " + contentTitles.get(startNodeId) + " ---");
        boolean[] visited = new boolean[totalVertices];
        Queue<Integer> queue = new LinkedList<>();

        visited[startNodeId] = true;
        queue.add(startNodeId);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.println("Discovered Content Option -> ID: " + currentNode + ", Title: " + contentTitles.get(currentNode));

            for (int neighbor : adjList[currentNode]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    public void executeDFSClusterAnalysis(int startNodeId) {
        System.out.println("\n--- Initiating DFS Cluster Isolation from: " + contentTitles.get(startNodeId) + " ---");
        boolean[] visited = new boolean[totalVertices];
        dfsRecursive(startNodeId, visited);
    }

    private void dfsRecursive(int node, boolean[] visited) {
        visited[node] = true;
        System.out.println("Explored Cluster Node -> ID: " + node + ", Title: " + contentTitles.get(node));

        for (int neighbor : adjList[node]) {
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("GRAPH TRANSVERSAL INITIALIZATION (MediaVault Recommendation Network)");
        MediaSimilarityGraph graph = new MediaSimilarityGraph(5);

        graph.addMediaNode(0, "Sci-Fi: Interstellar");
        graph.addMediaNode(1, "Sci-Fi: Inception");
        graph.addMediaNode(2, "Sci-Fi: The Matrix");
        graph.addMediaNode(3, "Drama: The Godfather");
        graph.addMediaNode(4, "Drama: Scarface");

        graph.addSimilarityEdge(0, 1); 
        graph.addSimilarityEdge(1, 2); 
        graph.addSimilarityEdge(3, 4); 

        graph.executeBFSRecommendation(0);
        graph.executeDFSClusterAnalysis(3);

        System.out.println("\nProcess finished with exit code 0");
    }
}
