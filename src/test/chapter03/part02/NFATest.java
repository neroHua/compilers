package test.chapter03.part02;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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
public class NFATest {
    
//    1.测试单个操作
    @Test
    public void test0011() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0012() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aa".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0013() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0014() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0015() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bb".toCharArray(), "a".toCharArray()));
    }

    @Test
    public void test0021() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0022() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0023() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("c".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0024() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0025() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cd".toCharArray(), "[ab]".toCharArray()));
    }

    @Test
    public void test0031() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0032() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aa".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0033() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0034() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "(a)".toCharArray()));
    }

    @Test
    public void test0035() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bb".toCharArray(), "(a)".toCharArray()));
    }

//    2.测试两个操作
//    2.1测试base和其他操作
    @Test
    public void test0041() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0042() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0043() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abc".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0044() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cde".toCharArray(), "ab".toCharArray()));
    }

    @Test
    public void test0051() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0052() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0053() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("c".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0054() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0055() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cd".toCharArray(), "a|b".toCharArray()));
    }

    @Test
    public void test0061() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a*".toCharArray()));
    }

    @Test
    public void test0062() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aa".toCharArray(), "a*".toCharArray()));
    }

    @Test
    public void test0063() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaaaaaa".toCharArray(), "a*".toCharArray()));
    }

    @Test
    public void test0064() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("".toCharArray(), "a*".toCharArray()));
    }

    @Test
    public void test0065() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "a*".toCharArray()));
    }

    @Test
    public void test0071() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0072() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0073() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaa".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0074() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaa".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0075() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaaaaaaaaaaaaaa".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0076() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0077() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbbbbbbb".toCharArray(), "a{1,3}".toCharArray()));
    }

    @Test
    public void test0081() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0082() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0083() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ac".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0084() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abc".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0085() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aabc".toCharArray(), "a[bc]".toCharArray()));
    }

    @Test
    public void test0091() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0092() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0093() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0094() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abc".toCharArray(), "a(b)".toCharArray()));
    }

    @Test
    public void test0095() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abcde".toCharArray(), "a(b)".toCharArray()));
    }

//    2.测试两个操作
//    2.2测试Concatenation和其他操作(一部分在2.1中已经测试过了，另一部分没有可能出现)
//    2.3测试Union和其他操作
    @Test
    public void test0101() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0102() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0103() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0104() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("c".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0105() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0106() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abc".toCharArray(), "a|[bc]".toCharArray()));
    }

    @Test
    public void test0111() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0112() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0113() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0114() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0115() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("caab".toCharArray(), "a|(b)".toCharArray()));
    }

    @Test
    public void test0121() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0122() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0123() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0124() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaab".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0125() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaaaaaa".toCharArray(), "a*b".toCharArray()));
    }

    @Test
    public void test0131() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0132() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0133() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaaaaaaa".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0134() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0135() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0136() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaab".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0137() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bb".toCharArray(), "a*|b".toCharArray()));
    }

    @Test
    public void test0141() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("".toCharArray(), "a**".toCharArray()));
    }

    @Test
    public void test0142() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a**".toCharArray()));
    }

    @Test
    public void test0143() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaaaaaaaa".toCharArray(), "a**".toCharArray()));
    }

//    @Test
//    public void test0144() {
//        NFA nfa = new NFA();
//        Assert.assertFalse(nfa.match("b".toCharArray(), "a**".toCharArray()));
//    }

