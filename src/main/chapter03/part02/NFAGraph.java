package main.chapter03.part02;

import java.util.List;

/**
 * 
 * NFA图
 *
 */
public class NFAGraph {
    
    Integer startState;
    Integer endState;
    List<Integer>[][] graph; //Q,A,Q'
    public static final int ALL_CHAR_LENGTH = 129; // 使用ASCII码表，第129位代表E

    public NFAGraph(Integer startState, Integer endState, List<Integer>[][] graph) {
        this.startState = startState;
        this.endState = endState;
        this.graph = graph;
    }
    
    public Integer getStartState() {
        return startState;
    }

    public Integer getEndState() {
        return endState;
    }

    public List<Integer>[][] getGraph() {
        return graph;
    }

    public List<Integer> getNextStatus(Integer currentStatus, Character nextChar) {
        return graph[currentStatus][nextChar];
    }

}