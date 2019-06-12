package main.chapter03.part02;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 复合运算：范围型 合并运算 [] 
 *
 */
public class NFAOperationScopeUnion implements NFAOperation {
    
    private char[] content;
    
    private final int priority = 1;  
    
    private final int STATUE_COUNT = 2; 
    private final int START_STATUE = 0;
    private final int END_STATUE = 1; 

    public NFAOperationScopeUnion(char[] expression, int startIndex, int endIndex) {
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
        List<Integer>[][] graph = new ArrayList[STATUE_COUNT][NFAGraph.ALL_CHAR_LENGTH]; // 状态转换图 129位代表E

        ArrayList<Integer> contentTransformList2 = new ArrayList<Integer>();
        contentTransformList2.add(END_STATUE);
        for (int i = 0; i < content.length; i++) {
            graph[START_STATUE][content[i]] = (ArrayList<Integer>) contentTransformList2.clone();
        }

        return new NFAGraph(START_STATUE, END_STATUE, graph);
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