//    @Test
//    public void test0145() {
//        NFA nfa = new NFA();
//        Assert.assertFalse(nfa.match("abbb".toCharArray(), "a**".toCharArray()));
//    }

    @Test
    public void test0151() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0152() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0153() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0154() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaab".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0155() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbb".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0156() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaaaaaaaaabbbbbb".toCharArray(), "a*[b]".toCharArray()));
    }

    @Test
    public void test0161() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0162() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0163() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbbbbbbb".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0164() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0165() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaa".toCharArray(), "a*{1,2}".toCharArray()));
    }

    @Test
    public void test0171() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0172() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0173() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbb".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0174() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0175() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaaaaab".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0176() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cb".toCharArray(), "a*(b)".toCharArray()));
    }

    @Test
    public void test0181() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0182() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0183() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbb".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0184() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abbb".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0185() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "[a]b".toCharArray()));
    }

    @Test
    public void test0191() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0192() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0193() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0194() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0195() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aa".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0196() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bb".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0197() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aabababb".toCharArray(), "[a]|b".toCharArray()));
    }

    @Test
    public void test0201() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0202() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0203() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaa".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0204() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aabbbbbbaaaaa".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0205() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbbbbbbbbbb".toCharArray(), "[a]*".toCharArray()));
    }

    @Test
    public void test0211() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0212() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0213() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0214() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0215() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaa".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0216() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbbbb".toCharArray(), "[a][b]".toCharArray()));
    }

    @Test
    public void test0221() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test0222() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test0223() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaa".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test0224() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaa".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test0225() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bb".toCharArray(), "[a]{1,3}".toCharArray()));
    }

    @Test
    public void test023() {
        String expressionString = "[a](b)";
    }

    @Test
    public void test0231() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0232() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0233() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0234() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0235() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaa".toCharArray(), "[a](b)".toCharArray()));
    }

    @Test
    public void test0236() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbbbb".toCharArray(), "[a](b)".toCharArray()));
    }


    @Test
    public void test024() {
        String expressionString = "a{1,2}a";
    }

    @Test
    public void test0241() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0242() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0243() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaa".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0244() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaa".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0245() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bb".toCharArray(), "[a]{1,2}a".toCharArray()));
    }

    @Test
    public void test0251() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0252() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0253() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aa".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0254() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0255() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0256() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaa".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0257() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bb".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0258() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aab".toCharArray(), "[a]{1,2}|b".toCharArray()));
    }

    @Test
    public void test0261() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0262() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0263() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaaa".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0264() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ba".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0265() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cccccccc".toCharArray(), "[a]{1,2}*".toCharArray()));
    }

    @Test
    public void test0271() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0272() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0273() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aa".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0274() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaa".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0275() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaaaaa".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0276() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbb".toCharArray(), "[a]{1,2}[a]".toCharArray()));
    }

    @Test
    public void test0281() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0282() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0283() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0284() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0285() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0286() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaa".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0287() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbb".toCharArray(), "[a]{1,2}{1,2}".toCharArray()));
    }

    @Test
    public void test0291() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0292() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0293() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aa".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0294() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaa".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0295() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaaaaa".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0296() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbb".toCharArray(), "[a]{1,2}(a)".toCharArray()));
    }

    @Test
    public void test0301() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0302() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0303() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abc".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0304() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cde".toCharArray(), "(a)b".toCharArray()));
    }

    @Test
    public void test0311() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0312() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("b".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0313() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("c".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0314() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("ab".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0315() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cd".toCharArray(), "(a)|b".toCharArray()));
    }

    @Test
    public void test0321() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0322() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aa".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0323() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaaaaaaaaaaaaa".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0324() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0325() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "(a)*".toCharArray()));
    }

    @Test
    public void test0331() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "(a)[b]".toCharArray()));
    }

    @Test
    public void test0332() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "(a)[b]".toCharArray()));
    }

    @Test
    public void test0333() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abc".toCharArray(), "(a)[b]".toCharArray()));
    }

    @Test
    public void test0334() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cde".toCharArray(), "(a)[b]".toCharArray()));
    }

    @Test
    public void test0341() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0342() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("a".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0343() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("aaa".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0344() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaa".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0345() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("aaaaaaaaaaaaaaaaa".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0346() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("b".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0347() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("bbbbbbbbbbb".toCharArray(), "(a){1,3}".toCharArray()));
    }

    @Test
    public void test0351() {
        NFA nfa = new NFA();
        Assert.assertTrue(nfa.match("ab".toCharArray(), "(a)(b)".toCharArray()));
    }

    @Test
    public void test0352() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("a".toCharArray(), "(a)(b)".toCharArray()));
    }

    @Test
    public void test0353() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("abc".toCharArray(), "(a)(b)".toCharArray()));
    }

    @Test
    public void test0354() {
        NFA nfa = new NFA();
        Assert.assertFalse(nfa.match("cde".toCharArray(), "(a)(b)".toCharArray()));
    }

}