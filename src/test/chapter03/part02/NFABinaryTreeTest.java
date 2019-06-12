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
public class NFABinaryTreeTest {
    
//    1.测试单个操作
    @Test
    public void test001() {
        String expressionString = "a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
        String expressionString = "[ab]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(1, (int) nfaGraph.getEndState());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                Assert.assertEquals(1, list.size());
                if (0 == i && 'a' == j) {
                    Assert.assertTrue(list.contains(1));
                } else if (0 == i && 'b' == j) {
                    Assert.assertTrue(list.contains(1));
                } else {
                    Assert.assertTrue(list.contains(0));
                }
            }
        }

    }

    @Test
    public void test003() {
        String expressionString = "(a)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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

//    2.测试两个操作
//    2.1测试base和其他操作
    @Test
    public void test004() {
        String expressionString = "ab";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test005() {
        String expressionString = "a|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test006() {
        String expressionString = "a*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test007() {
        String expressionString = "a{1,3}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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

    @Test
    public void test008() {
        String expressionString = "a[bc]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                } else if (1 == i && 'c' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }

    }

    @Test
    public void test009() {
        String expressionString = "a(b)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

//    2.测试两个操作
//    2.2测试Concatenation和其他操作(一部分在2.1中已经测试过了，另一部分没有可能出现)
//    2.3测试Union和其他操作
    @Test
    public void test010() {
        String expressionString = "a|[bc]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (3 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else if (3 == i && 'c' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else if (4 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test011() {
        String expressionString = "a|(b)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test012() {
        String expressionString = "a*b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(4, (int) nfaGraph.getEndState());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test013() {
        String expressionString = "a*|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(7, (int) nfaGraph.getEndState());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(5));
                } else if (1 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (2 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (4 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else if (5 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (6 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test014() {
        String expressionString = "a**";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(5, (int) nfaGraph.getEndState());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(5));
                } else if (1 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (2 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (4 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(5));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test015() {
        String expressionString = "a*[b]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(4, (int) nfaGraph.getEndState());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test016() {
        String expressionString = "a*{1,2}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(12, (int) nfaGraph.getEndState());

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(5));
                } else if (1 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (2 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(2));
                    Assert.assertTrue(list.contains(4));
                } else if (4 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(12));
                } else if (5 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(6));
                    Assert.assertTrue(list.contains(8));
                } else if (6 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else if (7 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(6));
                    Assert.assertTrue(list.contains(8));
                } else if (8 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(9));
                    Assert.assertTrue(list.contains(11));
                } else if (9 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(10));
                } else if (10 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(9));
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

    @Test
    public void test017() {
        String expressionString = "a*(b)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(4, (int) nfaGraph.getEndState());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (3 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test018() {
        String expressionString = "[a]b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test019() {
        String expressionString = "[a]|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test020() {
        String expressionString = "[a]*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }

    }

    @Test
    public void test021() {
        String expressionString = "[a][b]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test022() {
        String expressionString = "[a]{1,3}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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

    @Test
    public void test023() {
        String expressionString = "[a](b)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test024() {
        String expressionString = "a{1,2}a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(7, (int) nfaGraph.getEndState());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (3 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else if (4 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (5 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (6 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test025() {
        String expressionString = "a{1,2}|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(10, (int) nfaGraph.getEndState());

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if (0 == i && 128 == j) {
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
                    Assert.assertTrue(list.contains(10));
                } else if (8 == i && 'b' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(9));
                } else if (9 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(10));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test026() {
        String expressionString = "a{1,2}*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(8, (int) nfaGraph.getEndState());

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if (0 == i && 128 == j) {
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
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(8));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test027() {
        String expressionString = "a{1,2}[a]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(7, (int) nfaGraph.getEndState());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (3 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else if (4 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (5 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (6 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test028() {
        String expressionString = "a{1,2}{1,2}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(21, (int) nfaGraph.getEndState());

        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if (0 == i && 128 == j) {
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
                    Assert.assertTrue(list.contains(21));
                } else if (8 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(9));
                    Assert.assertTrue(list.contains(11));
                } else if (9 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(10));
                } else if (10 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(14));
                } else if (11 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(12));
                } else if (12 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(13));
                } else if (13 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(14));
                } else if (14 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(15));
                    Assert.assertTrue(list.contains(17));
                } else if (15 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(16));
                } else if (16 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(20));
                } else if (17 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(18));
                } else if (18 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(19));
                } else if (19 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(20));
                } else if (20 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(21));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test029() {
        String expressionString = "a{1,2}(a)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

        List<Integer>[][] graph = nfaGraph.getGraph();
        Assert.assertEquals(0, (int) nfaGraph.getStartState());
        Assert.assertEquals(7, (int) nfaGraph.getEndState());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 129; j++) {
                List<Integer> list = graph[i][j];
                if(0 == i && 128 == j) {
                    Assert.assertEquals(2, list.size());
                    Assert.assertTrue(list.contains(1));
                    Assert.assertTrue(list.contains(3));
                } else if (1 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(2));
                } else if (2 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (3 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(4));
                } else if (4 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(5));
                } else if (5 == i && 128 == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(6));
                } else if (6 == i && 'a' == j) {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(7));
                } else {
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test030() {
        String expressionString = "(a)b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test031() {
        String expressionString = "(a)|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test032() {
        String expressionString = "(a)*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test033() {
        String expressionString = "(a)[b]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

    @Test
    public void test034() {
        String expressionString = "(a){1,3}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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

    @Test
    public void test035() {
        String expressionString = "(a)(b)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        NFABinaryTree tree = parser.parse();
        NFAGraph nfaGraph = tree.getNFAGraph(tree.getRootNode());

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
                    Assert.assertEquals(1, list.size());
                    Assert.assertTrue(list.contains(0));
                }
            }
        }
    }

}