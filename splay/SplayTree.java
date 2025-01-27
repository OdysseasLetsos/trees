package splay;

public class SplayTree implements SplayInterface {
    private Node root;

    public SplayTree() {
        this.root = null;
    }

    @Override
    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }

        Node n = root;
        Node parent = null;
        while (n != null) {
            parent = n;
            if (key < n.getData()) {
                n = n.getLeftNode();
            } else if (key > n.getData()) {
                n = n.getRightNode();
            } else {
                // Key already exists, splay it to root and return
                splay(n);
                return;
            }
        }

        Node newNode = new Node(key);
        newNode.setParent(parent);

        if (key < parent.getData()) {
            parent.setLeftNode(newNode);
        } else {
            parent.setRightNode(newNode);
        }

        // Splay the newly inserted node to the root
        splay(newNode);
    }

    private void splay(Node x) {
        while (x.getParent() != null) {
            Node p = x.getParent();
            Node g = p.getParent();

            if (g == null) {
                // Zig step
                if (p.getLeftNode() == x) {
                    rotateRight(p);
                } else {
                    rotateLeft(p);
                }
            } else if (g.getLeftNode() == p && p.getLeftNode() == x) {
                // Zig-zig step (left-left)
                rotateRight(g);
                rotateRight(p);
            } else if (g.getRightNode() == p && p.getRightNode() == x) {
                // Zig-zig step (right-right)
                rotateLeft(g);
                rotateLeft(p);
            } else if (g.getLeftNode() == p && p.getRightNode() == x) {
                // Zig-zag step (left-right)
                rotateLeft(p);
                rotateRight(g);
            } else {
                // Zig-zag step (right-left)
                rotateRight(p);
                rotateLeft(g);
            }
        }
        root = x;
    }

    private void rotateRight(Node y) {
        Node x = y.getLeftNode();
        y.setLeftNode(x.getRightNode());
        if (x.getRightNode() != null) {
            x.getRightNode().setParent(y);
        }
        x.setParent(y.getParent());
        if (y.getParent() == null) {
            root = x;
        } else if (y == y.getParent().getRightNode()) {
            y.getParent().setRightNode(x);
        } else {
            y.getParent().setLeftNode(x);
        }
        x.setRightNode(y);
        y.setParent(x);
    }

    private void rotateLeft(Node x) {
        Node y = x.getRightNode();
        x.setRightNode(y.getLeftNode());
        if (y.getLeftNode() != null) {
            y.getLeftNode().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getLeftNode()) {
            x.getParent().setLeftNode(y);
        } else {
            x.getParent().setRightNode(y);
        }
        y.setLeftNode(x);
        x.setParent(y);
    }
    
    @Override
    public void delete(int key) {
        if (root == null) return;

        // Splay the node to be deleted to the root
        splay(find(key));

        // If the key doesn't exist in the tree, nothing to delete
        if (root.getData() != key) return;

        Node deleteNode = root;

        if (root.getLeftNode() == null) {
            // Case 1: No left child
            root = root.getRightNode();
            if (root != null) {
                root.setParent(null);
            }
        } else if (root.getRightNode() == null) {
            // Case 2: No right child
            root = root.getLeftNode();
            root.setParent(null);
        } else {
            // Case 3: Both children exist
            Node leftSubtree = root.getLeftNode();
            Node rightSubtree = root.getRightNode();
            leftSubtree.setParent(null);
            rightSubtree.setParent(null);

            root = root.getLeftNode();
            root.setParent(null);

            // Find the minimum node in the right subtree
            Node minRight = findMax(root);

            // Splay the minimum node to the root of the right subtree
            splay(minRight);

            // Connect the left subtree as the left child of the new root
            root.setRightNode(rightSubtree);
            rightSubtree.setParent(root);
        }

        // Clean up the deleted node
        deleteNode.setLeftNode(null);
        deleteNode.setRightNode(null);
    }

    public Node find(int key) {
        Node current = root;
        while (current != null) {
            if (key < current.getData()) {
                if (current.getLeftNode() == null) break;
                current = current.getLeftNode();
            } else if (key > current.getData()) {
                if (current.getRightNode() == null) break;
                current = current.getRightNode();
            } else {
                return current; // Found the key
            }
        }
        return current; // Will return null if key not found
    }

    private Node findMax(Node node) {
        while (node.getRightNode() != null) {
            node = node.getRightNode();
        }
        return node;
    }

    @Override
    public void printInOrder() {
        System.out.println("In-order traversal of the Splay Tree:");
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.getLeftNode());
            System.out.print(node.getData() + " ");
            printInOrder(node.getRightNode());
        }
    }
}