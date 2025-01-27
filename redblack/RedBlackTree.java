package redblack;

public class RedBlackTree implements RedBlackInterface {
    protected Node root;
    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    @Override
    public void insert(int data) {
        Node node = new Node(data);
        root = insert(root, node);
        fixViolation(node);
        size++;
    }

    private Node insert(Node root, Node node) {
        if (root == null) return node;

        if (node.getData() < root.getData()) {
            root.setLeftNode(insert(root.getLeftNode(), node));
            root.getLeftNode().setParent(root);
        } else if (node.getData() > root.getData()) {
            root.setRightNode(insert(root.getRightNode(), node));
            root.getRightNode().setParent(root);
        }

        return root;
    }

    private void fixViolation(Node node) {
        Node parent;
        Node grandParent;
    
        while (node != root && node.isRed() && node.getParent() != null && node.getParent().isRed()) {
            parent = node.getParent();
            grandParent = parent.getParent();
    
            if (parent == grandParent.getLeftNode()) {
                Node uncle = grandParent.getRightNode();
    
                if (uncle != null && uncle.isRed()) {
                    grandParent.setRed(true);
                    parent.setRed(false);
                    uncle.setRed(false);
                    node = grandParent;
                } else {
                    if (node == parent.getRightNode()) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.getParent();
                    }
                    rotateRight(grandParent);
                    boolean tempColor = parent.isRed();
                    parent.setRed(grandParent.isRed());
                    grandParent.setRed(tempColor);
                    node = parent;
                }
            } else {
                Node uncle = grandParent.getLeftNode();
    
                if (uncle != null && uncle.isRed()) {
                    grandParent.setRed(true);
                    parent.setRed(false);
                    uncle.setRed(false);
                    node = grandParent;
                } else {
                    if (node == parent.getLeftNode()) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.getParent();
                    }
                    rotateLeft(grandParent);
                    boolean tempColor = parent.isRed();
                    parent.setRed(grandParent.isRed());
                    grandParent.setRed(tempColor);
                    node = parent;
                }
            }
        }
        root.setRed(false);
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.getRightNode();
        node.setRightNode(rightChild.getLeftNode());

        if (node.getRightNode() != null)
            node.getRightNode().setParent(node);

        rightChild.setParent(node.getParent());

        if (node.getParent() == null)
            root = rightChild;
        else if (node == node.getParent().getLeftNode())
            node.getParent().setLeftNode(rightChild);
        else
            node.getParent().setRightNode(rightChild);

        rightChild.setLeftNode(node);
        node.setParent(rightChild);
    }

    private void rotateRight(Node node) {
        Node leftChild = node.getLeftNode();
        node.setLeftNode(leftChild.getRightNode());

        if (node.getLeftNode() != null)
            node.getLeftNode().setParent(node);

        leftChild.setParent(node.getParent());

        if (node.getParent() == null)
            root = leftChild;
        else if (node == node.getParent().getRightNode())
            node.getParent().setRightNode(leftChild);
        else
            node.getParent().setLeftNode(leftChild);

        leftChild.setRightNode(node);
        node.setParent(leftChild);
    }
    
    @Override
    public void delete(int data) {
        Node node = root;
      
        // Find the node to be deleted
        while (node != null && node.getData() != data) {
            // Traverse the tree to the left or right depending on the key
            if (data < node.getData()) {
                node = node.getLeftNode();
            } else {
                node = node.getRightNode();
            }
        }
      
        // Node not found?
        if (node == null) {
            return;
        }
      
        // At this point, "node" is the node to be deleted
      
        // In this variable, we'll store the node at which we're going to start to fix the R-B
        // properties after deleting a node.
        Node movedUpNode;
        boolean deletedNodeColor;
      
        // Node has zero or one child
        if (node.getLeftNode() == null || node.getRightNode() == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.isRed();
        }
      
        // Node has two children
        else {
            // Find minimum node of right subtree ("inorder successor" of current node)
            Node inOrderSuccessor = findMinimum(node.getLeftNode());
        
            // Copy inorder successor's data to current node (keep its color!)
            node.setData(inOrderSuccessor.getData());
        
            // Delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.isRed();
        }
      
        if (!deletedNodeColor) {
            if (movedUpNode != null && movedUpNode.isRed()) {
                // If the moved-up node is red, we can simply recolor it black
                movedUpNode.setRed(false);
            } else {
                fixRedBlackPropertiesAfterDelete(movedUpNode);
            }
        }

           // Clean up any remaining NIL nodes
        if (movedUpNode != null && movedUpNode.getClass() == NilNode.class) {
            replaceParentsChild(movedUpNode.getParent(), movedUpNode, null);
        }     
        
    }

    private Node deleteNodeWithZeroOrOneChild(Node node) {
        // Node has ONLY a left child --> replace by its left child
        if (node.getLeftNode() != null) {
            replaceParentsChild(node.getParent(), node, node.getLeftNode());
            return node.getLeftNode(); // moved-up node
        }
      
        // Node has ONLY a right child --> replace by its right child
        else if (node.getRightNode() != null) {
            replaceParentsChild(node.getParent(), node, node.getRightNode());
            return node.getRightNode(); // moved-up node
        }
      
        // Node has no children -->
        // * node is red --> just remove it
        // * node is black --> replace it by a temporary NIL node (needed to fix the R-B rules)
        else {
            Node newChild = node.isRed() ? null : new NilNode();
            replaceParentsChild(node.getParent(), node, newChild);
            return newChild;
        }
    }
    
    private Node findMinimum(Node node) {

        while (node.getRightNode() != null) {
            
            node = node.getRightNode();
        }
        return node;
    }

    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.getLeftNode() == oldChild) {
            parent.setLeftNode(newChild);
        } else if (parent.getRightNode() == oldChild) {
            parent.setRightNode(newChild);
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }
      
        if (newChild != null) {
            newChild.setParent(parent);
        }
    }

    private void fixRedBlackPropertiesAfterDelete(Node node) {
        while (node != root && isBlack(node)) {
            Node sibling = getSibling(node);
    
            if (node == node.getParent().getLeftNode()) {
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    node.getParent().setRed(true);
                    rotateLeft(node.getParent());
                    sibling = getSibling(node);
                }
    
                if (isBlack(sibling.getLeftNode()) && isBlack(sibling.getRightNode())) {
                    sibling.setRed(true);
                    node = node.getParent();
                } else {
                    if (isBlack(sibling.getRightNode())) {
                        sibling.getLeftNode().setRed(false);
                        sibling.setRed(true);
                        rotateRight(sibling);
                        sibling = getSibling(node);
                    }
    
                    sibling.setRed(node.getParent().isRed());
                    node.getParent().setRed(false);
                    sibling.getRightNode().setRed(false);
                    rotateLeft(node.getParent());
                    node = root;
                }
            } else {
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    node.getParent().setRed(true);
                    rotateRight(node.getParent());
                    sibling = getSibling(node);
                }
    
                if (isBlack(sibling.getLeftNode()) && isBlack(sibling.getRightNode())) {
                    sibling.setRed(true);
                    node = node.getParent();
                } else {
                    if (isBlack(sibling.getRightNode())) {
                        sibling.getLeftNode().setRed(false);
                        sibling.setRed(true);
                        rotateRight(sibling);
                        sibling = getSibling(node);
                    }
                    
                    rotateLeft(sibling);
                    rotateRight(sibling.getParent().getParent());

                    Node brother = getSibling(sibling);
                    brother.setRed(sibling.isRed());

                    node = root;
                }
            }
        }
    
        if (node != null) {
            node.setRed(false);
        }
    }

    private Node getSibling(Node node) {
        Node parent = node.getParent();
        if (node == parent.getLeftNode()) {
            return parent.getRightNode();
        } else if (node == parent.getRightNode()) {
            return parent.getLeftNode();
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }
    

    private boolean isBlack(Node node) {
        return node == null || node.isRed() == false;
    }

    @Override
    public boolean search(int data) {
        return search(root, data) != null;
    }

    private Node search(Node node, int data) {
        if (node == null || data == node.getData()) return node;
        if (data < node.getData()) return search(node.getLeftNode(), data);
        return search(node.getRightNode(), data);
    }

    @Override
    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node node) {
        if (node == root && node != null) {
            printInOrder(node.getLeftNode());
            System.out.print("(" + node.getData() + ", " + (node.isRed() ? "RED" : "BLACK") + " R" + ") ");
            printInOrder(node.getRightNode());
        }
        else if (node == root && node == null) {
            System.out.println("Tree is empty");
        }
        else if (node != null) {
            printInOrder(node.getLeftNode());
            System.out.print("(" + node.getData() + ", " + (node.isRed() ? "RED" : "BLACK") + ") ");
            printInOrder(node.getRightNode());
        }
    }

    @Override
    public int getSize() {
        return size;
    }
}