package zip;

import java.util.Random;

public class ZipTree implements ZipInterface {

    private Node root;
    private Random random;

    public ZipTree() {
        this.root = null;
        this.random = new Random();
    }

    @Override
    public void insert(int key) {
        if (search(key)) {
            return;
        }

        Node newNode = new Node(key, generateRank());
        root = insert(newNode, root);
    }

    private Node insert(Node x, Node root) {
        if (root == null) {
            x.setLeftNode(null);
            x.setRightNode(null);
            x.setRank(generateRank());
            return x;
        }
        if (x.getData() < root.getData()) {
            if (insert(x, root.getLeftNode()) == x) {
                if (x.getRank() < root.getRank()) {
                    root.setLeftNode(x);
                } else {
                    root.setLeftNode(x.getRightNode());
                    x.setRightNode(root);
                    return x;
                }
            }
        } else {
            if (insert(x, root.getRightNode()) == x) {
                if (x.getRank() <= root.getRank()) {
                    root.setRightNode(x);
                } else {
                    root.setRightNode(x.getLeftNode());
                    x.setLeftNode(root);
                    return x;
                }
            }
        }
        return root;
    }

    private int generateRank() {
        int rank = 0;
        while (random.nextBoolean()) {
            rank++;
        }
        return rank;
    }

    @Override
    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(Node node, int key) {
        if (node == null) {
            return false;
        }
        if (key == node.getData()) {
            return true;
        }
        if (key < node.getData()) {
            return search(node.getLeftNode(), key);
        } else {
            return search(node.getRightNode(), key);
        }
    }

    @Override
    public void delete(int key) {
        if (!search(key)) {
            return;
        }
        root = delete(key, root);
    }

    private Node delete(int key, Node root) {
        if (root == null) {
            return null;
        }
        if (key == root.getData()) {
            return zip(root.getLeftNode(), root.getRightNode());
        }
        if (key < root.getData()) {
            if (root.getLeftNode() != null && key == root.getLeftNode().getData()) {
                root.setLeftNode(zip(root.getLeftNode().getLeftNode(), root.getLeftNode().getRightNode()));
            } else {
                root.setLeftNode(delete(key, root.getLeftNode()));
            }
        } else {
            if (root.getRightNode() != null && key == root.getRightNode().getData()) {
                root.setRightNode(zip(root.getRightNode().getLeftNode(), root.getRightNode().getRightNode()));
            } else {
                root.setRightNode(delete(key, root.getRightNode()));
            }
        }
        return root;
    }

    private Node zip(Node x, Node y) {
        if (x == null) {
            return y;
        }
        if (y == null) {
            return x;
        }
        if (x.getRank() < y.getRank()) {
            y.setLeftNode(zip(x, y.getLeftNode()));
            return y;
        } else {
            x.setRightNode(zip(x.getRightNode(), y));
            return x;
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void printInOrder() {
        System.out.println("In-order traversal of the Zip Tree:");
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.getLeftNode());
            System.out.print("(Key: " + node.getData() + ", Rank: " + node.getRank() + ") ");
            printInOrder(node.getRightNode());
        }
    }
}
