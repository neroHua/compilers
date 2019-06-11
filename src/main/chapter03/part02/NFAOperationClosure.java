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
    private final int START_STATUE = 0;

    @Override
    public NFAGraph getNFAGraph(NFAGraph firstNFAGraph, NFAGraph secondNFAGraph) {
        List<Integer>[][] secondGraph = secondNFAGraph.getGraph();
        Integer secondStartState = secondNFAGraph.getStartState();
        Integer secondEndState = secondNFAGraph.getEndState();

        @SuppressWarnings("unchecked")
        List<Integer>[][] graph = new ArrayList[secondGraph.length + STATUE_INCREA_COUNT][NFAGraph.ALL_CHAR_LENGTH];
       
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(0);
        // 新加的初始状态
        for (int i = 0; i < 128; i++) {
            graph[0][i] = (ArrayList<Integer>) arrayList2.clone();
        }

        // 新加的结束状态
        for (int i = 0; i < 129; i++) {
            graph[graph.length + 1][i] = (ArrayList<Integer>) arrayList2.clone();
        }
        
        // 中间的状态
        for (int i = 0; i < secondGraph.length; i++) {
            for (int j = 0; j < NFAGraph.ALL_CHAR_LENGTH; j++) {
                graph[i + 1][j] = convert(secondGraph[i][j]);
            }
        }

        // 特殊处理的状态
        ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        arrayList1.add(secondStartState + 1);
        arrayList1.add(secondEndState + secondGraph.length);
        graph[0][129] = arrayList1;
        graph[secondGraph.length + 1][129] = (ArrayList<Integer>) arrayList1.clone();
 
        return new NFAGraph(START_STATUE, secondGraph.length + 1, graph);
    } 

    private List<Integer> convert(List<Integer> toBeConvertStateList) {
        for (int i = 0; i < toBeConvertStateList.size(); i++) {
            if (0 == toBeConvertStateList.get(i)) {
                continue;
            } else {
                toBeConvertStateList.set(i, toBeConvertStateList.get(i) + 1);
            }
        }

        return toBeConvertStateList;
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