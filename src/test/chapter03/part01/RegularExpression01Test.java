package test.chapter03.part01;

import org.junit.Assert;
import org.junit.Test;
import main.chapter03.part01.RegularExpression01;

public class RegularExpression01Test {

    RegularExpression01 regularExpression01 = new RegularExpression01();
    
    @Test
    public void test001() {
        String s = "aaa";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_A));
    }
    
    @Test
    public void test002() {
        String s = "aab";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_A));
    }
    
    @Test
    public void test003() {
        String s = "fwaafwefw";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_A));
    }
    
    @Test
    public void test004() {
        String s = "fwaafwefwedi";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_A));
    }
    
    @Test
    public void test005() {
        String s = "fwaafwefweda";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_A));
    }
    
    @Test
    public void test006() {
        String s = "/**/*/";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_C));
    }
    
    @Test
    public void test007() {
        String s = "/*\"*/\"*/";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_C));
    }
    
    @Test
    public void test008() {
        String s = "023";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_0));
    }
    
    @Test
    public void test009() {
        String s = "0230";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_0));
    }
    
    @Test
    public void test010() {
        String s = "0203";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_0));
    }
    
    @Test
    public void test011() {
        String s = "003";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_0));
    }
    
    @Test
    public void test012() {
        String s = "400";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_0));
    }
    
    @Test
    public void test013() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_0));
    }
    
    @Test
    public void test014() {
        String s = "123";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_1));
    }
    
    @Test
    public void test015() {
        String s = "1231";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_1));
    }
    
    @Test
    public void test016() {
        String s = "1213";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_1));
    }
    
    @Test
    public void test017() {
        String s = "113";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_1));
    }
    
    @Test
    public void test018() {
        String s = "411";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_1));
    }
    
    @Test
    public void test019() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_1));
    }
    
    @Test
    public void test020() {
        String s = "823";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_8));
    }
    
    @Test
    public void test021() {
        String s = "8238";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_8));
    }
    
    @Test
    public void test022() {
        String s = "8283";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_8));
    }
    
    @Test
    public void test023() {
        String s = "883";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_8));
    }
    
    @Test
    public void test024() {
        String s = "488";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_8));
    }
    
    @Test
    public void test025() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_8));
    }
    
    @Test
    public void test026() {
        String s = "923";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_9));
    }
    
    @Test
    public void test027() {
        String s = "9239";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_9));
    }
    
    @Test
    public void test028() {
        String s = "9293";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_9));
    }
    
    @Test
    public void test029() {
        String s = "993";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_9));
    }
    
    @Test
    public void test030() {
        String s = "499";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_9));
    }
    
    @Test
    public void test031() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_D_9));
    }

    @Test
    public void test032() {
        String s = "023";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test033() {
        String s = "0230";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test034() {
        String s = "0203";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test035() {
        String s = "003";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test036() {
        String s = "400";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test037() {
        String s = "0001";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test038() {
        String s = "0010";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test039() {
        String s = "0100";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test040() {
        String s = "1000";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test041() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_0));
    }
    
    @Test
    public void test042() {
        String s = "123";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test043() {
        String s = "1231";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test044() {
        String s = "1213";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test045() {
        String s = "113";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test046() {
        String s = "411";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test047() {
        String s = "1113";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test048() {
        String s = "1131";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test049() {
        String s = "1311";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test050() {
        String s = "3111";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test051() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_1));
    }
    
    @Test
    public void test052() {
        String s = "823";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test053() {
        String s = "8238";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test054() {
        String s = "8283";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test055() {
        String s = "883";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test056() {
        String s = "488";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test057() {
        String s = "8883";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test058() {
        String s = "8838";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test059() {
        String s = "8388";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test060() {
        String s = "3888";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test061() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_8));
    }
    
    @Test
    public void test062() {
        String s = "923";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test063() {
        String s = "9239";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test064() {
        String s = "9293";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test065() {
        String s = "993";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test066() {
        String s = "499";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test067() {
        String s = "9993";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test068() {
        String s = "9939";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test069() {
        String s = "9399";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test070() {
        String s = "3999";
        Assert.assertEquals(true, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
    @Test
    public void test071() {
        String s = "ab4";
        Assert.assertEquals(false, s.matches(regularExpression01.REGULAR_EXPRESSION_3_3_5_E_9));
    }
    
}
