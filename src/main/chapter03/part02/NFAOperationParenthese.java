package main.chapter03.part02;

/**
 * 
 * 小括号运算：  主要用于表示子树合并完毕
 *
 */
public class NFAOperationParenthese implements NFAOperation {
    
    private final int priority = 2;
    
    @Override 
    public NFAGraph getNFAGraph(NFAGraph firstNFAGraph, NFAGraph secondNFAGraph) {
        return secondNFAGraph;
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