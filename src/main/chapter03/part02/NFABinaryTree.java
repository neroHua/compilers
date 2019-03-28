package main.chapter03.part02;

public class NFABinaryTree {
    
    NFABinaryTree fatherTree;
    Node fatherNode;
    boolean asRightChildOfFatherNode;

    Node root;

    public void addLeftChild(Node father, Node leftChild) {
        father.leftChild = leftChild;
        leftChild.father = father;
    }

    public void addRightChild(Node father, Node rightChild) {
        father.rightChild = rightChild;
        rightChild.father = father;
    }

    public void addFatherNodeCurrentNodeAsLeftChild(Node currentNode, Node fatherNode) {
        if (root == currentNode) {
            root = fatherNode;
        }

        currentNode.father = fatherNode;
        fatherNode.leftChild = currentNode;
    }

    public void addFatherNodeCurrentNodeAsRightChild(Node currentNode, Node fatherNode) {
        if (root == currentNode) {
            root = fatherNode;
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
    
    public void mergeNFABinaryTreeAsCurrentNodeRightChild(Node currentNode, NFABinaryTree toBeMergedNFABinaryTree) {
        addRightChild(currentNode, toBeMergedNFABinaryTree.root);
        toBeMergedNFABinaryTree = null;
    }

    public void mergeNFABinaryTreeAsCurrentNodeLeftChild(Node currentNode, NFABinaryTree toBeMergedNFABinaryTree) {
        addLeftChild(currentNode, toBeMergedNFABinaryTree.root);
        toBeMergedNFABinaryTree = null;
    }

    public static class Node {

        Node father;
        Node leftChild;
        Node rightChild;

        NFAOperation nfaOperation;

        NFAGraph getNFAGraph() {
            return nfaOperation.getNFAGraph();
        }

    }

}
