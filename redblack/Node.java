package redblack;


public class Node {
    private Node leftNode, rightNode, parent;
    private int data;
    private boolean isRed;

    public Node(int data) {
        this.data = data;
        isRed = true;
    }

    // Getters and setters
    public Node getLeftNode() { return leftNode; }
    public void setLeftNode(Node leftNode) { this.leftNode = leftNode; }
    public Node getRightNode() { return rightNode; }
    public void setRightNode(Node rightNode) { this.rightNode = rightNode; }
    public int getData() { return data; }
    public void setData(int data) { this.data = data; }
    public Node getParent() { return parent; }
    public void setParent(Node parent) { this.parent = parent; }
    public boolean isRed() { return isRed; }
    public void setRed(boolean red) { isRed = red; }
}
