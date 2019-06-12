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

public class NFAOperationClosureTest {
    
    @Test
    public void test001() {
        String expressionString = "a";
        char[] expression = expressionString.toCharArray();
        NFAOperationBase nfaOperationBase = new NFAOperationBase(expression, 0, 0);
        NFAOperationClosure nfaOperationClosure = new NFAOperationClosure();
        NFAGraph nfaGraph = nfaOperationClosure.getNFAGraph(null, nfaOperationBase.getNFAGraph(null, null));
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(3, (int) nfaGraph.getEndState());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else {
                    Assert.assertNull(list);
                }
            }
        }
    }

    @Test
    public void test002() {
        String expressionString = "a";
        char[] expression = expressionString.toCharArray();
        NFAOperationBase nfaOperationBase = new NFAOperationBase(expression, 0, 0);
        NFAOperationClosure nfaOperationClosure = new NFAOperationClosure();
        NFAGraph nfaGraph = nfaOperationClosure.getNFAGraph(null, nfaOperationBase.getNFAGraph(null, null));
        nfaGraph = nfaOperationClosure.getNFAGraph(null, nfaGraph);
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(5, (int) nfaGraph.getEndState());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(2 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(3));
                }  else if (1 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (3 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(5));
                } else if (4 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(5));
                } else {
                    Assert.assertNull(list);
                }
            }
        }
    }

}