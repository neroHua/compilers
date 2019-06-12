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

public class NFAOperationConcatenationTest {
    
    @Test
    public void test001() {
        String expressionString1 = "a";
        char[] expression1 = expressionString1.toCharArray();
        NFAOperationBase nfaOperationBase1 = new NFAOperationBase(expression1, 0, 0);
        String expressionString2 = "b";
        char[] expression2 = expressionString2.toCharArray();
        NFAOperationBase nfaOperationBase2 = new NFAOperationBase(expression2, 0, 0);

        NFAOperationConcatenation nfaOperationConcatenation = new NFAOperationConcatenation();
        NFAGraph nfaGraph = nfaOperationConcatenation.getNFAGraph(nfaOperationBase1.getNFAGraph(null, null), nfaOperationBase2.getNFAGraph(null, null));
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(2, (int) nfaGraph.getEndState());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(1));
                } else if (1 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else {
                    Assert.assertNull(list);
                }
            }
        }
    }

    @Test
    public void test002() {
        String expressionString1 = "a";
        char[] expression1 = expressionString1.toCharArray();
        NFAOperationBase nfaOperationBase1 = new NFAOperationBase(expression1, 0, 0);
        String expressionString2 = "b";
        char[] expression2 = expressionString2.toCharArray();
        NFAOperationBase nfaOperationBase2 = new NFAOperationBase(expression2, 0, 0);
        String expressionString3 = "c";
        char[] expression3 = expressionString3.toCharArray();
        NFAOperationBase nfaOperationBase3 = new NFAOperationBase(expression3, 0, 0);
        NFAOperationConcatenation nfaOperationConcatenation = new NFAOperationConcatenation();

        NFAGraph nfaGraph = nfaOperationConcatenation.getNFAGraph(nfaOperationBase1.getNFAGraph(null, null), nfaOperationBase2.getNFAGraph(null, null));
        nfaGraph = nfaOperationConcatenation.getNFAGraph(nfaGraph, nfaOperationBase3.getNFAGraph(null, null));
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(3, (int) nfaGraph.getEndState());
 
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(1));
                } else if (1 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 'c' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(3));
                } else {
                    Assert.assertNull(list);
                }
            }
        }
    }

}