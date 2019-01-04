package test.chapter03.part01;

import org.junit.Assert;
import org.junit.Test;

public class RegularExpression00Test {

    // 1.基本的一些操作
    // 表达式形式如下：位置 次数
    // 位置：只有一个位置，该位置上允许某个，允许某些个，不允许某个，不允许某些个字符
    // 次数：前面一个为位置上的字符出现或者不出现的个数
    @Test
    public void test001() {
        String partten = "^a*$"; // 匹配字符a,0次或者是多次
        String s = "";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test002() {
        String partten = "^a*$"; // 匹配字符a,0次或者是多次
        String s = "a";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test003() {
        String partten = "^a*$"; // 匹配字符a,0次或者是多次
        String s = "aaaaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test004() {
        String partten = "^a*$"; // 匹配字符a,0次或者是多次
        String s = "aa1aaa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test005() {
        String partten = "^a+$"; // 匹配字符a,1次或者是多次
        String s = "";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test006() {
        String partten = "^a+$"; // 匹配字符a,1次或者是多次
        String s = "a";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test007() {
        String partten = "^a+$"; // 匹配字符a,1次或者是多次
        String s = "aaaaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test008() {
        String partten = "^a+$"; // 匹配字符a,1次或者是多次
        String s = "aa1aaa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test009() {
        String partten = "^a?$"; // 匹配字符a,0次或者是1次
        String s = "";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test010() {
        String partten = "^a?$"; // 匹配字符a,0次或者是1次
        String s = "a";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test011() {
        String partten = "^a?$"; // 匹配字符a,0次或者是1次
        String s = "aaaaa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test012() {
        String partten = "^a?$"; // 匹配字符a,0次或者是1次
        String s = "aa1aaa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test013() {
        String partten = "^a{3}$"; // 匹配字符a,3次
        String s = "";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test014() {
        String partten = "^a{3}$"; // 匹配字符a,3次
        String s = "aa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test015() {
        String partten = "^a{3}$"; // 匹配字符a,3次
        String s = "aaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test016() {
        String partten = "^a{3}$"; // 匹配字符a,3次
        String s = "aaa1";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test017() {
        String partten = "^a{3}$"; // 匹配字符a,3次
        String s = "aaaaa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test018() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test019() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "aa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test020() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "aaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test021() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "aaaaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test022() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "aaaaaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test023() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "aaa1aa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test024() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "aaaaaaa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test025() {
        String partten = "^a{3,6}$"; // 匹配字符a,至少3次，至多6次
        String s = "aaaaaaa1";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test026() {
        String partten = "^a{3,}$"; // 匹配字符a,至少3次
        String s = "";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test027() {
        String partten = "^a{3,}$"; // 匹配字符a,至少3次
        String s = "aa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test028() {
        String partten = "^a{3,}$"; // 匹配字符a,至少3次
        String s = "aaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test029() {
        String partten = "^a{3,}$"; // 匹配字符a,至少3次
        String s = "aaaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test030() {
        String partten = "^a{3,}$"; // 匹配字符a,至少3次
        String s = "aaaa1";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test031() {
        String partten = "^a{3,}$"; // 匹配字符a,至少3次
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test032() {
        String partten = "^(abc)+$"; // 匹配字符abc,至少1次
        String s = "abc";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test033() {
        String partten = "^(abc)+$"; // 匹配字符abc,至少1次
        String s = "abcabc";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test034() {
        String partten = "^(abc)+$"; // 匹配字符abc,至少1次
        String s = "abcabcabc";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test035() {
        String partten = "^(abc)+$"; // 匹配字符abc,至少1次
        String s = "abcabcaba";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test036() {
        String partten = "^(abc)+$"; // 匹配字符abc,至少1次
        String s = "abcabcabc";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test037() {
        String partten = "^[abc]+$"; // 匹配字符a或者b或者c,至少1次
        String s = "abccbaaaccbbbcbcac";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test038() {
        String partten = "^[abc]+$"; // 匹配字符a或者b或者c,至少1次
        String s = "a";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test039() {
        String partten = "^[abc]+$"; // 匹配字符a或者b或者c,至少1次
        String s = "b";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test040() {
        String partten = "^[abc]+$"; // 匹配字符a或者b或者c,至少1次
        String s = "c";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test041() {
        String partten = "^[abc]+$"; // 匹配字符a或者b或者c,至少1次
        String s = "abccbaaaccbbbcbcac";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test042() {
        String partten = "^[0-9]+$"; // 匹配处于0-9之间的字符(依据ASCII码表),至少1次
        String s = "12080384324";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test043() {
        String partten = "^[a-z]+$"; // 匹配处于a-z之间的字符(依据ASCII码表),至少1次
        String s = "adfaefasaef";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test044() {
        String partten = "^[A-Z]+$"; // 匹配处于A-Z之间的字符(依据ASCII码表),至少1次
        String s = "JDSLJFELJIFA";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test045() {
        String partten = "^[a-z0-9A-Z]+$"; // 匹配处于a-z或者0-9或者A-Z之间的字符(依据ASCII码表),至少1次
        String s = "adfaefasaeAFAWEF192301293f";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test046() {
        String partten = "^[A-Z0-9a-z]+$"; // 匹配处于A-Z或者0-9或者a-z之间的字符(依据ASCII码表),至少1次
        String s = "adfaefasaeAFAWEF192301293f";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test047() {
        String partten = "^[^a]+$"; // 匹配非a字符,至少1次
        String s = "aaaaaa";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test048() {
        String partten = "^[^a]+$"; // 匹配非a字符,至少1次
        String s = "111111222";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test049() {
        String partten = "^[^abc]+$"; // 匹配非a,非b，非c字符,至少1次
        String s = "a";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test050() {
        String partten = "^[^abc]+$"; // 匹配非a,非b，非c字符,至少1次
        String s = "b";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test051() {
        String partten = "^[^abc]+$"; // 匹配非a,非b，非c字符,至少1次
        String s = "c";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test052() {
        String partten = "^[^abc]+$"; // 匹配非a,非b，非c字符,至少1次
        String s = "1131213314";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test053() {
        String partten = "^[^abc]+$"; // 匹配非a,非b，非c字符,至少1次
        String s = "1a13b1c21331a4b";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test054() {
        String partten = "^[^abc]+$"; // 匹配非a,非b，非c字符,至少1次
        String s = "1a13b1c21331a4b";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test055() {
        String partten = "^[^abc]+$"; // 匹配非a,非b，非c字符,至少1次
        String s = "1a13b1c21331a4b";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test056() {
        String partten = "^[^0-9]+$"; // 匹配不处于0-9之间的字符(依据ASCII码表),至少1次
        String s = "12080384324";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test057() {
        String partten = "^[^a-z]+$"; // 匹配不处于a-z之间的字符(依据ASCII码表),至少1次
        String s = "adfaefasaef";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test058() {
        String partten = "^[^A-Z]+$"; // 匹配不处于A-Z之间的字符(依据ASCII码表),至少1次
        String s = "JDSLJFELJIFA";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test059() {
        String partten = "^[^a-z0-9A-Z]+$"; // 匹配不处于a-z或者0-9或者A-Z之间的字符(依据ASCII码表),至少1次
        String s = "adfaefasaeAFAWEF192301293f";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test060() {
        String partten = "^[^A-Z0-9a-z]+$"; // 匹配不处于A-Z或者0-9或者a-z之间的字符(依据ASCII码表),至少1次
        String s = "adfaefasaeAFAWEF192301293f";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    // 2.基本的一些基本的与非操作,注意小括号开头前的?:表示不捕获
    
    @Test
    public void test061() {
        String partten = "^(?:abc)+$"; // 匹配abc,至少1次
        String s = "abc";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test062() {
        String partten = "^(?:abc)+$"; // 匹配abc,至少1次
        String s = "abc1";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test063() {
        String partten = "^(?:abc)+$"; // 匹配abc,至少1次
        String s = "abcabcabc";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test064() {
        String partten = "^(?:abc)+$"; // 匹配abc,至少1次
        String s = "abcabcabca";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test065() {
        String partten = "^(?:a|b|c)+$"; // 匹配a或者b或者c,至少1次
        String s = "a";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test066() {
        String partten = "^(?:a|b|c)+$"; // 匹配a或者b或者c,至少1次
        String s = "b";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test067() {
        String partten = "^(?:a|b|c)+$"; // 匹配a或者b或者c,至少1次
        String s = "c";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test068() {
        String partten = "^(?:a|b|c)+$"; // 匹配a或者b或者c,至少1次
        String s = "abccbaaccbbababacacabcc";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    // 3.初级练习
    @Test
    public void test069() {
        String partten = "^[0-9]+$"; // 匹配非负整数
        String s = "123123";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test070() {
        String partten = "^[0-9]+$"; // 匹配非负整数
        String s = "0123123";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test071() {
        String partten = "^[1-9][0-9]+$"; // 匹配非负整数
        String s = "123123";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test072() {
        String partten = "^[1-9][0-9]+$"; // 匹配非负整数
        String s = "0123123";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test073() {
        String partten = "^0b[0-1]+$"; // 匹配二进制数
        String s = "0b101010";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test074() {
        String partten = "^0b[0-1]+$"; // 匹配二进制数
        String s = "0b123123";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test075() {
        String partten = "^0[0-7]+$"; // 匹配八进制数
        String s = "01312312352374623";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test076() {
        String partten = "^0[0-7]+$"; // 匹配八进制数
        String s = "0312318967";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test077() {
        String partten = "^0x[0-9A-F]+$"; // 匹配16进制数
        String s = "0x129FAB";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test078() {
        String partten = "^0x[0-9A-F]+$"; // 匹配16进制数
        String s = "0x312G";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test079() {
        String partten = "^[0-9]*\\.[0-9]+$"; // 匹配非负浮点数  
        String s = "9.372";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test080() {
        String partten = "^-[0-9]*\\.[0-9]+$"; // 匹配负浮点数  
        String s = "-9.372";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    // 4.中级练习
    @Test
    public void test081() {
        String partten = "^[_a-zA-Z][_0-9a-zA-Z]+$"; // 匹配变量名(下划线，数字，字母组成，开头不能是数字)
        String s = "this_is_a_variable_0";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    @Test
    public void test082() {
        String partten = "^[_a-zA-Z][_0-9a-zA-Z]+$"; // 匹配变量名(下划线，数字，字母组成，开头不能是数字)
        String s = "8_this_is_a_variable_0";
        Assert.assertEquals(false, s.matches(partten));
    }
    
    @Test
    public void test083() {
        String partten = "^.+@.+\\.+.+"; // 匹配电子邮箱@在.前面
        String s = "adaefaeaf@sdfae.com";
        Assert.assertEquals(true, s.matches(partten));
    }
    
    
}
