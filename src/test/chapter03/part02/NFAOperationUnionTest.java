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

public class NFAOperationUnionTest {
    
    @Test
    public void test001() {
        String expressionString1 = "a";
        char[] expression1 = expressionString1.toCharArray();
        NFAOperationBase nfaOperationBase1 = new NFAOperationBase(expression1, 0, 0);
        String expressionString2 = "b";
        char[] expression2 = expressionString2.toCharArray();
        NFAOperationBase nfaOperationBase2 = new NFAOperationBase(expression2, 0, 0);

        NFAOperationUnion nfaOperationUnion = new NFAOperationUnion();
        NFAGraph nfaGraph = nfaOperationUnion.getNFAGraph(nfaOperationBase1.getNFAGraph(null, null), nfaOperationBase2.getNFAGraph(null, null));
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(5, (int) nfaGraph.getEndState());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (4 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (3 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
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
        NFAOperationUnion nfaOperationUnion = new NFAOperationUnion();

        NFAGraph nfaGraph = nfaOperationUnion.getNFAGraph(nfaOperationBase1.getNFAGraph(null, null), nfaOperationBase2.getNFAGraph(null, null));
        nfaGraph = nfaOperationUnion.getNFAGraph(nfaGraph, nfaOperationBase3.getNFAGraph(null, null));
        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(9, (int) nfaGraph.getEndState());
 
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(7));
                } else if(1 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (2 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (4 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (5 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (6 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(9));
                } else if (7 == i && 'c' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(8));
                } else if (8 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(9));
                } else {
                    Assert.assertNull(list);
                }
            }
        }
    }

}