package main.chapter03.part02;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 三大基本运算： 连接运算 
 *
 */
public class NFAOperationConcatenation implements NFAOperation {

    private final int priority = 4; 

    private final int STATUE_DESCREA_COUNT = 1;
    private final int CONVERT_FIRST_EACH_STATE_INCREA_NUMBER = 0;
    private final int START_STATE = 0;
 
    public NFAGraph getNFAGraph(NFAGraph firstNFAGraph, NFAGraph secondNFAGraph) {
        List<Integer>[][] firstGraph  = firstNFAGraph.getGraph();
        List<Integer>[][] secondGraph = secondNFAGraph.getGraph();

        List<Integer>[][] graph = new ArrayList[firstGraph.length + secondGraph.length - STATUE_DESCREA_COUNT][NFAGraph.ALL_CHAR_LENGTH];
       
        final int END_STATE = firstGraph.length + secondGraph.length - 2;
        final int CONVERT_SECOND_EACH_STATE_INCREA_NUMBER = firstGraph.length - 1;
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(START_STATE);
        // 中间的状态: 第一部分
        for (int i = 0; i < firstGraph.length - 1; i++) {
            for (int j = 0; j < NFAGraph.ALL_CHAR_LENGTH; j++) {
                graph[i][j] = convert(firstGraph[i][j], CONVERT_FIRST_EACH_STATE_INCREA_NUMBER);
            }
        }
        // 中间的状态: 第二部分
        for (int i = firstGraph.length - 1; i < firstGraph.length + secondGraph.length - 1; i++) {
            for (int j = 0; j < NFAGraph.ALL_CHAR_LENGTH; j++) {
                graph[i][j] = convert(secondGraph[i - CONVERT_SECOND_EACH_STATE_INCREA_NUMBER][j], CONVERT_SECOND_EACH_STATE_INCREA_NUMBER);
            }
        }

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