package main.chapter03.part02;

public class NFAOperationScopeUnion implements NFAOperation {
    
    private char[] content;

    public NFAOperationScopeUnion(char[] expression, int startIndex, int endIndex) {
        this.content = new char[endIndex - startIndex + 1];
        for (int i = 0; i < this.content.length; i++) {
            this.content[i] = expression[i + startIndex];
        } 
    } 

    @Override
    public NFAGraph getNFAGraph() {
        return null;
    }
    
    @Override
    public boolean hasInnerOperation() {
        return false;
    }
}