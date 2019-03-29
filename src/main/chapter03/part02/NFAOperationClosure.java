package main.chapter03.part02;

/**
 * 
 * 三大基本操作：闭包运算 *
 *
 */
public class NFAOperationClosure implements NFAOperation {
    
    private final int priority = 3; 

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