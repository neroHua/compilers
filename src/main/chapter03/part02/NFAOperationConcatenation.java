package main.chapter03.part02;

/**
 * 
 * 三大基本运算： 连接运算 
 *
 */
public class NFAOperationConcatenation implements NFAOperation {

    private final int priority = 4; 

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