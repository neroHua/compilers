package main.chapter03.part02;

/**
 * 
 * 左括号运算： 主要用于创建子树，配合完成从左往右扫描，而不用递归扫描
 *
 */
public class NFAOperationParentheseLeft implements NFAOperation {
    
    private final int priority = 2;  
    
    @Override 
    public NFAGraph getNFAGraph(NFAGraph firstNFAGraph, NFAGraph secondNFAGraph) {
        return null;
    }
    
    @Override
    public boolean hasInnerOperation() {
        return false;
    }
    
    @Override
    public int getPriority() {
        return this.priority;
    }

}