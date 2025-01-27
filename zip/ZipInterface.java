package zip;

public interface ZipInterface {
    public void insert(int key);
    public boolean search(int key);
    public void delete(int key);
    public Node getRoot();
    public void printInOrder();
}
