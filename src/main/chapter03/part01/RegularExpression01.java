package main.chapter03.part01;

/**
 * 
 * 正则表达式 和有限状态自动机装换成(finite automata)
 * 
 * 将有限状态自动机转化成正则表达式核心思想是：将多个状态转成合并成一个可以接受的状态，在合并的过程中保持路径的连续性。
 * 
 * @author 滑德友
 * @time 2019年1月8日20:34:02
 *
 */
public class RegularExpression01 {
    
    public final String REGULAR_EXPRESSION_3_3_5_A = "[a-z&&[^aeiou]]*a*[a-z&&[^eiou]]*e*[a-z&&[^aiou]]*i*[a-z&&[^aeou]]*o*[a-z&&[^aeiu]]*u*[a-z&&[^aeio]]*$";

    public final String REGULAR_EXPRESSION_3_3_5_B = "^a*b*c*d*e*f*g*h*i*j*k*m*n*o*p*q*r*s*t*u*v*w*x*y*z*$";

    public final String REGULAR_EXPRESSION_3_3_5_C = "^\\/\\*(((?!.*(\\*\\/).*).)*|([^\"]*\"[^\"]*\\*\\/[^\"]*\"[^\"]*))\\*\\/$";
    
    public final String REGULAR_EXPRESSION_3_3_5_D_0 = "^(?:00[0-9]*)|(?:0[1-9]*0[0-9]*)|(?:[1-9]*0[1-9]*0[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_1 = "^(?:11[0-9]*)|(?:1[02-9]*1[0-9]*)|(?:[02-9]*1[02-9]*1[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_2 = "^(?:22[0-9]*)|(?:2[0-13-9]*2[0-9]*)|(?:[0-13-9]*2[0-13-9]*2[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_3 = "^(?:33[0-9]*)|(?:3[0-24-9]*3[0-9]*)|(?:[0-24-9]*3[0-24-9]*3[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_4 = "^(?:44[0-9]*)|(?:4[0-35-9]*4[0-9]*)|(?:[0-35-9]*4[0-35-9]*4[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_5 = "^(?:55[0-9]*)|(?:5[0-46-9]*5[0-9]*)|(?:[0-46-9]*5[0-46-9]*5[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_6 = "^(?:66[0-9]*)|(?:6[0-57-9]*6[0-9]*)|(?:[0-57-9]*6[0-57-9]*6[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_7 = "^(?:77[0-9]*)|(?:7[0-68-9]*7[0-9]*)|(?:[0-68-9]*7[0-68-9]*7[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_8 = "^(?:88[0-9]*)|(?:8[0-79]*8[0-9]*)|(?:[0-79]*8[0-79]*8[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_9 = "^(?:99[0-9]*)|(?:9[0-8]*9[0-9]*)|(?:[0-8]*9[0-8]*9[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_D_X = "^[0-9]*$";
    public final String REGULAR_EXPRESSION_3_3_5_D = "!(0|1|2|3|4|5|6|7|8|9) && X";

    public final String REGULAR_EXPRESSION_3_3_5_E_0 = "^(?:000[0-9]*)|(?:00[1-9]*0[0-9]*)|(?:0[1-9]*0[1-9]*0[0-9]*)|(?:[1-9]*0[1-9]*0[1-9]*0[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_1 = "^(?:111[0-9]*)|(?:11[02-9]*1[0-9]*)|(?:1[02-9]*1[02-9]*1[0-9]*)|(?:[02-9]*1[02-9]*1[02-9]*1[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_2 = "^(?:222[0-9]*)|(?:22[0-13-9]*2[0-9]*)|(?:2[0-13-9]*2[0-13-9]*2[0-9]*)|(?:[0-13-9]*2[0-13-9]*2[0-13-9]*2[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_3 = "^(?:333[0-9]*)|(?:33[0-24-9]*3[0-9]*)|(?:3[0-24-9]*3[0-24-9]*3[0-9]*)|(?:[0-24-9]*3[0-24-9]*3[0-24-9]*3[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_4 = "^(?:444[0-9]*)|(?:44[0-35-9]*4[0-9]*)|(?:4[0-35-9]*4[0-35-9]*4[0-9]*)|(?:[0-35-9]*4[0-35-9]*4[0-35-9]*4[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_5 = "^(?:555[0-9]*)|(?:55[0-46-9]*5[0-9]*)|(?:5[0-46-9]*5[0-46-9]*5[0-9]*)|(?:[0-46-9]*5[0-46-9]*5[0-46-9]*5[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_6 = "^(?:666[0-9]*)|(?:66[0-57-9]*6[0-9]*)|(?:6[0-57-9]*6[0-57-9]*6[0-9]*)|(?:[0-57-9]*6[0-57-9]*6[0-57-9]*6[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_7 = "^(?:777[0-9]*)|(?:77[0-68-9]*7[0-9]*)|(?:7[0-68-9]*7[0-68-9]*7[0-9]*)|(?:[0-68-9]*7[0-68-9]*7[0-68-9]*7[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_8 = "^(?:888[0-9]*)|(?:88[0-79]*8[0-9]*)|(?:8[0-79]*8[0-79]*8[0-9]*)|(?:[0-79]*8[0-79]*8[0-79]*8[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_9 = "^(?:999[0-9]*)|(?:99[0-8]*9[0-9]*)|(?:9[0-8]*9[0-8]*9[0-9]*)|(?:[0-8]*9[0-8]*9[0-8]*9[0-9]*)$";
    public final String REGULAR_EXPRESSION_3_3_5_E_X = "^[0-9]*$";
    public final String REGULAR_EXPRESSION_3_3_5_E = "!(0|1|2|3|4|5|6|7|8|9) && X";

    public final String REGULAR_EXPRESSION_3_3_5_F_A1 = "^b*ab*(?:b*ab*ab*)*$";
    public final String REGULAR_EXPRESSION_3_3_5_F_A2 = "^(?:(?:b*ab*ab*)*b*ab*)$";
    public final String REGULAR_EXPRESSION_3_3_5_F_B1 = "^(?:(?:a*ba*ba*)*)$";
    public final String REGULAR_EXPRESSION_3_3_5_F_X = "^[a-b]*$";
    public final String REGULAR_EXPRESSION_3_3_5_F = "^(?:b|a(?:aa|bb)*(?:ab|ba))(?:aa|bb|(?:ba|ab)(?:aa|bb)*(?:ab|ba))*)$";

    public final String REGULAR_EXPRESSION_3_3_5_H_0 = "[ab]*(?:abb)[ab]*";
    public final String REGULAR_EXPRESSION_3_3_5_H_X = "^[a-b]*$";
    public final String REGULAR_EXPRESSION_3_3_5_H = "!(0) && X";
    
    public final String REGULAR_EXPRESSION_3_3_5_I_0 = "(?:[ab]*a[ab]*b[ab]*b[ab]*)|(?:[ab]*b[ab]*a[ab]*b[ab]*)|(?:[ab]*b[ab]*b[ab]*a[ab]*)";
    public final String REGULAR_EXPRESSION_3_3_5_I_X = "^[a-b]*$";
    public final String REGULAR_EXPRESSION_3_3_5_I = "!(0) && X";
    
}
