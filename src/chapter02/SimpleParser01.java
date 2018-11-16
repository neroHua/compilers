package chapter02;

/**
 * 此类用于将
 * 简单的中缀算术运算符表达式转换成对应的前缀和后缀算术运算表达式
 * 简单的算术表达式应该只包含+，-,0,1,2,3,4,5,6,7,8,9这些字符
 * 这里扩展支持()这两个字符
 * 
 * 
 * @author neroHua
 * @time 2018年10月4日22:18:28
 *
 */
public class SimpleParser01 {

	/**
	 * productions:
	 * 	expression: digit |
	 * 				expression + digit |
	 * 				expression - digit |
	 *              term |
	 *              expression + term |
	 *              expression - term
	 *              
	 *  term:       (digit) |
	 *              (expression)
	 *              
	 * 	digit:      0 |
	 * 				1 |
	 * 				2 |
	 * 				3 |
	 * 				4 |
	 * 				5 |
	 * 				6 |
	 * 				7 |
	 * 				8 |
	 * 				9
	 * 
	 * translate schemes:
	 * 	expression: digit {'digit'} |
	 *              expression + digit {'expression digit +'} |
	 *              expression - digit {'expression digit -'} |
	 *              term {'term'} |
	 *              expression + term {'expression term +'} |
	 *              expression - term {'expression term -'}
	 *              
	 *  term:       (digit) {'digit'} |
	 *              (expression) {'expression'}
	 *              
	 * 	digit:      0 {'0'} |
	 *              1 {'1'} |
	 *              2 {'2'} |
	 *              3 {'3'} |
	 *              4 {'4'} |
	 *              5 {'5'} |
	 *              6 {'6'} |
	 *              7 {'7'} |
	 *              8 {'8'} |
	 *              9 {'9'}
	 * 
	 * @param expression
	 * @return
	 */
	public char[] postFixScanFromRightToLeft(char[] inFixExpression) {
		return subPostFixScanFromRightToLeft(inFixExpression, inFixExpression.length - 1, 0, inFixExpression.length - 1);
	}

