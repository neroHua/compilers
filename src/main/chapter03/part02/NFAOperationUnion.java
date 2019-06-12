package main.chapter03.part02;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 三大基本运算： 合并运算 | 
 *
 */
public class NFAOperationUnion implements NFAOperation {
    
    private final int priority = 5;  

    private final int STATUE_INCREA_COUNT = 2;
    private final int CONVERT_FIRST_EACH_STATE_INCREA_NUMBER = 1;
    private final int START_STATE = 0;
   
    @Override
    public NFAGraph getNFAGraph(NFAGraph firstNFAGraph, NFAGraph secondNFAGraph) {
        if (null == firstNFAGraph) {
            throw new IllegalArgumentException("firstNFAGrap should not be null");
        }
        if (null == secondNFAGraph) {
            throw new IllegalArgumentException("secondNFAGrap should not be null");
        }

        List<Integer>[][] firstGraph  = firstNFAGraph.getGraph();
        Integer firstStartState = firstNFAGraph.getStartState();
        Integer firstEndState = firstNFAGraph.getEndState();
        List<Integer>[][] secondGraph = secondNFAGraph.getGraph();
        Integer secondStartState = secondNFAGraph.getStartState();
        Integer secondEndState = secondNFAGraph.getEndState();

        List<Integer>[][] graph = new ArrayList[firstGraph.length + secondGraph.length + STATUE_INCREA_COUNT][NFAGraph.ALL_CHAR_LENGTH];
       
        final int END_STATE = firstGraph.length + secondGraph.length + 1;
        final int CONVERT_SECOND_EACH_STATE_INCREA_NUMBER = firstGraph.length + 1;
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(START_STATE);
        // 新加的初始状态
        // 新加的结束状态
        for (int i = 0; i < NFAGraph.ALL_CHAR_LENGTH; i++) {
            graph[START_STATE][i] = (ArrayList<Integer>) arrayList2.clone();
            graph[END_STATE][i] = (ArrayList<Integer>) arrayList2.clone();
        }
        
        // 中间的状态: 第一部分
        for (int i = 1; i < firstGraph.length + 1; i++) {
            for (int j = 0; j < NFAGraph.ALL_CHAR_LENGTH; j++) {
                graph[i][j] = convert(firstGraph[i - CONVERT_FIRST_EACH_STATE_INCREA_NUMBER][j], CONVERT_FIRST_EACH_STATE_INCREA_NUMBER);
            }
        }
        // 中间的状态: 第二部分
        for (int i = firstGraph.length + 1; i < firstGraph.length + secondGraph.length + 1; i++) {
            for (int j = 0; j < NFAGraph.ALL_CHAR_LENGTH; j++) {
                graph[i][j] = convert(secondGraph[i - CONVERT_SECOND_EACH_STATE_INCREA_NUMBER][j], CONVERT_SECOND_EACH_STATE_INCREA_NUMBER);
            }
        }

        // 特殊处理的状态: 起点状态
        ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        arrayList1.add(firstStartState + CONVERT_FIRST_EACH_STATE_INCREA_NUMBER);
        arrayList1.add(secondStartState + CONVERT_SECOND_EACH_STATE_INCREA_NUMBER);
        graph[START_STATE][NFAGraph.ALL_CHAR_LENGTH - 1] = arrayList1;

        // 特殊处理的状态: 起点状态
        ArrayList<Integer> arrayList3 = new ArrayList<Integer>();
        arrayList3.add(END_STATE);
        graph[firstEndState + CONVERT_FIRST_EACH_STATE_INCREA_NUMBER][NFAGraph.ALL_CHAR_LENGTH - 1] = (ArrayList<Integer>) arrayList3.clone();
        graph[secondEndState+ CONVERT_SECOND_EACH_STATE_INCREA_NUMBER][NFAGraph.ALL_CHAR_LENGTH - 1] = (ArrayList<Integer>) arrayList3.clone();
 
        return new NFAGraph(START_STATE, END_STATE, graph);
    }

    private List<Integer> convert(List<Integer> toBeConvertStateList, Integer increaNumber) {
        List<Integer> convertedList = new ArrayList<Integer>();
        for (int i = 0; i < toBeConvertStateList.size(); i++) {
            if (START_STATE == toBeConvertStateList.get(i)) {
                convertedList.add(i, toBeConvertStateList.get(i));
            } else {
                convertedList.add(i, toBeConvertStateList.get(i) + increaNumber);
            }
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