package main.chapter03.part02;

/**
 * 
 * 符合运算：数量上的合并运算 {} 
 *
 */
public class NFAOperationCountUnion implements NFAOperation {
    
    private char[] content;

    private final int priority = 3;  

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