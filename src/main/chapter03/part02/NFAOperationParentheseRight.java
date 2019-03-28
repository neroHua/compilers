package main.chapter03.part02;

public class NFAOperationParentheseRight implements NFAOperation {
    
    @Override 
    public NFAGraph getNFAGraph() {
        return null;
    }
    
    @Override
    public boolean hasInnerOperation() {
        return false;
    }

}