package main.chapter03.part02;

import java.util.ArrayList;
import java.util.List;

public class NFAOperationBase implements NFAOperation {
    
    private char[] content;
    
    public NFAOperationBase(char[] expression, int startIndex, int endIndex) {
        this.content = new char[endIndex - startIndex + 1];
        for (int i = 0; i < this.content.length; i++) {
            this.content[i] = expression[i + startIndex];
        } 
    } 

    @Override
    public NFAGraph getNFAGraph() {
        @SuppressWarnings("unchecked")
        List<Integer>[][] graph = new ArrayList[2][129]; // 状态转换图 129位代表E

        List<Integer> contentTransformList = new ArrayList<Integer>();
        contentTransformList.add(1);

        graph[0][content[0]] = contentTransformList;

        return new NFAGraph(0, 1, graph);
    }

    @Override
    public boolean hasInnerOperation() {
        return false;
    }

}