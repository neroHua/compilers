package main.chapter03.part02;

/**
 * 
 * 符合运算：数量上的合并运算 {} 
 *
 */
public class NFAOperationCountUnion implements NFAOperation {
    
    private char[] content;

    private final int priority = 3;  

    public NFAOperationCountUnion() {
        
    }

    public NFAOperationCountUnion(char[] expression, int startIndex, int endIndex) {
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
        int[] minAndMaxCount = parserCount(content);
        int minCount = minAndMaxCount[0];
        int maxCount = minAndMaxCount[1];
        if (minCount > maxCount) {
            throw new IllegalArgumentException("最小个数不能大于最大个数");
        }
        if (minCount < 1) {
            throw new IllegalArgumentException("最小个数不能低于1");
        }
        if (maxCount < 1) {
            throw new IllegalArgumentException("最大个数不能低于1");
        }

        NFAGraph[] toBeUnionedGraph = new NFAGraph[maxCount - minCount + 1];
        NFAOperationConcatenation nfaOperationConcatenation = new NFAOperationConcatenation();
        NFAOperationUnion nfaOperationUnion = new NFAOperationUnion();
        NFAGraph firstToBeUnionedGraph = secondNFAGraph;
        for (int i = 1; i < minCount; i++) {
            firstToBeUnionedGraph = nfaOperationConcatenation.getNFAGraph(firstToBeUnionedGraph,secondNFAGraph);
        }

        toBeUnionedGraph[0] = firstToBeUnionedGraph;
        for (int i = 1; i < toBeUnionedGraph.length; i++) {
            toBeUnionedGraph[i] = nfaOperationConcatenation.getNFAGraph(toBeUnionedGraph[i - 1], secondNFAGraph);
        }

        NFAGraph graph = toBeUnionedGraph[0];
        for (int i = 1; i < toBeUnionedGraph.length; i++) {
             graph = nfaOperationUnion.getNFAGraph(graph, toBeUnionedGraph[i]);
        }

        return graph;
    }
    
    private int[] parserCount(char[] content) {
        int[] count = new int[2]; // 位置0为小值，位置1为大值

        char split = ',';
        int splitIndex = 0;
        for (int i = 0; i < content.length; i++) {
            if (content[i] == split) {
                splitIndex = i;
                break;
            }
        }

        count[0] = parserInt(content, 0, splitIndex - 1);
        count[1] = parserInt(content, splitIndex + 1, content.length - 1);

        return count;
    }

    private int parserInt(char[] content, int startIndex, int endIndex) {
        int result = 0;
        int redix = 10;
        int degree = 1;

        for (int i = endIndex; i >= startIndex; i--) {
            int currentNumber = 0;
            if ('0' == content[i]) {
               currentNumber = 0;
            } else if ('1' == content[i]) {
                currentNumber = 1;
            } else if ('2' == content[i]) {
                currentNumber = 2;
            } else if ('3' == content[i]) {
                currentNumber = 3;
            } else if ('4' == content[i]) {
                currentNumber = 4;
            } else if ('5' == content[i]) {
                currentNumber = 5;
            } else if ('6' == content[i]) {
                currentNumber = 6;
            } else if ('7' == content[i]) {
                currentNumber = 7;
            } else if ('8' == content[i]) {
                currentNumber = 8;
            } else if ('9' == content[i]) {
                currentNumber = 9;
            } else {
                throw new IllegalArgumentException("被解析成字符数组中包含着不是数字的字符：" + content[i]);
            }

            result += currentNumber * degree;
            degree *= redix;
        }

        return result;
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