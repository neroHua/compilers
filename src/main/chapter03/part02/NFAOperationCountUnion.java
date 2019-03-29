package main.chapter03.part02;

public class NFAOperationCountUnion implements NFAOperation {
    
    private char[] content;
    
    public NFAOperationCountUnion() {
        
    }

    public NFAOperationCountUnion(char[] expression, int startIndex, int endIndex) {
        this.content = new char[endIndex - startIndex + 1];
        for (int i = 0; i < this.content.length; i++) {
            this.content[i] = expression[i + startIndex];
        } 
    }
    
    public char[] getContent() {
        return content;
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