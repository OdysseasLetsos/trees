package splay;

public interface SplayInterface {
    public void insert(int key);
    public Node find(int key);
    public void delete(int key);
    public void printInOrder();
}
