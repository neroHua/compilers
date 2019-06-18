package main.chapter03.part02;

import java.util.List;

/**
 * 
 * 从一个正则表达式中,构造一个NFAGraph
 * 正则表达式如下(a 是ASCII作为字母表的任意一个字符)：
 *      unit:           a                               |
 *                      parentheses                     |
 *                      scopeUnion
 *                      
 *      parentheses:    (expression)
 *      
 *      concatenation:  unit1unit2                      |
 *                      unitclosure                     |
 *                      closureunit                     |
 *                      closure1closure2                |
 *                      unitcountUnion                  |
 *                      countUnionunit                  |
 *                      countUnion1countUnion2          |
 *                      closurecountUnion               |
 *                      countUnionclosure               |
 *                      unitconcatenation               |
 *                      closureconcatenation            |
 *                      countUnionconcatenation
 *                      
 *      union:          unit1|unit2                     |
 *                      unit|concatenation              |
 *                      concatenation|unit              |
 *                      concatenation1|concatenation2   |
 *                      unit|closure                    |
 *                      closure|unit                    |
 *                      closure1|closure2               |
 *                      unit|countUnion                 |
 *                      countUnion|unit                 |
 *                      countUnion1|countUnion2         |
 *                      concatenation|closure           |
 *                      closure|concatenation           |
 *                      concatenation|countUnion        |
 *                      countUnion|concatenation        |
 *                      closure|countUnion              |
 *                      countUnion|closure              |
 *                      unit|union                      |
 *                      concatenation|union             |
 *                      closure|union                   |
 *                      countUnion|union
 *                      
 *      closure:        a*                              |
 *                      parentheses*                    |
 *                      scopeUnion*
 *                      
 *      scopeUnion:     [abcde......] 为a|b|c|d|e......
 *      
 *      countUnion:     unit{0,n} 为 empty|unit|unitunit|unitunitunit|........n个unit
 *      
 *      expression:     unit                            |
 *                      parentheses                     |
 *                      concatenation                   |
 *                      union                           |
 *                      closure                         |
 *                      scopeUnion                      |
 *                      countUnion
 * 
 *  特别说明:因为语法说明的时候已经使用了|()[]{},*这9个字符作为关键字，所以匹配以上几个字符时请使用\|\(\)\[\]\{\}\,\*而匹配\请用\\
 * 
 * 
 * @author 滑德友
 * @time 2019年3月15日18:09:05 
 *
 */
public class NFA {
    
    private char[] specialKey = {'\\', '|', '(', ')', '[', ']', '{', '}', ',', '*'};
    private char[] ASCII = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F,
            0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2B, 0x2C, 0x2D, 0x2E, 0x2F,
            0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x3B, 0x3C, 0x3D, 0x3E, 0x3F,
            0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4A, 0x4B, 0x4C, 0x4D, 0x4E, 0x4F,
            0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5A, 0x5B, 0x5C, 0x5D, 0x5E, 0x5F,
            0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A, 0x7B, 0x7C, 0x7D, 0x7E, 0x7F};
    
    public Boolean match(char[] sourceChar, char[] regularExpression) {
        NFAGraph graph = getNFAGraph(regularExpression);
        return subMatch(sourceChar, 0, 0, graph);
    }
    
    private Boolean subMatch(char[] sourceChar, int currentIndex, int currentState, NFAGraph nfaGraph) {
        List<Integer> nextStateByChar = sourceChar.length == currentIndex ? null : nfaGraph.getNextState(currentState, sourceChar[currentIndex]);
        List<Integer> nextStateByE = nfaGraph.getNextStateByE(currentState);
        // 结束条件1
        // currentIndex == sourceChar.length时，则他的上一步有可能来是沿着字符courceChar[sourceChar.length - 1] 走的
        // 只需要查看沿着E走，是否包含end状态,不包含则接着往下走
        if (currentIndex == sourceChar.length) {
            if (null != nextStateByE && nextStateByE.contains(nfaGraph.getEndState())) {
                return true;
            }
        }
        // 结束条件2
        // currentIndex == sourceChar.length - 1时，
        // 查看沿着sourceChar[sourceChar.length - 1]是否包含end状态,不包含则接着往下走
        if (sourceChar.length - 1 == currentIndex) {
            if (null != nextStateByChar && nextStateByChar.contains(nfaGraph.getEndState())) {
                return true;
            }
        }

        // 情况1：沿着当前字符走
        if (null != nextStateByChar) {
            return subMatchNextByChar(sourceChar, currentIndex, nfaGraph, nextStateByChar);
        }
        
        // 情况2：沿着E走
        if (null != nextStateByE) {
            return subMatchNextByE(sourceChar, currentIndex, nfaGraph, nextStateByE);
        }

        // 情况3：没有路可以走
        return false;
    }

    private NFAGraph getNFAGraph(char[] regularExpression) {
        NFAScanner nfaScanner = new NFAScanner(regularExpression);
        NFAParser nfaParser = new NFAParser(nfaScanner);
        NFABinaryTree nfaBinaryTree = nfaParser.parse();
        return nfaBinaryTree.getNFAGraph(nfaBinaryTree.rootNode);
    }

    private boolean subMatchNextByChar(char[] sourceChar, int currentIndex, NFAGraph nfaGraph,
            List<Integer> nextStateByChar) {
        boolean result = false;
        for (int i : nextStateByChar) {
            result |= subMatch(sourceChar, currentIndex + 1, i, nfaGraph);
            if (result) {
                return true;
            }
        }
        return false;
    }

    private boolean subMatchNextByE(char[] sourceChar, int currentIndex, NFAGraph nfaGraph, List<Integer> nextStateByE) {
        boolean result = false;
        for (int i : nextStateByE) {
            result |= subMatch(sourceChar, currentIndex, i, nfaGraph);
            if (result) {
                return true;
            }
        }
        return false;
    }

}