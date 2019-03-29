package main.chapter03.part02;

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

}
