package test.chapter03.part02;

import org.junit.Assert;
import org.junit.Test;

import main.chapter03.part02.NFABinaryTree;
import main.chapter03.part02.NFABinaryTree.Node;
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
public class NFAParserTest {
    
//    1.测试单个操作
    @Test
    public void test001() {
        String expressionString = "a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));
        
        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());
        Assert.assertEquals(NFAOperationBase.class, tree.getRootNode().getNFAOperation().getClass());
        Assert.assertEquals(null, tree.getRootNode().getFather());
        Assert.assertEquals(null, tree.getRootNode().getLeftChild());
        Assert.assertEquals(null, tree.getRootNode().getRightChild());
    }

    @Test
    public void test002() {
        String expressionString = "|";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        try {
            NFABinaryTree tree = parser.parse();
        } catch (Exception e) {
            Assert.assertEquals("| should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        }
    }

    @Test
    public void test003() {
        String expressionString = "*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        try {
            NFABinaryTree tree = parser.parse();
        } catch (Exception e) {
            Assert.assertEquals("* should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        }
    }

    @Test
    public void test004() {
        String expressionString = "{";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        try {
            NFABinaryTree tree = parser.parse();
        } catch (Exception e) {
            Assert.assertEquals("{ should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        }
    }

    @Test
    public void test005() {
        String expressionString = "[1]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Node rootNode = tree.getRootNode();

        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(null, rootNode.getFather());
        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild());
    }

    @Test
    public void test006() {
        String expressionString = "(";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Node fatherNode = tree.getFatherNode();

        Assert.assertEquals(1, parser.getToBeClosedSubTreeCount());
        Assert.assertEquals(NFAOperationParentheseLeft.class, fatherNode.getNFAOperation().getClass());
        Assert.assertEquals(fatherNode, tree.getFatherTree().getRootNode());
        Assert.assertEquals(null, fatherNode.getFather());
        Assert.assertEquals(null, fatherNode.getLeftChild());
        Assert.assertEquals(null, fatherNode.getRightChild());
        Assert.assertEquals(null, tree.getRootNode());
    }

    @Test
    public void test007() {
        String expressionString = "(1";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(1, parser.getToBeClosedSubTreeCount());

        Node fatherNode = tree.getFatherNode();
        Assert.assertEquals(NFAOperationParentheseLeft.class, fatherNode.getNFAOperation().getClass());
        Assert.assertEquals(fatherNode, tree.getFatherTree().getRootNode());
        Assert.assertEquals(null, fatherNode.getFather());
        Assert.assertEquals(null, fatherNode.getLeftChild());
        Assert.assertEquals(null, fatherNode.getRightChild());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationBase.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(null, rootNode.getFather());
        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild());
    }

    @Test
    public void test008() {
        String expressionString = "(1)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(null, rootNode.getFather());
        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
    }

//    2.测试两个操作
//    2.1测试base和其他操作
    @Test
    public void test009() {
        String expressionString = "ab";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
    }

    @Test
    public void test010() {
        String expressionString = "a|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
    }

    @Test
    public void test011() {
        String expressionString = "a*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild());
    }

    @Test
    public void test012() {
        String expressionString = "a{1,2}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild());
    }

    @Test
    public void test013() {
        String expressionString = "a[12]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
    }

    @Test
    public void test014() {
        String expressionString = "a(1";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(1, parser.getToBeClosedSubTreeCount());

        NFABinaryTree fatherTree = tree.getFatherTree();
        Node fatherRootNode = fatherTree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, fatherRootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, fatherRootNode.getLeftChild().getNFAOperation().getClass());

        Assert.assertEquals(null, fatherRootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getRightChild());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationBase.class, rootNode.getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild());
    }

    @Test
    public void test015() {
        String expressionString = "a(1)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

//    2.测试两个操作
//    2.2测试Concatenation和其他操作(一部分在2.1中已经测试过了，另一部分没有可能出现)
//    2.3测试Union和其他操作
    @Test
    public void test016() {
        String expressionString = "a|*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getLeftChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild().getRightChild());
    }

    @Test
    public void test017() {
        String expressionString = "a|[1]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
    }

    @Test
    public void test018() {
        String expressionString = "a|{1,2}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getLeftChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild().getRightChild());
    }

    @Test
    public void test019() {
        String expressionString = "a|(1";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(1, parser.getToBeClosedSubTreeCount());

        NFABinaryTree fatherTree = tree.getFatherTree();
        Node fatherRootNode = fatherTree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, fatherRootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, fatherRootNode.getLeftChild().getNFAOperation().getClass());

        Assert.assertEquals(null, fatherRootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getRightChild());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationBase.class, rootNode.getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild());
    }

    @Test
    public void test020() {
        String expressionString = "a|(1)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

    @Test
    public void test021() {
        String expressionString = "a*b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
    }

    @Test
    public void test022() {
        String expressionString = "a*|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
    }

    @Test
    public void test023() {
        String expressionString = "a**";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

    @Test
    public void test024() {
        String expressionString = "a*[1]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test025() {
        String expressionString = "a*{1,2}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

    @Test
    public void test026() {
        String expressionString = "a*(1";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(1, parser.getToBeClosedSubTreeCount());

        NFABinaryTree fatherTree = tree.getFatherTree();
        Node fatherRootNode = fatherTree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, fatherRootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParentheseLeft.class, fatherRootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationClosure.class, fatherRootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, fatherRootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, fatherRootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getRightChild().getRightChild());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationBase.class, rootNode.getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild());
    }

    @Test
    public void test027() {
        String expressionString = "[1]a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
    }

    @Test
    public void test028() {
        String expressionString = "[1]|a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
    }

    @Test
    public void test029() {
        String expressionString = "[1]*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
    }

    @Test
    public void test030() {
        String expressionString = "[1][1]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
    }

    @Test
    public void test031() {
        String expressionString = "[1]{1,3}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
    }

    @Test
    public void test032() {
        String expressionString = "[1](1";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(1, parser.getToBeClosedSubTreeCount());

        NFABinaryTree fatherTree = tree.getFatherTree();
        Node fatherRootNode = fatherTree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, fatherRootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, fatherRootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParentheseLeft.class, fatherRootNode.getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, fatherRootNode.getLeftChild().getRightChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getLeftChild());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationBase.class, rootNode.getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild());
    }

    @Test
    public void test033() {
        String expressionString = "[1](1)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
    }

    @Test
    public void test034() {
        String expressionString = "a{1,3}a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test035() {
        String expressionString = "a{1,3}|b";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test036() {
        String expressionString = "a{1,3}*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

    @Test
    public void test037() {
        String expressionString = "a{1,3}[a]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test038() {
        String expressionString = "a{1,3}{1,3}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

    @Test
    public void test039() {
        String expressionString = "a{1,3}(1";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(1, parser.getToBeClosedSubTreeCount());

        NFABinaryTree fatherTree = tree.getFatherTree();
        Node fatherRootNode = fatherTree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, fatherRootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationCountUnion.class, fatherRootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParentheseLeft.class, fatherRootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, fatherRootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, fatherRootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, fatherRootNode.getLeftChild().getRightChild().getRightChild());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationBase.class, rootNode.getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild());
    }

    @Test
    public void test040() {
        String expressionString = "a{1,3}(1)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test041() {
        String expressionString = "(1)a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test042() {
        String expressionString = "(1)|a";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test043() {
        String expressionString = "(1)*";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationClosure.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

    @Test
    public void test044() {
        String expressionString = "(1)[1]";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationScopeUnion.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
    }

    @Test
    public void test045() {
        String expressionString = "(1){1,3}";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationCountUnion.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }

    @Test
    public void test046() {
        String expressionString = "(1)(1)";
        char[] expression = expressionString.toCharArray();
        NFAParser parser = new NFAParser(new NFAScanner(expression));

        NFABinaryTree tree = parser.parse();
        Assert.assertEquals(0, parser.getToBeClosedSubTreeCount());

        Node rootNode = tree.getRootNode();
        Assert.assertEquals(NFAOperationConcatenation.class, rootNode.getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getLeftChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationParenthese.class, rootNode.getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getLeftChild().getRightChild().getNFAOperation().getClass());
        Assert.assertEquals(NFAOperationBase.class, rootNode.getRightChild().getRightChild().getNFAOperation().getClass());

        Assert.assertEquals(null, rootNode.getLeftChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getLeftChild().getRightChild().getRightChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getLeftChild());
        Assert.assertEquals(null, rootNode.getRightChild().getRightChild().getRightChild());
    }



}