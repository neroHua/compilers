package test.chapter03.part02;

import org.junit.Assert;
import org.junit.Test;

import main.chapter03.part02.NFAOperation;
import main.chapter03.part02.NFAOperationBase;
import main.chapter03.part02.NFAOperationParentheseLeft;
import main.chapter03.part02.NFAOperationScopeUnion;
import main.chapter03.part02.NFAScanner;

@SuppressWarnings("unused")
public class NFAScannerTest {
    
//    1.测试初始位置
//    @Test
//    public void test001() {
//        String expressionString = "ab";
//        char[] expression = expressionString.toCharArray();
//        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
//        NFAOperation nextOperation = scanner.getNextOperation();
//
//        Assert.assertEquals(new NFAOperationBase(expression, 0, 0).getClass(), nextOperation.getClass());
//    }
    
    @Test
    public void test001() {
        String expressionString = "|";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        try {
            NFAOperation nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("| should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        }
    }

    @Test
    public void test002() {
        String expressionString = "*";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        try {
            NFAOperation nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("* should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        }
    }

    @Test
    public void test003() {
        String expressionString = "(";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        NFAOperation nextOperation = scanner.getNextOperation();

        Assert.assertEquals(NFAOperationParentheseLeft.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());
    }

    @Test
    public void test004() {
        String expressionString = ")";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        try {
            NFAOperation nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals(") should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        };
    }
    
    @Test
    public void test005() {
        String expressionString = "[123]";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        NFAOperation nextOperation = scanner.getNextOperation();

        Assert.assertEquals(NFAOperationScopeUnion.class, nextOperation.getClass());
        Assert.assertEquals(String.copyValueOf(expression), String.copyValueOf(((NFAOperationScopeUnion)nextOperation).getContent()));
        Assert.assertEquals(5, scanner.getScannedIndex());
    }
    
    @Test
    public void test006() {
        String expressionString = "[123]]";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        NFAOperation nextOperation = scanner.getNextOperation();

        Assert.assertEquals(NFAOperationScopeUnion.class, nextOperation.getClass());
        Assert.assertEquals("[123]", String.copyValueOf(((NFAOperationScopeUnion)nextOperation).getContent()));
        Assert.assertEquals(5, scanner.getScannedIndex());
    }
    
    @Test
    public void test007() {
        String expressionString = "[123";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        try {
            NFAOperation nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("cant't find a ] int the " + String.copyValueOf(expression), e.getMessage());
        };
    }
    
    @Test
    public void test008() {
        String expressionString = "]";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        try {
            NFAOperation nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("] should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        };
    }

    @Test
    public void test009() {
        String expressionString = "{";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        try {
            NFAOperation nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("{ should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        };
    }

    @Test
    public void test010() {
        String expressionString = "}";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        try {
            NFAOperation nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("} should not in the first place of one expression:" + String.copyValueOf(expression), e.getMessage());
        };
    }
    
    @Test
    public void test011() {
        String expressionString = "a";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);
        NFAOperation nextOperation = scanner.getNextOperation();

        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals("a", String.copyValueOf(((NFAOperationBase)nextOperation).getContent()));
        Assert.assertEquals(0, scanner.getScannedIndex());
    }
    
}