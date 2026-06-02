import java.util.Arrays;

public class MediaVaultSorter {

    public void sortTrendingMetrics(double[] arr) {
        int n = arr.length;

        // Build max heap from input array
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Iteratively extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            double temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    private void heapify(double[] arr, int n, int i) {
        int largest = i; 
        int left = 2 * i + 1; 
        int right = 2 * i + 2; 

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            double swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }

    public static void main(String[] args) {
        System.out.println("ADVANCED SORTING ENGINE INITIALIZATION (MediaVault Trending Aggregator)");
        
        double[] viewingCounts = {120.4, 450.8, 89.2, 310.5, 560.1, 150.3, 410.9, 620.5};
        
        System.out.println("Initial unsorted streaming metrics list (Hourly Views in thousands):");
        System.out.println(Arrays.toString(viewingCounts) + "\n");

        System.out.println("Executing Max-Heapification phase on trending metrics array...");
        MediaVaultSorter sorter = new MediaVaultSorter();
        
        System.out.println("Building internal structural heap properties successful.\n");
        System.out.println("Extracting sorted tracking arrays via in-place Heapification swaps...\n");
        
        sorter.sortTrendingMetrics(viewingCounts);

        System.out.println("Sorted Trending Leaderboard (Ascending View Metrics):");
        System.out.println(Arrays.toString(viewingCounts));
        
        System.out.println("\nProcess finished with exit code 0");
    }
}