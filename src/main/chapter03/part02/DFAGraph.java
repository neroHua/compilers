package main.chapter03.part02;

import java.util.List;

/**
 * 
 * DFA图
 *
 */
public class DFAGraph {
    
    Integer startState;
    List<Integer> endState;
    Integer[][] graph; //Q,A,Q'
    public static final int ALL_CHAR_LENGTH = 128; // 使用ASCII码表

    public DFAGraph(Integer startState, List<Integer> endState, Integer[][] graph) {
        this.startState = startState;
        this.endState = endState;
        this.graph = graph;
    }
    
    public Integer getStartState() {
        return startState;
    }

    public List<Integer> getEndState() {
        return endState;
    }

    public Integer[][] getGraph() {
        return graph;
    }

    public Integer getNextState(Integer currentState, Character nextChar) {
        return graph[currentState][nextChar];
    }

}