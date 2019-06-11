package main.chapter03.part02;

/**
 * 
 * 主要用于把一个大的的表达式,转换成一棵树，对树进行后序遍历即可得到表达式对应的NFA图
 *
 */
public class NFABinaryTree {
    
    NFABinaryTree fatherTree;
    Node fatherNode;

    Node rootNode;
    
    public void NFABinaryTree() {
        this.fatherTree = null;
        this.fatherNode = null;
        this.rootNode = null;
    }
    
    public void NFABinaryTree(NFABinaryTree fatherTree, Node fatherNode, Node rootNode) {
        this.fatherTree = fatherTree;
        this.fatherNode = fatherNode;
        this.rootNode = rootNode;
    }
    
    public NFAGraph getNFAGraph(Node node) {
        
        NFAGraph leftChildNFAGraph = null;
        if (null != node.leftChild) {
            leftChildNFAGraph = getNFAGraph(node.leftChild);
        }

        NFAGraph rightChildNFAGraph = null;
        if (null != node.rightChild) {
            rightChildNFAGraph = getNFAGraph(node.rightChild);
        }
        
       return node.getNFAGraph(leftChildNFAGraph, rightChildNFAGraph);
    }

    public void addLeftChild(Node father, Node leftChild) {
        father.leftChild = leftChild;
        leftChild.father = father;
    }

    public void addRightChild(Node father, Node rightChild) {
        father.rightChild = rightChild;
        rightChild.father = father;
    }

    public void addFatherNodeCurrentNodeAsLeftChild(Node currentNode, Node fatherNode) {
        if (rootNode == currentNode) {
            rootNode = fatherNode;
        }

        currentNode.father = fatherNode;
        fatherNode.leftChild = currentNode;
    }

    public void addFatherNodeCurrentNodeAsRightChild(Node currentNode, Node fatherNode) {
        if (rootNode == currentNode) {
            rootNode = fatherNode;
        }

        currentNode.father = fatherNode;
        fatherNode.rightChild = currentNode;
    }

    public void addHigherOperationNode(Node currentNode, Node higherOperationNode) {
        while(currentNode.father != null) {
            currentNode = currentNode.father;
        }

        addFatherNodeCurrentNodeAsLeftChild(currentNode, higherOperationNode);
    }
    
    public void addNotHigherOperationNode(Node currentNode, Node notHigherOperationNode) {
        addFatherNodeCurrentNodeAsLeftChild(currentNode, notHigherOperationNode);
    }
    
    public void mergeNFABinaryTreeAsCurrentNodeRightChild(Node currentNode, NFABinaryTree subNFABinaryTree) {
        addRightChild(currentNode, subNFABinaryTree.rootNode);
        subNFABinaryTree = null;
    }

    public void mergeNFABinaryTreeAsCurrentNodeLeftChild(Node currentNode, NFABinaryTree subNFABinaryTree) {
        addLeftChild(currentNode, subNFABinaryTree.rootNode);
        subNFABinaryTree = null;
    }
    
    public Node getRootNode() {
        return this.rootNode;
    }

    public NFABinaryTree getFatherTree() {
        return this.fatherTree;
    }

    public Node getFatherNode() {
        return this.fatherNode;
    }

    public static class Node {

        Node father;
        Node leftChild;
        Node rightChild;

        NFAOperation nfaOperation;

        public NFAGraph getNFAGraph(NFAGraph leftChildNFAGraph, NFAGraph rightChildNFAGraph) {
            return nfaOperation.getNFAGraph(leftChildNFAGraph, rightChildNFAGraph);
        }

        public NFAOperation getNFAOperation() {
            return this.nfaOperation;
        }

        public Node getFather() {
            return father;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public NFAOperation getNfaOperation() {
            return nfaOperation;
        }

    }

}
