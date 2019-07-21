package test.chapter03.part02;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import main.chapter03.part02.DFA;
import main.chapter03.part02.NFA;
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
public class DFATest {
    
//    1.测试单个操作
    @Test
    public void test0011() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0012() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aa".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0013() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0014() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0015() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bb".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0021() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0022() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0023() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("c".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0024() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0025() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cd".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0031() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0032() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aa".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0033() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0034() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0035() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bb".toCharArray(), "(a)".toCharArray()));
    }

//    2.测试两个操作
//    2.1测试base和其他操作
    @Test
    public void test0041() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0042() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0043() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abc".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0044() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cde".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0051() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0052() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0053() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("c".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0054() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0055() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cd".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0061() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a*".toCharArray()));
    }

    @Test
    public void test0062() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aa".toCharArray(), "a*".toCharArray()));
    }

    @Test
    public void test0063() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaaaaaaaa".toCharArray(), "a*".toCharArray()));
    }

//    @Test
//    public void test0064() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("".toCharArray(), "a*".toCharArray()));
//    }

    @Test
    public void test0065() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "a*".toCharArray()));
    }

//    @Test
//    public void test0071() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "a{1,3}".toCharArray()));
//    }

    @Test
    public void test0072() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0073() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaa".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0074() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaa".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0075() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaaaaaaaaaaaaaa".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0076() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0077() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbbbbbbb".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0081() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0082() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0083() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ac".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0084() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abc".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0085() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aabc".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0091() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0092() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0093() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0094() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abc".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0095() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abcde".toCharArray(), "a(b)".toCharArray()));
    }

//    2.测试两个操作
//    2.2测试Concatenation和其他操作(一部分在2.1中已经测试过了，另一部分没有可能出现)
//    2.3测试Union和其他操作
//    @Test
//    public void test0101() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "a|[bc]".toCharArray()));
//    }

    @Test
    public void test0102() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0103() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0104() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("c".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0105() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0106() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abc".toCharArray(), "a|[bc]".toCharArray()));
    }

//    @Test
//    public void test0111() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "a|(b)".toCharArray()));
//    }

    @Test
    public void test0112() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0113() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0114() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0115() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("caab".toCharArray(), "a|(b)".toCharArray()));
    }

//    @Test
//    public void test0121() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "a*b".toCharArray()));
//    }

    @Test
    public void test0122() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0123() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0124() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaab".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0125() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaaaaaa".toCharArray(), "a*b".toCharArray()));
    }

//    @Test
//    public void test0131() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("".toCharArray(), "a*|b".toCharArray()));
//    }

    @Test
    public void test0132() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0133() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaaaaaaaaa".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0134() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0135() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0136() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaab".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0137() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bb".toCharArray(), "a*|b".toCharArray()));
    }

//    @Test
//    public void test0141() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("".toCharArray(), "a**".toCharArray()));
//    }

//    @Test
//    public void test0142() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("a".toCharArray(), "a**".toCharArray()));
//    }

//    @Test
//    public void test0143() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("aaaaaaaaaaaaaaaa".toCharArray(), "a**".toCharArray()));
//    }

//    @Test
//    public void test0144() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("b".toCharArray(), "a**".toCharArray()));
//    }

//    @Test
//    public void test0145() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("abbb".toCharArray(), "a**".toCharArray()));
//    }

//    @Test
//    public void test0151() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "a*[b]".toCharArray()));
//    }

    @Test
    public void test0152() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0153() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0154() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaab".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0155() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbb".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0156() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaaaaaaaaabbbbbb".toCharArray(), "a*[b]".toCharArray()));
    }

//    @Test
//    public void test0161() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("".toCharArray(), "a*{1,2}".toCharArray()));
//    }

    @Test
    public void test0162() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0163() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbbbbbbb".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0164() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0165() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaaaa".toCharArray(), "a*{1,2}".toCharArray()));
    }

//    @Test
//    public void test0171() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "a*(b)".toCharArray()));
//    }

    @Test
    public void test0172() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0173() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbb".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0174() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0175() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaaaaaaab".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0176() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cb".toCharArray(), "a*(b)".toCharArray()));
    }

//    @Test
//    public void test0181() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]b".toCharArray()));
//    }

    @Test
    public void test0182() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0183() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbb".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0184() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abbb".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0185() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "[a]b".toCharArray()));
    }

