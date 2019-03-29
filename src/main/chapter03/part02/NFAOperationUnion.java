package main.chapter03.part02;

/**
 * 
 * 三大基本运算： 合并运算 | 
 *
 */
public class NFAOperationUnion implements NFAOperation {
    
    private final int priority = 5;  
    
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