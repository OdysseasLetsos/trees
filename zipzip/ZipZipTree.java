package zipzip;

public class ZipZipTree implements ZipZipInterface {
    private Node root;

    public ZipZipTree() {
        root = null;
    }

    @Override
    public void insert(int key) {
        Node newNode = new Node(key);
        root = unzip(root, newNode);
    }

    private Node unzip(Node current, Node newNode) {
        if (current == null) {
            return newNode;
        }

        if (isGreater(newNode, current)) {
            if (newNode.getKey() < current.getKey()) {
                newNode.setRight(current);
                newNode.setLeft(current.getLeft());
                current.setLeft(null);
                return newNode;
            } else {
                newNode.setLeft(current);
                newNode.setRight(current.getRight());
                current.setRight(null);
                return newNode;
            }
        } else {
            if (newNode.getKey() < current.getKey()) {
                current.setLeft(unzip(current.getLeft(), newNode));
            } else {
                current.setRight(unzip(current.getRight(), newNode));
            }
            return current;
        }
    }

    @Override
    public void delete(int key) {
        root = zip(root, key);
    }

    private Node zip(Node current, int key) {
        if (current == null) return null;

        if (current.getKey() == key) {
            return zipNodes(current.getLeft(), current.getRight());
        }

        if (key < current.getKey()) {
            current.setLeft(zip(current.getLeft(), key));
        } else {
            current.setRight(zip(current.getRight(), key));
        }
        return current;
    }

    private Node zipNodes(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        if (isGreater(left, right)) {
            left.setRight(zipNodes(left.getRight(), right));
            return left;
        } else {
            right.setLeft(zipNodes(left, right.getLeft()));
            return right;
        }
    }

    private boolean isGreater(Node a, Node b) {
        if (a.getRank1() != b.getRank1()) {
            return a.getRank1() > b.getRank1();
        }
        if (a.getRank2() != b.getRank2()) {
            return a.getRank2() > b.getRank2();
        }
        return a.getKey() < b.getKey();
    }

    @Override
    public boolean search(int key) {
        return searchRecursive(root, key);
    }
    
    private boolean searchRecursive(Node current, int key) {
        if (current == null) {
            return false;
        }
        
        if (key == current.getKey()) {
            return true;
        }
        
        boolean searchLeft = searchRecursive(current.getLeft(), key);
        if (searchLeft) {
            return true;
        }
        
        return searchRecursive(current.getRight(), key);
    }

    @Override
    public void printInOrder() {
        printInOrderRecursive(root, "");
    }

    private void printInOrderRecursive(Node node, String prefix) {
        if (node == null) {
            return;
        }
        
        System.out.println(prefix + node.getKey() + " (" + node.getRank1() + "," + node.getRank2() + ")");
        
        printInOrderRecursive(node.getLeft(), prefix + "L-");
        printInOrderRecursive(node.getRight(), prefix + "R-");
    }

    public Node getRoot() {
        return root;
    }
}