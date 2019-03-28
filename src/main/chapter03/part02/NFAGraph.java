package main.chapter03.part02;

import java.util.List;

public class NFAGraph {
    
    int startState;
    int endState;
    List<Integer>[][] graph; //Q,A,Q'

    public NFAGraph(int startState, int endState, List<Integer>[][] graph) {
        this.startState = startState;
        this.endState = endState;
        this.graph = graph;
    }

}
