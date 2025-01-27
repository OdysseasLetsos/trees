package zip;

public class Node {

    private Node leftNode, rightNode, parent;
    private int data;
    private int rank;

    // constructor with rank to check
    public Node(int data, int rank) {
        // TODO Auto-generated constructor stub
        this.data = data;
        setLeftNode(null);
        setRightNode(null);
        setParent(null);
        this.rank = rank;

    }

    public Node(int data, Node leftNode, Node rightNode, Node parent) {
        this.data = data;
        setLeftNode(leftNode);
        setRightNode(rightNode);
        setParent(parent);
    }

    public Node() {

    }

    public Node getLeftNode() {

        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {

        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public Node getParent() {

        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
