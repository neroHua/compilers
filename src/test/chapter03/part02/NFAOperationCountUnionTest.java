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

public class NFAOperationCountUnionTest {
    
    @Test
    public void test001() {
        String expressionString1 = "a";
        char[] expression1 = expressionString1.toCharArray();
        NFAOperationBase nfaOperationBase1 = new NFAOperationBase(expression1, 0, 0);
        String expressionString2 = "{1,3}";
        char[] expression2 = expressionString2.toCharArray();
        NFAOperationCountUnion nfaOperationCountUnion = new NFAOperationCountUnion(expression2, 1, expression2.length - 2);
        NFAGraph nfaGraph = nfaOperationCountUnion.getNFAGraph(null, nfaOperationBase1.getNFAGraph(null, null));

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(12, (int) nfaGraph.getEndState());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(8));
                } else if (1 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (2 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else if (4 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (5 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (6 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else if (7 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(12));
                } else if (8 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(9));
                } else if (9 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(10));
                } else if (10 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(11));
                } else if (11 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(12));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

}