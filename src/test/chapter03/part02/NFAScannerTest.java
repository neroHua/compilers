package test.chapter03.part02;

import org.junit.Assert;
import org.junit.Test;

import main.chapter03.part02.NFAOperation;
import main.chapter03.part02.NFAOperationBase;
import main.chapter03.part02.NFAOperationClosure;
import main.chapter03.part02.NFAOperationConcatenation;
import main.chapter03.part02.NFAOperationCountUnion;
import main.chapter03.part02.NFAOperationParentheseLeft;
import main.chapter03.part02.NFAOperationParentheseRight;
import main.chapter03.part02.NFAOperationScopeUnion;
import main.chapter03.part02.NFAOperationUnion;
import main.chapter03.part02.NFAScanner;

@SuppressWarnings("unused")
public class NFAScannerTest {
    
//    1.测试0位置
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
        Assert.assertEquals("123", String.copyValueOf(((NFAOperationScopeUnion)nextOperation).getContent()));
        Assert.assertEquals(4, scanner.getScannedIndex());
    }
    
    @Test
    public void test006() {
        String expressionString = "[123]]";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationScopeUnion.class, nextOperation.getClass());
        Assert.assertEquals("123", String.copyValueOf(((NFAOperationScopeUnion)nextOperation).getContent()));
        Assert.assertEquals(4, scanner.getScannedIndex());
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
    
//    2.测试1位置
    @Test
    public void test012() {
        String expressionString = "a|";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationUnion.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());
    } 

    @Test
    public void test013() {
        String expressionString = "a*";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationClosure.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());
    } 

    @Test
    public void test014() {
        String expressionString = "a|(";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        Assert.assertEquals(NFAOperationParentheseLeft.class, nextOperation.getClass());
        Assert.assertEquals(2, scanner.getScannedIndex());
    } 

    @Test
    public void test015() {
        String expressionString = "a(";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationParentheseLeft.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    } 
 
    @Test
    public void test016() {
        String expressionString = "()";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationParentheseRight.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());
    } 
 
    @Test
    public void test017() {
        String expressionString = "a|[bcd]";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationScopeUnion.class, nextOperation.getClass());
        Assert.assertEquals(6, scanner.getScannedIndex());
        Assert.assertEquals("bcd", String.copyValueOf(((NFAOperationScopeUnion) nextOperation).getContent()));
    } 
    
    @Test
    public void test018() {
        String expressionString = "a|[bcd";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        try {
            nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("cant't find a ] int the " + String.copyValueOf(expression), e.getMessage());
        }
    }  

    @Test
    public void test019() {
        String expressionString = "a[bcd]";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationScopeUnion.class, nextOperation.getClass());
        Assert.assertEquals(5, scanner.getScannedIndex());
        Assert.assertEquals("bcd", String.copyValueOf(((NFAOperationScopeUnion) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    } 
 
    @Test
    public void test020() {
        String expressionString = "a|{1,3}";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationUnion.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationCountUnion.class, nextOperation.getClass());
        Assert.assertEquals(6, scanner.getScannedIndex());
        Assert.assertEquals("1,3", String.copyValueOf(((NFAOperationCountUnion) nextOperation).getContent()));
    } 

    @Test
    public void test021() {
        String expressionString = "a|[bcd";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        try {
            nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("cant't find a ] int the " + String.copyValueOf(expression), e.getMessage());
        }
    }   

    @Test
    public void test022() {
        String expressionString = "a{1,3}";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationCountUnion.class, nextOperation.getClass());
        Assert.assertEquals(5, scanner.getScannedIndex());
        Assert.assertEquals("1,3", String.copyValueOf(((NFAOperationCountUnion) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    } 

    @Test
    public void test023() {
        String expressionString = "a{1,3";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        try {
            nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("cant't find a } int the " + String.copyValueOf(expression), e.getMessage());
        }
    }   

    @Test
    public void test024() {
        String expressionString = "a}";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        try {
            nextOperation = scanner.getNextOperation();
        } catch (Exception e) {
            Assert.assertEquals("} should after{ in one expression:" + String.copyValueOf(expression), e.getMessage());
        };
    }

    @Test
    public void test025() {
        String expressionString = "ab";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());
        Assert.assertEquals("a", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());
        Assert.assertEquals("b", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    } 
  
//    3.综合测试
    @Test
    public void test026() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());
        Assert.assertEquals("e", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test027() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(0, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test028() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());
        Assert.assertEquals("f", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test029() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(1, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test030() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationParentheseLeft.class, nextOperation.getClass());
        Assert.assertEquals(2, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test031() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(3, scanner.getScannedIndex());
        Assert.assertEquals("a", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test032() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(3, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test033() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(4, scanner.getScannedIndex());
        Assert.assertEquals("b", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test034() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(4, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test035() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationParentheseLeft.class, nextOperation.getClass());
        Assert.assertEquals(5, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    } 

    @Test
    public void test036() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(6, scanner.getScannedIndex());
        Assert.assertEquals("a", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test037() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationUnion.class, nextOperation.getClass());
        Assert.assertEquals(7, scanner.getScannedIndex());
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test038() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(8, scanner.getScannedIndex());
        Assert.assertEquals("b", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test039() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationParentheseRight.class, nextOperation.getClass());
        Assert.assertEquals(9, scanner.getScannedIndex());
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test040() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationClosure.class, nextOperation.getClass());
        Assert.assertEquals(10, scanner.getScannedIndex());
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test041() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationUnion.class, nextOperation.getClass());
        Assert.assertEquals(11, scanner.getScannedIndex());
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test042() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(12, scanner.getScannedIndex());
        Assert.assertEquals("c", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test043() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(12, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test044() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(13, scanner.getScannedIndex());
        Assert.assertEquals("d", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test045() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationCountUnion.class, nextOperation.getClass());
        Assert.assertEquals(18, scanner.getScannedIndex());
        Assert.assertEquals("1,3", String.copyValueOf(((NFAOperationCountUnion) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test046() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationParentheseRight.class, nextOperation.getClass());
        Assert.assertEquals(19, scanner.getScannedIndex());
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test047() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(19, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test048() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(20, scanner.getScannedIndex());
        Assert.assertEquals("a", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test049() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationUnion.class, nextOperation.getClass());
        Assert.assertEquals(21, scanner.getScannedIndex());
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test050() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(22, scanner.getScannedIndex());
        Assert.assertEquals("h", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test051() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(22, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test052() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationScopeUnion.class, nextOperation.getClass());
        Assert.assertEquals(26, scanner.getScannedIndex());
        Assert.assertEquals("hi", String.copyValueOf(((NFAOperationScopeUnion) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test053() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationConcatenation.class, nextOperation.getClass());
        Assert.assertEquals(26, scanner.getScannedIndex());
        Assert.assertEquals(true, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

    @Test
    public void test054() {
        String expressionString = "ef(ab(a|b)*|cd{1,3})a|h[hi]d";
        char[] expression = expressionString.toCharArray();
        NFAScanner scanner = new NFAScanner(expression, 0, expression.length - 1);

        NFAOperation nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();

        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        nextOperation = scanner.getNextOperation();
        Assert.assertEquals(NFAOperationBase.class, nextOperation.getClass());
        Assert.assertEquals(27, scanner.getScannedIndex());
        Assert.assertEquals("d", String.copyValueOf(((NFAOperationBase) nextOperation).getContent()));
        Assert.assertEquals(false, scanner.getHasNextScanedIndexBeenAddConcateNation());
    }

}