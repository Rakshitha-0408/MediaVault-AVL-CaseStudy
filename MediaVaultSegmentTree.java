

import java.util.Arrays;

public class MediaVaultSegmentTree {
    private double[] tree;
    private int n;

    public MediaVaultSegmentTree(double[] mediaMetrics) {
        this.n = mediaMetrics.length;
        this.tree = new double[4 * n];
        buildTree(mediaMetrics, 0, 0, n - 1);
    }

    private void buildTree(double[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
            return;
        }
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        buildTree(arr, leftChild, start, mid);
        buildTree(arr, rightChild, mid + 1, end);

        tree[node] = Math.max(tree[leftChild], tree[rightChild]);
    }

    public double queryRangeMax(int queryStart, int queryEnd) {
        System.out.println("Processing Interval Range Query for Window [" + queryStart + ", " + queryEnd + "]...");
        return queryRangeMaxRec(0, 0, n - 1, queryStart, queryEnd);
    }

    private double queryRangeMaxRec(int node, int start, int end, int qStart, int qEnd) {
        if (qStart <= start && qEnd >= end) {
            return tree[node];
        }
        if (end < qStart || start > qEnd) {
            return Double.NEGATIVE_INFINITY;
        }
        int mid = start + (end - start) / 2;
        double leftMax = queryRangeMaxRec(2 * node + 1, start, mid, qStart, qEnd);
        double rightMax = queryRangeMaxRec(2 * node + 2, mid + 1, end, qStart, qEnd);

        return Math.max(leftMax, rightMax);
    }

    public void updateMetric(int index, double newValue) {
        System.out.println("Updating Media Platform metric at index offset " + index + " to: " + newValue);
        updateRec(0, 0, n - 1, index, newValue);
    }

    private void updateRec(int node, int start, int end, int index, double val) {
        if (start == end) {
            tree[node] = val;
            return;
        }
        int mid = start + (end - start) / 2;
        if (index <= mid) {
            updateRec(2 * node + 1, start, mid, index, val);
        } else {
            updateRec(2 * node + 2, mid + 1, end, index, val);
        }
        tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
    }

    public void displayTreeArray() {
        System.out.println("Segment Tree Array Structure representation: " + Arrays.toString(tree));
    }

    public static void main(String[] args) {
        System.out.println("SEGMENT TREE INITIALIZATION (MediaVault Analytics Engine)");
        
        double[] analyticalMetrics = {4.2, 4.8, 3.9, 4.5, 4.7, 3.1, 5.0, 4.1};
        
        System.out.println("Initial content metric data values:");
        System.out.println(Arrays.toString(analyticalMetrics) + "\n");

        MediaVaultSegmentTree analyticsEngine = new MediaVaultSegmentTree(analyticalMetrics);
        analyticsEngine.displayTreeArray();

        System.out.println("\n--- Query Execution 1 ---");
        double maxRating1 = analyticsEngine.queryRangeMax(2, 5);
        System.out.println("Result -> Maximum performance value found within window [2, 5]: " + maxRating1);

        System.out.println("\n--- Live Data Update Trigger ---");
        analyticsEngine.updateMetric(5, 4.9);
        analyticsEngine.displayTreeArray();

        System.out.println("\n--- Query Execution 2 (Post-Update Verification) ---");
        double maxRating2 = analyticsEngine.queryRangeMax(2, 5);
        System.out.println("Result -> Updated maximum performance value found within window [2, 5]: " + maxRating2);

        System.out.println("\nProcess finished with exit code 0");
    }
}