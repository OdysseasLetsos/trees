package splay;

public class Node {
    private Node leftNode,rightNode,parent;
    private int data;

    
    
    public Node(int data) {
        // TODO Auto-generated constructor stub
        this.data=data;
        setLeftNode(null);
        setRightNode(null);
        setParent(null);
        
    }
    
    public Node(int data,Node leftNode,Node rightNode,Node parent) {
        this.data=data;
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
}
