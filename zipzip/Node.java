package zipzip;

import java.util.Random;

public class Node {
    private int key;
    private int rank1;
    private int rank2;
    private Node left;
    private Node right;
    private static final Random random = new Random();

    public Node(int key) {
        this.key = key;
        this.rank1 = generateRank1();
        this.rank2 = generateRank2();
    }

    private int generateRank1() {
        return geometricDistribution(0.5);
    }

    private int geometricDistribution(double p) {
        return (int) (Math.log(1 - random.nextDouble()) / Math.log(1 - p));
    }


    private int generateRank2() {
        return 1 + (int)(Math.random() * (Math.log(1000000) * Math.log(1000000) * Math.log(1000000)));
    }

    // Getters and setters remain the same
    public int getKey() { return key; }
    public int getRank1() { return rank1; }
    public int getRank2() { return rank2; }
    public Node getLeft() { return left; }
    public Node getRight() { return right; }
    public void setLeft(Node left) { this.left = left; }
    public void setRight(Node right) { this.right = right; }
}