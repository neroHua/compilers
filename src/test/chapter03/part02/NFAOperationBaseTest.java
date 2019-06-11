package test.chapter03.part02;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import main.chapter03.part02.NFABinaryTree;
import main.chapter03.part02.NFABinaryTree.Node;
import main.chapter03.part02.NFAGraph;
import main.chapter03.part02.NFAOperation;
import main.chapter03.part02.NFAOperationBase;
import main.chapter03.part02.NFAOperationClosure;
import main.chapter03.part02.NFAOperationConcatenation;
import main.chapter03.part02.NFAOperationCountUnion;
import main.chapter03.part02.NFAOperationParenthese;
import main.chapter03.part02.NFAOperationParentheseLeft;
import main.chapter03.part02.NFAOperationParentheseRight;
import main.chapter03.part02.NFAOperationScopeUnion;
import main.chapter03.part02.NFAOperationUnion;
import main.chapter03.part02.NFAParser;
import main.chapter03.part02.NFAScanner;

@SuppressWarnings("unused")
public class NFAOperationBaseTest {
    
    @Test
    public void test001() {
        String expressionString = "a";
        char[] expression = expressionString.toCharArray();
        NFAOperationBase nfaOperationBase = new NFAOperationBase(expression, 0, 0);
        NFAGraph nfaGraph = nfaOperationBase.getNFAGraph(null, null);
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(1, (int) nfaGraph.getEndState());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                Assert.assertEquals(1, list.size());
                if(0 == i && 'a' == j) {
                    Assert.assertTrue(list.contains(1));
                } else {
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test002() {
        String expressionString = "b";
        char[] expression = expressionString.toCharArray();
        NFAOperationBase nfaOperationBase = new NFAOperationBase(expression, 0, 0);
        NFAGraph nfaGraph = nfaOperationBase.getNFAGraph(null, null);
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(1, (int) nfaGraph.getEndState());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                Assert.assertEquals(1, list.size());
                if(0 == i && 'b' == j) {
                    Assert.assertTrue(list.contains(1));
                } else {
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

}