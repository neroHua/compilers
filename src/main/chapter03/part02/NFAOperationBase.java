package main.chapter03.part02;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 根本运算
 *
 */
@SuppressWarnings("unchecked")
public class NFAOperationBase implements NFAOperation {
    
    private char[] content;
    
    private final int priority = 0;

    private final int STATUE_COUNT = 2; 
    private final int START_STATUE = 0;
    private final int END_STATUE = 1; 

    public NFAOperationBase(char[] expression, int startIndex, int endIndex) {
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

        ArrayList<Integer> contentTransformList1 = new ArrayList<Integer>();
        contentTransformList1.add(START_STATUE);
        for (int i = 0; i < STATUE_COUNT; i++) {
            for ( int j = 0; j < NFAGraph.ALL_CHAR_LENGTH; j++) {
                graph[i][j] = (List<Integer>) contentTransformList1.clone();
            }
        }

        List<Integer> contentTransformList2 = new ArrayList<Integer>();
        contentTransformList2.add(END_STATUE);
        graph[START_STATUE][content[0]] = contentTransformList2;

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