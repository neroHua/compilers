package main.chapter03.part02;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 三大基本操作：闭包运算 *
 *
 */
public class NFAOperationClosure implements NFAOperation {
    
    private final int priority = 3; 
    
    private final int STATUE_INCREA_COUNT = 2;
    private final int CONVERT_EACH_STATE_INCREA_NUMBER = 1;
    private final int START_STATE = 0;

    @Override
    public NFAGraph getNFAGraph(NFAGraph firstNFAGraph, NFAGraph secondNFAGraph) {
        List<Integer>[][] secondGraph = secondNFAGraph.getGraph();
        Integer secondStartState = secondNFAGraph.getStartState();

        List<Integer>[][] graph = new ArrayList[secondGraph.length + STATUE_INCREA_COUNT][NFAGraph.ALL_CHAR_LENGTH];
       
        final int END_STATE = secondGraph.length + 1;
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(START_STATE);
        
        // 中间的状态
        for (int i = 0; i < secondGraph.length; i++) {
            for (int j = 0; j < NFAGraph.ALL_CHAR_LENGTH; j++) {
                graph[i + 1][j] = convert(secondGraph[i][j]);
            }
        }

        // 特殊处理的状态
        ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        arrayList1.add(secondStartState + CONVERT_EACH_STATE_INCREA_NUMBER);
        arrayList1.add(END_STATE);
        graph[START_STATE][NFAGraph.ALL_CHAR_LENGTH - 1] = arrayList1;
        graph[END_STATE - CONVERT_EACH_STATE_INCREA_NUMBER][NFAGraph.ALL_CHAR_LENGTH - 1] = (ArrayList<Integer>) arrayList1.clone();
 
        return new NFAGraph(START_STATE, END_STATE, graph);
    } 

    private List<Integer> convert(List<Integer> toBeConvertStateList) {
        if (null == toBeConvertStateList) {
            return null;
        }

        List<Integer> convertedList = new ArrayList<Integer>();
        for (int i = 0; i < toBeConvertStateList.size(); i++) {
            convertedList.add(i, toBeConvertStateList.get(i) + CONVERT_EACH_STATE_INCREA_NUMBER);
        }

        return convertedList;
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