//    @Test
//    public void test0191() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]|b".toCharArray()));
//    }

    @Test
    public void test0192() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0193() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0194() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0195() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aa".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0196() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bb".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0197() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aabababb".toCharArray(), "[a]|b".toCharArray()));
    }

//    @Test
//    public void test0201() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("".toCharArray(), "[a]*".toCharArray()));
//    }

    @Test
    public void test0202() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0203() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaaaa".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0204() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aabbbbbbaaaaa".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0205() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbbbbbbbbbb".toCharArray(), "[a]*".toCharArray()));
    }

//    @Test
//    public void test0211() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a][b]".toCharArray()));
//    }

    @Test
    public void test0212() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0213() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0214() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0215() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaa".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0216() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbbbb".toCharArray(), "[a][b]".toCharArray()));
    }

//    @Test
//    public void test0221() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]{1,3}".toCharArray()));
//    }

    @Test
    public void test0222() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test0223() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaa".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test0224() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaa".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test0225() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bb".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test023() {
        String expressionString = "[a](b)";
    }

//    @Test
//    public void test0231() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a](b)".toCharArray()));
//    }

    @Test
    public void test0232() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0233() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0234() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0235() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaa".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0236() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbbbb".toCharArray(), "[a](b)".toCharArray()));
    }


    @Test
    public void test024() {
        String expressionString = "a{1,2}a";
    }

//    @Test
//    public void test0241() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]{1,2}a".toCharArray()));
//    }

    @Test
    public void test0242() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0243() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaa".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0244() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaa".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0245() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bb".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

//    @Test
//    public void test0251() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]{1,2}|b".toCharArray()));
//    }

    @Test
    public void test0252() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0253() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aa".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0254() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0255() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0256() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaa".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0257() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bb".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0258() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aab".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

//    @Test
//    public void test0261() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("".toCharArray(), "[a]{1,2}*".toCharArray()));
//    }

    @Test
    public void test0262() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0263() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaaaaa".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0264() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ba".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0265() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cccccccc".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

//    @Test
//    public void test0271() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]{1,2}[a]".toCharArray()));
//    }

    @Test
    public void test0272() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0273() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aa".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0274() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaa".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0275() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaaaaa".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0276() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbb".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

//    @Test
//    public void test0281() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
//    }

    @Test
    public void test0282() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0283() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0284() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0285() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0286() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0287() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbb".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

//    @Test
//    public void test0291() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "[a]{1,2}(a)".toCharArray()));
//    }

    @Test
    public void test0292() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0293() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aa".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0294() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaa".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0295() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaaaaa".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0296() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbb".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0301() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0302() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0303() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abc".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0304() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cde".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0311() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0312() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("b".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0313() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("c".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0314() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("ab".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0315() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cd".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0321() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0322() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aa".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0323() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaaaaaaaaaaaaa".toCharArray(), "(a)*".toCharArray()));
    }

//    @Test
//    public void test0324() {
//        DFA dfa = new DFA();
//        Assert.assertTrue(dfa.match("".toCharArray(), "(a)*".toCharArray()));
//    }

    @Test
    public void test0325() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0331() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "(a)[b]".toCharArray()));
    }

    @Test
    public void test0332() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "(a)[b]".toCharArray()));
    }

    @Test
    public void test0333() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abc".toCharArray(), "(a)[b]".toCharArray()));
    }

    @Test
    public void test0334() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cde".toCharArray(), "(a)[b]".toCharArray()));
    }

//    @Test
//    public void test0341() {
//        DFA dfa = new DFA();
//        Assert.assertFalse(dfa.match("".toCharArray(), "(a){1,3}".toCharArray()));
//    }

    @Test
    public void test0342() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("a".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0343() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("aaa".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0344() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaa".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0345() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("aaaaaaaaaaaaaaaaa".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0346() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("b".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0347() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("bbbbbbbbbbb".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0351() {
        DFA dfa = new DFA();
        Assert.assertTrue(dfa.match("ab".toCharArray(), "(a)(b)".toCharArray()));
    }

    @Test
    public void test0352() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("a".toCharArray(), "(a)(b)".toCharArray()));
    }

    @Test
    public void test0353() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("abc".toCharArray(), "(a)(b)".toCharArray()));
    }

    @Test
    public void test0354() {
        DFA dfa = new DFA();
        Assert.assertFalse(dfa.match("cde".toCharArray(), "(a)(b)".toCharArray()));
    }

}