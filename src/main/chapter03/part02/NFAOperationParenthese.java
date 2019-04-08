package main.chapter03.part02;

/**
 * 
 * 小括号运算：  主要用于表示子树合并完毕
 *
 */
public class NFAOperationParenthese implements NFAOperation {
    
    private final int priority = 2;
    
    @Override 
    public NFAGraph getNFAGraph() {
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