package main.chapter03.part02;

import main.chapter03.part02.NFABinaryTree.Node;

/**
 * 
 * 把Scanner得到的Operation组织成一个BinaryTree
 * 一般的：
 *      currentOperation.priority <= previousOperation.priority     有：currentOperation.leftChild = previous
 *      currentOperation.priority > previousOperation.priority      有：previous.rightChild = currentOperation
 * 特殊的：
 *      currentOperation为NFAOperationParentheseLeft时，需要在previousOperation.rightChild上创建子树
 *      currentOperation为NFAOperationParentheseRight时，需要在previousOperation.rightChild上合并子树
 */
public class NFAParser {
    
    private NFAScanner nfaScanner;
    private NFABinaryTree tree;

    private int toBeClosedSubTreeCount;
    
    public NFAParser(NFAScanner nfaScanner) {
        this.nfaScanner = nfaScanner;
        this.tree = null;
        this.toBeClosedSubTreeCount = 0;
    }
    
    public NFABinaryTree parse() {
        NFAOperation previousNFAOperation = null;
        NFAOperation currentNFAOperation = null;
        NFABinaryTree.Node previousNFANode = null;
        NFABinaryTree.Node currentNFANode = null;

        while (nfaScanner.hasNextOperation()) {
            previousNFAOperation = currentNFAOperation;
            currentNFAOperation = nfaScanner.getNextOperation();
            previousNFANode = currentNFANode;
            currentNFANode = buildNFATreeNode(currentNFAOperation);

            Node parentheseNode = buildTree(previousNFAOperation, previousNFANode, currentNFAOperation, currentNFANode);
            currentNFANode = parentheseNode == null ? currentNFANode : parentheseNode;
        }
        
        return this.tree;
    }
    
    private Node buildTree(NFAOperation previousNFAOperation, NFABinaryTree.Node previousNFANode, NFAOperation currentNFAOperation, NFABinaryTree.Node currentNFANode) {
       if (null == previousNFAOperation && null == currentNFAOperation) {
           return null;
       }

       Node parentheseNode = null;
       if (null == previousNFAOperation && null != currentNFAOperation) {

           if (currentNFAOperation instanceof NFAOperationParentheseLeft) {
               buildRootTree(currentNFANode);
               buildSubTree(currentNFANode);
               this.toBeClosedSubTreeCount++;
           } else {
               buildRootTree(currentNFANode);
           }

       }
       
       if (null != previousNFAOperation && null != currentNFAOperation) {
           
          if (currentNFAOperation instanceof NFAOperationParentheseLeft) {
              addHigherPriorityNode(previousNFANode, currentNFANode);
              buildSubTree(currentNFANode);
              this.toBeClosedSubTreeCount++;
          } else if (currentNFAOperation instanceof NFAOperationParentheseRight) {
              parentheseNode = mergeSubTree();
              this.toBeClosedSubTreeCount--;
          } else if (currentNFAOperation.getPriority() < previousNFAOperation.getPriority()) {
              addHigherPriorityNode(previousNFANode, currentNFANode);
          } else if (currentNFAOperation.getPriority() >= previousNFAOperation.getPriority()) {
              addNotHigherPriorityNode(previousNFANode, currentNFANode);
          }

       }
        
       return parentheseNode;
    }

    private void buildRootTree(Node currentNFANode) {
        this.tree = new NFABinaryTree();
        this.tree.fatherTree = null;
        this.tree.fatherNode = null;
        this.tree.rootNode = currentNFANode;
    }
    
    private void buildSubTree(Node fatherNode) {
        NFABinaryTree subTree = new NFABinaryTree();
        subTree.fatherTree = this.tree;
        subTree.fatherNode = fatherNode;
        this.tree = subTree;
    }

    private void addHigherPriorityNode(Node previousNFANode, Node currentNFANode) {
        if (null == this.tree.rootNode) {
            this.tree.rootNode = currentNFANode;
            return;
        }

        if (currentNFANode.nfaOperation instanceof NFAOperationClosure || currentNFANode.nfaOperation instanceof NFAOperationCountUnion) {
           addClosureORCountUnion(previousNFANode, currentNFANode);
           return;
        }

        while (previousNFANode.rightChild != null) {
            previousNFANode = previousNFANode.rightChild;
        }
        previousNFANode.rightChild = currentNFANode;
        currentNFANode.father = previousNFANode;
    }

    private void addNotHigherPriorityNode(Node previousNFANode, Node currentNFANode) {
        if (null == this.tree.rootNode) {
            this.tree.rootNode = currentNFANode;
            return;
        }

        if (currentNFANode.nfaOperation instanceof NFAOperationClosure || currentNFANode.nfaOperation instanceof NFAOperationCountUnion) {
           addClosureORCountUnion(previousNFANode, currentNFANode);
           return ;
        }

        while (null != previousNFANode.father) {
            previousNFANode = previousNFANode.father;
        }
        previousNFANode.father = currentNFANode;
        currentNFANode.leftChild = previousNFANode;
        if (this.tree.rootNode == previousNFANode) {
            this.tree.rootNode = currentNFANode;
        }
    }
    
    private void addClosureORCountUnion(Node previousNFANode, Node currentNFANode) {
        if (null == previousNFANode.father) {
            this.tree.rootNode = currentNFANode;
            previousNFANode.father = currentNFANode;
            currentNFANode.rightChild = previousNFANode;
            return;
        }
        
        Node oldFatherNode = previousNFANode.father;
        if (previousNFANode == oldFatherNode.rightChild) {
            oldFatherNode.rightChild = currentNFANode;
        } else if (previousNFANode == oldFatherNode.leftChild) {
            oldFatherNode.leftChild = currentNFANode;
        }

        previousNFANode.father = currentNFANode;
        currentNFANode.rightChild = previousNFANode;
        currentNFANode.father = oldFatherNode;
    }

    private Node mergeSubTree() {
        if (null == this.tree.fatherTree || this.toBeClosedSubTreeCount <= 0) {
            throw new RuntimeException("there is no more father tree to be merged with");
        }
        NFAOperation nfaOperationParenthese = new NFAOperationParenthese();
        Node parentheseNode = buildNFATreeNode(nfaOperationParenthese);
        Node parentheseLeftNode = this.tree.fatherNode;

        if (null == parentheseLeftNode.father) {
            this.tree.fatherTree.rootNode = parentheseNode;
       } else {
            parentheseNode.father = parentheseLeftNode.father;
            if (parentheseLeftNode == parentheseLeftNode.father.leftChild) {
                parentheseLeftNode.father.leftChild = parentheseNode;
            } else if (parentheseLeftNode == parentheseLeftNode.father.rightChild) {
                parentheseLeftNode.father.rightChild = parentheseNode;
            }
        }

        parentheseNode.leftChild = parentheseLeftNode.leftChild;
        parentheseNode.rightChild = parentheseLeftNode.rightChild;
        if (null != parentheseLeftNode.leftChild) {
            parentheseLeftNode.leftChild.father = parentheseNode;
        }
        if (null != parentheseLeftNode.rightChild) {
            parentheseLeftNode.rightChild.father = parentheseNode;
        }
        
        this.tree.fatherNode = parentheseNode;
        this.tree.fatherNode.rightChild = this.tree.rootNode;
        this.tree = this.tree.fatherTree;
        
        return parentheseNode;
    }

    private Node buildNFATreeNode(NFAOperation nfaOperation) {
        Node node = new NFABinaryTree.Node();
        node.nfaOperation = nfaOperation;
        return node;
    }
    
    public int getToBeClosedSubTreeCount() {
        return toBeClosedSubTreeCount;
    }
}