	private char[] subPostFixScanFromRightToLeft(char[] inFixExpression, int currentCharIndex,  int startCharIndex, int endCharIndex) {
		if(currentCharIndex < 0) {
			return null;
		}
		
		int[] matchExpressionMessage = matchExpression01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
		switch (matchExpressionMessage[0]) {
            case 1:
                char[] returnChar = new char[1];
                returnChar[0] = inFixExpression[currentCharIndex];
                return returnChar;
            case 2:
            case 3:
                char[] part11 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 2, startCharIndex, endCharIndex - 2);
                char part12 = inFixExpression[matchExpressionMessage[1]];
                char part13 = inFixExpression[currentCharIndex];
                return doMatchExpression01(part11, part12, part13);
            case 4:
                return subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
            case 5:
            case 6:
                char[] part21 = subPostFixScanFromRightToLeft(inFixExpression, matchExpressionMessage[1] - 1, startCharIndex, matchExpressionMessage[1] - 1);
                char part22 = inFixExpression[matchExpressionMessage[1]];
                char[] part23 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, matchExpressionMessage[1] + 2, endCharIndex - 1);
                return doMatchExpression01(part21, part22, part23);
            default:
                break;
        }
		
		int matchTermType = matchTerm01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
		switch (matchTermType) {
            case 1:
                return doMatchDigit01(inFixExpression, currentCharIndex);
            case 2:
                return subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
            default:
                break;
        }
		
		if (matchDigit01(inFixExpression, currentCharIndex)) {
		    return doMatchDigit01(inFixExpression, currentCharIndex);
		}

		throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组");
	}

    private int[] matchExpression01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
		if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && currentCharIndex == startCharIndex) {
			return packMatchExpression01Message(1, 0);
		}
		if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex - 1] == '+') {
			return packMatchExpression01Message(2, currentCharIndex - 1);
		}
		if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex - 1] == '-') {
			return packMatchExpression01Message(3, currentCharIndex - 1);
		}
		if(inFixExpression[currentCharIndex] == ')' && startCharIndex <= endCharIndex - 2) {
		    int matchParenthese = 1;
		    for( int i = currentCharIndex - 1; i >= startCharIndex; i--) {
		        if(inFixExpression[i] == ')') {
		            matchParenthese++;
		        } else if (inFixExpression[i] == '(') {
		            matchParenthese--;
		        }
		        
		        if(matchParenthese == 0) {
		            if(i == startCharIndex) {
		                return packMatchExpression01Message(4, 0);
		            }
		            if(inFixExpression[i - 1] == '+') {
                        return packMatchExpression01Message(5, i - 1);
                    }
                    if(inFixExpression[i - 1] == '-') {
                        return packMatchExpression01Message(6, i - 1);
                    }
                    break;
		        }
		    }
        }
		return packMatchExpression01Message(0, 0);
	}
	
	private int[] packMatchExpression01Message(int matchExpressionType, int addOrSubtractIndex) {
	    int[] returnMatchExpressionMessage = new int[2];
	    returnMatchExpressionMessage[0] = matchExpressionType;
	    returnMatchExpressionMessage[1] = addOrSubtractIndex;
	    return returnMatchExpressionMessage;
	};
	
    private char[] doMatchExpression01(char[] part1, char part2, char part3) {
        char[] part = new char[part1.length + 1 + 1];

        for (int i = 0, j = 0; j < part1.length; i++, j++) {
            part[i] = part1[j];
        }
        part[part.length - 2] = part3;
        part[part.length - 1] = part2;

        return part;
    }
	   
	private char[] doMatchExpression01(char[] part1, char part2, char[] part3) {
		char[] part = new char[part1.length + 1 + part3.length];
		
		for(int i = 0, j = 0; j < part1.length; i++, j++){
			part[i] = part1[j];
		}
		for(int i = part1.length, j = 0; j < part3.length; i++, j++ ) {
			part[i] = part3[j];
		}
		part[part.length - 1] = part2;
		
		return part;
	}
	

    private int matchTerm01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
            if(endCharIndex - startCharIndex == 2 && inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' ) {
                return 1;
            } else {
                return 2;
            }
        }
        
        return 0;
    }

	private boolean matchDigit01(char[] inFixExpression, int currentCharIndex) {
		if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
			return true;
		}
		return false;
	}
	
	private char[] doMatchDigit01(char[] inFixExpression, int currentCharIndex) {
		char[] returnChar = new char[1];
		returnChar[0] = inFixExpression[currentCharIndex];
		return returnChar;
	}
	
	/**
     * productions:
     *  expression: digit |
     *              expression + digit |
     *              expression - digit |
     *              term |
     *              expression + term |
     *              expression - term
     *              
     *  term:       (digit) |
     *              (expression)
     *              
     *  digit:      0 |
     *              1 |
     *              2 |
     *              3 |
     *              4 |
     *              5 |
     *              6 |
     *              7 |
     *              8 |
     *              9
     * 
     * translate schemes:
     *  expression: digit {'digit'} |
     *              expression + digit {'+ expression digit'} |
     *              expression - digit {'- expression digit'} |
     *              term {'term'} |
     *              expression + term {'+ expression term'} |
     *              expression - term {'- expression term'}
     *              
     *  term:       (digit) {'digit'} |
     *              (expression) {'expression'}
     *              
     *  digit:      0 {'0'} |
     *              1 {'1'} |
     *              2 {'2'} |
     *              3 {'3'} |
     *              4 {'4'} |
     *              5 {'5'} |
     *              6 {'6'} |
     *              7 {'7'} |
     *              8 {'8'} |
     *              9 {'9'}
     * 
     * @param expression
     * @return
     */
    public char[] preFixScanFromRightToLeft(char[] inFixExpression) {
        return subPreFixScanFromRightToLeft(inFixExpression, inFixExpression.length - 1, 0, inFixExpression.length - 1);
    }

    private char[] subPreFixScanFromRightToLeft(char[] inFixExpression, int currentCharIndex,  int startCharIndex, int endCharIndex) {
        if(currentCharIndex < 0) {
            return null;
        }
        
        int[] matchExpressionMessage = matchExpression02(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        switch (matchExpressionMessage[0]) {
            case 1:
                char[] returnChar = new char[1];
                returnChar[0] = inFixExpression[currentCharIndex];
                return returnChar;
            case 2:
            case 3:
                char[] part11 = subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex - 2, startCharIndex, endCharIndex - 2);
                char part12 = inFixExpression[matchExpressionMessage[1]];
                char part13 = inFixExpression[currentCharIndex];
                return doMatchExpression02(part11, part12, part13);
            case 4:
                return subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
            case 5:
            case 6:
                char[] part21 = subPreFixScanFromRightToLeft(inFixExpression, matchExpressionMessage[1] - 1, startCharIndex, matchExpressionMessage[1] - 1);
                char part22 = inFixExpression[matchExpressionMessage[1]];
                char[] part23 = subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, matchExpressionMessage[1] + 2, endCharIndex - 1);
                return doMatchExpression02(part21, part22, part23);
            default:
                break;
        }
        
        int matchTermType = matchTerm02(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        switch (matchTermType) {
            case 1:
                return doMatchDigit02(inFixExpression, currentCharIndex);
            case 2:
                return subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
            default:
                break;
        }
        
        if (matchDigit02(inFixExpression, currentCharIndex)) {
            return doMatchDigit02(inFixExpression, currentCharIndex);
        }

        throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组");
    }

    private int[] matchExpression02(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && currentCharIndex == startCharIndex) {
            return packMatchExpression02Message(1, 0);
        }
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex - 1] == '+') {
            return packMatchExpression02Message(2, currentCharIndex - 1);
        }
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex - 1] == '-') {
            return packMatchExpression02Message(3, currentCharIndex - 1);
        }
        if(inFixExpression[currentCharIndex] == ')' && startCharIndex <= endCharIndex - 2) {
            int matchParenthese = 1;
            for( int i = currentCharIndex - 1; i >= startCharIndex; i--) {
                if(inFixExpression[i] == ')') {
                    matchParenthese++;
                } else if (inFixExpression[i] == '(') {
                    matchParenthese--;
                }
                
                if(matchParenthese == 0) {
                    if(i == startCharIndex) {
                        return packMatchExpression02Message(4, 0);
                    }
                    if(inFixExpression[i - 1] == '+') {
                        return packMatchExpression02Message(5, i - 1);
                    }
                    if(inFixExpression[i - 1] == '-') {
                        return packMatchExpression02Message(6, i - 1);
                    }
                    break;
                }
            }
        }
        return packMatchExpression02Message(0, 0);
    }
    
    private int[] packMatchExpression02Message(int matchExpressionType, int addOrSubtractIndex) {
        int[] returnMatchExpressionMessage = new int[2];
        returnMatchExpressionMessage[0] = matchExpressionType;
        returnMatchExpressionMessage[1] = addOrSubtractIndex;
        return returnMatchExpressionMessage;
    };
    
    private char[] doMatchExpression02(char[] part1, char part2, char part3) {
        char[] part = new char[part1.length + 1 + 1];

        part[0] = part2;
        for (int i = 1, j = 0; j < part1.length; i++, j++) {
            part[i] = part1[j];
        }
        part[part.length - 1] = part3;

        return part;
    }
       
    private char[] doMatchExpression02(char[] part1, char part2, char[] part3) {
        char[] part = new char[part1.length + 1 + part3.length];
        
        part[0] = part2;
        for(int i = 1, j = 0; j < part1.length; i++, j++){
            part[i] = part1[j];
        }
        for(int i = part1.length + 1, j = 0; j < part3.length; i++, j++ ) {
            part[i] = part3[j];
        }
        
        return part;
    }
    

    private int matchTerm02(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
            if(endCharIndex - startCharIndex == 2 && inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' ) {
                return 1;
            } else {
                return 2;
            }
        }
        
        return 0;
    }

    private boolean matchDigit02(char[] inFixExpression, int currentCharIndex) {
        if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            return true;
        }
        return false;
    }
    
    private char[] doMatchDigit02(char[] inFixExpression, int currentCharIndex) {
        char[] returnChar = new char[1];
        returnChar[0] = inFixExpression[currentCharIndex];
        return returnChar;
    }
    
    /**
     * productions:
     *  expression: digit |
     *              digit + expression |
     *              digit - expression |
     *              term |
     *              term + expression |
     *              term - expression
     *              
     *  term:       (digit) |
     *              (expression)
     *              
     *  digit:      0 |
     *              1 |
     *              2 |
     *              3 |
     *              4 |
     *              5 |
     *              6 |
     *              7 |
     *              8 |
     *              9
     * 
     * translate schemes:
     *  expression: digit {'digit'} |
     *              digit + expression {'digit expression +'} |
     *              digit - expression {'digit expression -'} |
     *              term {'term'} |
     *              term + expression {'term expression +'} |
     *              term - expression {'term expression -'}
     *              
     *  term:       (digit) {'digit'} |
     *              (expression) {'expression'}
     *              
     *  digit:      0 {'0'} |
     *              1 {'1'} |
     *              2 {'2'} |
     *              3 {'3'} |
     *              4 {'4'} |
     *              5 {'5'} |
     *              6 {'6'} |
     *              7 {'7'} |
     *              8 {'8'} |
     *              9 {'9'}
     * 
     * @param expression
     * @return
     */
    public char[] postFixScanFromLeftToRight(char[] inFixExpression) {
        return subPostFixScanFromLeftToRight(inFixExpression, 0, 0, inFixExpression.length - 1);
    }

    private char[] subPostFixScanFromLeftToRight(char[] inFixExpression, int currentCharIndex,  int startCharIndex, int endCharIndex) {
        if(currentCharIndex < 0) {
            return null;
        }
        
        int[] matchExpressionMessage = matchExpression03(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        switch (matchExpressionMessage[0]) {
            case 1:
                char[] returnChar = new char[1];
                returnChar[0] = inFixExpression[currentCharIndex];
                return returnChar;
            case 2:
            case 3:
                char part11 = inFixExpression[currentCharIndex];
                char part12 = inFixExpression[matchExpressionMessage[1]];
                char[] part13 = subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 2, startCharIndex + 2, endCharIndex);
                return doMatchExpression03(part11, part12, part13);
            case 4:
                return subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
            case 5:
            case 6:
                char[] part21 = subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, matchExpressionMessage[1] - 2);
                char part22 = inFixExpression[matchExpressionMessage[1]];
                char[] part23 = subPostFixScanFromLeftToRight(inFixExpression, matchExpressionMessage[1] + 1, matchExpressionMessage[1] + 1, endCharIndex);
                return doMatchExpression03(part21, part22, part23);
            default:
                break;
        }
        
        int matchTermType = matchTerm03(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        switch (matchTermType) {
            case 1:
                return doMatchDigit03(inFixExpression, currentCharIndex);
            case 2:
                return subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
            default:
                break;
        }
        
        if (matchDigit03(inFixExpression, currentCharIndex)) {
            return doMatchDigit03(inFixExpression, currentCharIndex);
        }

        throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组");
    }

    private int[] matchExpression03(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && currentCharIndex == endCharIndex) {
            return packMatchExpression03Message(1, 0);
        }
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex + 1] == '+') {
            return packMatchExpression03Message(2, currentCharIndex + 1);
        }
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex + 1] == '-') {
            return packMatchExpression03Message(3, currentCharIndex + 1);
        }
        if(inFixExpression[currentCharIndex] == '(' && startCharIndex <= endCharIndex - 2) {
            int matchParenthese = 1;
            for( int i = currentCharIndex + 1; i <= endCharIndex; i++) {
                if(inFixExpression[i] == '(') {
                    matchParenthese++;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese--;
                }
                
                if(matchParenthese == 0) {
                    if(i == endCharIndex) {
                        return packMatchExpression03Message(4, 0);
                    }
                    if(inFixExpression[i + 1] == '+') {
                        return packMatchExpression03Message(5, i + 1);
                    }
                    if(inFixExpression[i + 1] == '-') {
                        return packMatchExpression03Message(6, i + 1);
                    }
                    break;
                }
            }
        }
        return packMatchExpression03Message(0, 0);
    }
    
    private int[] packMatchExpression03Message(int matchExpressionType, int addOrSubtractIndex) {
        int[] returnMatchExpressionMessage = new int[2];
        returnMatchExpressionMessage[0] = matchExpressionType;
        returnMatchExpressionMessage[1] = addOrSubtractIndex;
        return returnMatchExpressionMessage;
    };
    
    private char[] doMatchExpression03(char part1, char part2, char[] part3) {
        char[] part = new char[ 1 + 1 + part3.length];

        part[0] = part1;
        for (int i = 1, j = 0; j < part3.length; i++, j++) {
            part[i] = part3[j];
        }
        part[part.length - 1] = part2;

        return part;
    }
       
    private char[] doMatchExpression03(char[] part1, char part2, char[] part3) {
        char[] part = new char[part1.length + 1 + part3.length];
        
        for(int i = 0, j = 0; j < part1.length; i++, j++){
            part[i] = part1[j];
        }
        for(int i = part1.length, j = 0; j < part3.length; i++, j++ ) {
            part[i] = part3[j];
        }
        part[part.length - 1] = part2;
        
        return part;
    }
    

    private int matchTerm03(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
            if(endCharIndex - startCharIndex == 2 && inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' ) {
                return 1;
            } else {
                return 2;
            }
        }
        
        return 0;
    }

    private boolean matchDigit03(char[] inFixExpression, int currentCharIndex) {
        if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            return true;
        }
        return false;
    }
    
    private char[] doMatchDigit03(char[] inFixExpression, int currentCharIndex) {
        char[] returnChar = new char[1];
        returnChar[0] = inFixExpression[currentCharIndex];
        return returnChar;
    }
    
    /**
     * productions:
     *  expression: digit |
     *              digit + expression |
     *              digit - expression |
     *              term |
     *              term + expression |
     *              term - expression
     *              
     *  term:       (digit) |
     *              (expression)
     *              
     *  digit:      0 |
     *              1 |
     *              2 |
     *              3 |
     *              4 |
     *              5 |
     *              6 |
     *              7 |
     *              8 |
     *              9
     * 
     * translate schemes:
     *  expression: digit {'digit'} |
     *              digit + expression {'+ digit expression'} |
     *              digit - expression {'- digit expression'} |
     *              term {'term'} |
     *              term + expression {'+ term expression'} |
     *              term - expression {'- term expression'}
     *              
     *  term:       (digit) {'digit'} |
     *              (expression) {'expression'}
     *              
     *  digit:      0 {'0'} |
     *              1 {'1'} |
     *              2 {'2'} |
     *              3 {'3'} |
     *              4 {'4'} |
     *              5 {'5'} |
     *              6 {'6'} |
     *              7 {'7'} |
     *              8 {'8'} |
     *              9 {'9'}
     * 
     * @param expression
     * @return
     */
    public char[] preFixScanFromLeftToRight(char[] inFixExpression) {
        return subpreFixScanFromLeftToRight(inFixExpression, 0, 0, inFixExpression.length - 1);
    }

    private char[] subpreFixScanFromLeftToRight(char[] inFixExpression, int currentCharIndex,  int startCharIndex, int endCharIndex) {
        if(currentCharIndex < 0) {
            return null;
        }
        
        int[] matchExpressionMessage = matchExpression04(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        switch (matchExpressionMessage[0]) {
            case 1:
                char[] returnChar = new char[1];
                returnChar[0] = inFixExpression[currentCharIndex];
                return returnChar;
            case 2:
            case 3:
                char part11 = inFixExpression[currentCharIndex];
                char part12 = inFixExpression[matchExpressionMessage[1]];
                char[] part13 = subpreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 2, startCharIndex + 2, endCharIndex);
                return doMatchExpression04(part11, part12, part13);
            case 4:
                return subpreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
            case 5:
            case 6:
                char[] part21 = subpreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, matchExpressionMessage[1] - 2);
                char part22 = inFixExpression[matchExpressionMessage[1]];
                char[] part23 = subpreFixScanFromLeftToRight(inFixExpression, matchExpressionMessage[1] + 1, matchExpressionMessage[1] + 1, endCharIndex);
                return doMatchExpression04(part21, part22, part23);
            default:
                break;
        }
        
        int matchTermType = matchTerm04(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        switch (matchTermType) {
            case 1:
                return doMatchDigit04(inFixExpression, currentCharIndex);
            case 2:
                return subpreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
            default:
                break;
        }
        
        if (matchDigit04(inFixExpression, currentCharIndex)) {
            return doMatchDigit04(inFixExpression, currentCharIndex);
        }

        throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组");
    }

    private int[] matchExpression04(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && currentCharIndex == endCharIndex) {
            return packMatchExpression04Message(1, 0);
        }
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex + 1] == '+') {
            return packMatchExpression04Message(2, currentCharIndex + 1);
        }
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' && inFixExpression[currentCharIndex + 1] == '-') {
            return packMatchExpression04Message(3, currentCharIndex + 1);
        }
        if(inFixExpression[currentCharIndex] == '(' && startCharIndex <= endCharIndex - 2) {
            int matchParenthese = 1;
            for( int i = currentCharIndex + 1; i <= endCharIndex; i++) {
                if(inFixExpression[i] == '(') {
                    matchParenthese++;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese--;
                }
                
                if(matchParenthese == 0) {
                    if(i == endCharIndex) {
                        return packMatchExpression04Message(4, 0);
                    }
                    if(inFixExpression[i + 1] == '+') {
                        return packMatchExpression04Message(5, i + 1);
                    }
                    if(inFixExpression[i + 1] == '-') {
                        return packMatchExpression04Message(6, i + 1);
                    }
                    break;
                }
            }
        }
        return packMatchExpression04Message(0, 0);
    }
    
    private int[] packMatchExpression04Message(int matchExpressionType, int addOrSubtractIndex) {
        int[] returnMatchExpressionMessage = new int[2];
        returnMatchExpressionMessage[0] = matchExpressionType;
        returnMatchExpressionMessage[1] = addOrSubtractIndex;
        return returnMatchExpressionMessage;
    };
    
    private char[] doMatchExpression04(char part1, char part2, char[] part3) {
        char[] part = new char[ 1 + 1 + part3.length];

        part[0] = part2;
        part[1] = part1;
        for (int i = 2, j = 0; j < part3.length; i++, j++) {
            part[i] = part3[j];
        }

        return part;
    }
       
    private char[] doMatchExpression04(char[] part1, char part2, char[] part3) {
        char[] part = new char[part1.length + 1 + part3.length];
        
        part[0] = part2;
        for(int i = 1, j = 0; j < part1.length; i++, j++){
            part[i] = part1[j];
        }
        for(int i = part1.length + 1, j = 0; j < part3.length; i++, j++ ) {
            part[i] = part3[j];
        }
        
        return part;
    }
    

    private int matchTerm04(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
            if(endCharIndex - startCharIndex == 2 && inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9' ) {
                return 1;
            } else {
                return 2;
            }
        }
        
        return 0;
    }

    private boolean matchDigit04(char[] inFixExpression, int currentCharIndex) {
        if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            return true;
        }
        return false;
    }
    
    private char[] doMatchDigit04(char[] inFixExpression, int currentCharIndex) {
        char[] returnChar = new char[1];
        returnChar[0] = inFixExpression[currentCharIndex];
        return returnChar;
    }
    
	public static void main(String[] args) {
		SimpleParser01 simpleParser = new SimpleParser01();
		char[] inFixExpression = "((9-5)+(2))".toCharArray();
		
        char[] postFixExpression1 = simpleParser.postFixScanFromRightToLeft(inFixExpression);
        String postFix1 = new String(postFixExpression1);
        System.out.println(postFix1);
        
        char[] pretFixExpression1 = simpleParser.preFixScanFromRightToLeft(inFixExpression);
        String preFix1 = new String(pretFixExpression1);
        System.out.println(preFix1);
        
        char[] postFixExpression2 = simpleParser.postFixScanFromLeftToRight(inFixExpression);
        String postFix2 = new String(postFixExpression2);
        System.out.println(postFix2);
        
        char[] pretFixExpression2 = simpleParser.preFixScanFromLeftToRight(inFixExpression);
        String preFix2 = new String(pretFixExpression2);
        System.out.println(preFix2);
	}
}