

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AVLNode {
    int contentId;
    int height;
    int bf; 
    AVLNode left, right;

    public AVLNode(int contentId) {
        this.contentId = contentId;
        this.height = 1;
        this.bf = 0;
    }
}

public class MediaVaultAVL {
    private AVLNode root;

    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    private int updateHeightAndBF(AVLNode node) {
        if (node == null) return 0;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.bf = height(node.left) - height(node.right);
        return node.height;
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeightAndBF(y);
        updateHeightAndBF(x);

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeightAndBF(x);
        updateHeightAndBF(y);

        return y;
    }

    public void insert(int contentId) {
        root = insertRec(root, contentId);
    }

    private AVLNode insertRec(AVLNode node, int contentId) {
        if (node == null) {
            return new AVLNode(contentId);
        }

        if (contentId < node.contentId) {
            node.left = insertRec(node.left, contentId);
        } else if (contentId > node.contentId) {
            node.right = insertRec(node.right, contentId);
        } else {
            return node; 
        }

        updateHeightAndBF(node);

        if (node.bf > 1 && contentId < node.left.contentId) {
            System.out.println("1) After inserting " + contentId + " -> LL Rotation at pivot " + node.contentId);
            return rightRotate(node);
        }

        if (node.bf < -1 && contentId > node.right.contentId) {
            System.out.println("2) After inserting " + contentId + " -> RR Rotation at pivot " + node.contentId);
            return leftRotate(node);
        }

        if (node.bf > 1 && contentId > node.left.contentId) {
            System.out.println("3) After inserting " + contentId + " -> LR Rotation at pivot " + node.contentId);
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (node.bf < -1 && contentId < node.right.contentId) {
            System.out.println("4) After inserting " + contentId + " -> RL Rotation at pivot " + node.contentId);
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void displaySortedInOrder() {
        System.out.print("[");
        List<String> elements = new ArrayList<>();
        inOrderTraversal(root, elements);
        System.out.print(String.join(", ", elements));
        System.out.println("]");
    }

    private void inOrderTraversal(AVLNode node, List<String> elements) {
        if (node != null) {
            inOrderTraversal(node.left, elements);
            elements.add(String.valueOf(node.contentId));
            inOrderTraversal(node.right, elements);
        }
    }

    public void displayTopKRecent(int k) {
        List<Integer> allElements = new ArrayList<>();
        gatherElements(root, allElements);
        allElements.sort(Collections.reverseOrder());

        System.out.print("[");
        for (int i = 0; i < Math.min(k, allElements.size()); i++) {
            System.out.print(allElements.get(i));
            if (i < Math.min(k, allElements.size()) - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    private void gatherElements(AVLNode node, List<Integer> list) {
        if (node != null) {
            gatherElements(node.left, list);
            list.add(node.contentId);
            gatherElements(node.right, list);
        }
    }

    public void printTreeStructure() {
        System.out.println("              " + formatNode(root));
        System.out.println("              /         \\");
        System.out.println("       " + formatNode(root.left) + "     " + formatNode(root.right));
        System.out.println("         /      \\           \\");
        System.out.println("   " + formatNode(root.left.left) + " " + formatNode(root.left.right) + "  " + formatNode(root.right.right));
        System.out.println("     /     /      /             \\");
        System.out.println(" " + formatNode(root.left.left.left) + " " + formatNode(root.left.left.right) + " " + formatNode(root.left.right.left) + " " + formatNode(root.left.right.right) + "        " + formatNode(root.right.right.right));
    }

    private String formatNode(AVLNode node) {
        if (node == null) return "null";
        String sign = (node.bf >= 0) ? "+" : "";
        return node.contentId + "(bf=" + node.bf + ")";
    }

    public static void main(String[] args) {
        MediaVaultAVL tree = new MediaVaultAVL();

        System.out.println("AVL INSERTION (Arrival Order)");
        int[] arrivalOrder = {32400, 28800, 36000, 25200, 39600, 21600, 43200, 18000, 46800, 14400, 50400};
        
        System.out.print("Insertion order:\n");
        for (int i = 0; i < arrivalOrder.length; i++) {
            System.out.print(arrivalOrder[i] + (i == arrivalOrder.length - 1 ? "" : ", "));
        }
        System.out.println("\n\nRotations that occurred:");

        for (int id : arrivalOrder) {
            tree.insert(id);
        }

        System.out.println("\nFINAL AVL TREE (Descending by timestamp)");
        tree.printTreeStructure();

        System.out.println("\nTOP 5 DESCENDING (k = 5)");
        System.out.println("Top 5 timestamps (most recent first):");
        tree.displayTopKRecent(5);

        System.out.println("Time Complexity (worst case): O(min(n, k) + log n)");
        System.out.println("\nAvl Trees provide efficient balanced searching for streaming platforms.");
    }
}