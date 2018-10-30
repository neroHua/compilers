package chapter02;

/**
 * 此类用于将
 * 简单的中缀算术运算符表达式转换成对应的前缀和后缀算术运算表达式
 * 简单的算术表达式应该只包含+，-,0,1,2,3,4,5,6,7,8,9这些字符
 * 这里扩展支持()这两个字符
 * 
 * 可以从下面的四个转换方式中可以看到
 * 转换的最终结果受语法和转换的方法影响
 * 
 * @author neroHua
 * @time 2018年10月4日22:18:28
 *
 */
public class SimpleParserWithParenthese {

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
	 * 	expression: digit {'expression digit +'} |
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
		char[] postExpression = null;

		postExpression = subPostFixScanFromRightToLeft(inFixExpression, inFixExpression.length - 1, 0, inFixExpression.length - 1);

		return postExpression;
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
		if(inFixExpression[currentCharIndex] == ')' && startCharIndex <= endCharIndex - 4) {
		    int matchParenthese = 1;
		    for( int i = currentCharIndex - 1; i >= startCharIndex + 2; i--) {
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
		        }
		    }
        }
		return packMatchExpression01Message(0, 0);
	}
	
	private int[] packMatchExpression01Message(int matchExpressionType, int plusOrMinuxIndex) {
	    int[] returnMatchExpressionMessage = new int[2];
	    returnMatchExpressionMessage[0] = matchExpressionType;
	    returnMatchExpressionMessage[1] = plusOrMinuxIndex;
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
	
	public static void main(String[] args) {
		SimpleParserWithParenthese simpleParser = new SimpleParserWithParenthese();
		char[] inFixExpression = "(9-5)+(2)".toCharArray();
		
        char[] postFixExpression1 = simpleParser.postFixScanFromRightToLeft(inFixExpression);
        String postFix1 = new String(postFixExpression1);
        System.out.println(postFix1);
	}
}