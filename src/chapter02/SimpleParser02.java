package chapter02;

/**
 * 此类用于将
 * 简单的中缀算术运算符表达式转换成对应的前缀和后缀算术运算表达式
 * 简单的算术表达式应该只包含+，-,0,1,2,3,4,5,6,7,8,9这些字符
 * 这里扩展支持()这两个字符
 * 这里扩展支持* \ % 这三个字符
 * 
 * 
 * @author neroHua
 * @time 2018年11月1日19:12:27
 *
 */
public class SimpleParser02 {

	/**
	 * productions:
	 * 	expression: factory |
	 *              expression + factory |
	 *              expression - factory
	 *              
	 *  factory:    term |
     *              factory * term |
     *              factory \ term |
     *              factory % term
	 *              
	 *  term:       (digit) |
	 *              (expression) |
	 *              (factory)
	 *              digit
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
	 * 	expression: factory {'factory'} |
     *              expression + factory {'expression factory +'} |
     *              expression - factory {'expression factory -'} |
	 *              
     *  factory:    term {'term'} |
     *              factory * term {'factory term *'}  |
     *              factory \ term {'factory term \'}  |
     *              factory % term {'factory term %'}
     *              
	 *  term:       (digit) {'digit'} |
	 *              (expression) {'expression'} |
	 *              (factory) {'factory'} |
	 *              digit
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
		
		int matchDigitMessage = matchDigit01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
		if(matchDigitMessage != 0) {
		    return doMatchDigit01(inFixExpression, currentCharIndex);
		}

		int matchTermMessage = matchTermMessage01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex); 
		switch (matchTermMessage) {
            case 1:
                return doMatchDigit01(inFixExpression, currentCharIndex);
            case 2:
                return subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
            default:
                break;
        }
		
		int[] matchFactoryMessage = matchFactoryMessage01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
		if(matchFactoryMessage[0] != 0) {
		    switch (matchFactoryMessage[0]) {
                case 1:
                    return doMatchDigit01(inFixExpression, currentCharIndex);
                case 2:
                    return subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
                case 3:
                case 4:
                case 5:
                    char[] part11 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 2, startCharIndex, endCharIndex - 2);
                    char part12 = inFixExpression[currentCharIndex - 1];
                    char part13 = inFixExpression[currentCharIndex];
                    return doMatchFactory01(part11, part12, part13);
                case 6:
                case 7:
                case 8:
                    char[] part21 = subPostFixScanFromRightToLeft(inFixExpression, matchFactoryMessage[1] - 1, startCharIndex, matchFactoryMessage[1] - 1);
                    char part22 = inFixExpression[matchFactoryMessage[1]];
                    char[] part23 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex, matchFactoryMessage[1] + 1, endCharIndex);
                    return doMatchFactory01(part21, part22, part23);
                default:
                    break;
            }
		}
		
		int[] matchExpressionMessage = matchExpressionMessage01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
		if(matchExpressionMessage[0] != 0) {
		    switch (matchExpressionMessage[0]) {
                case 1:
                    return doMatchDigit01(inFixExpression, currentCharIndex);

                case 2:
                case 3:
                case 4:
                    char[] part11 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 2, startCharIndex, endCharIndex - 2);
                    char part12 = inFixExpression[currentCharIndex - 1];
                    char part13 = inFixExpression[currentCharIndex];
                    return doMatchFactory01(part11, part12, part13);
                case 5:
                case 6:
                case 7:
                    char[] part21 = subPostFixScanFromRightToLeft(inFixExpression, matchExpressionMessage[1] - 1, startCharIndex, matchExpressionMessage[1] - 1);
                    char part22 = inFixExpression[matchExpressionMessage[1]];
                    char[] part23 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex, matchExpressionMessage[1] + 1, endCharIndex);
                    return doMatchFactory01(part21, part22, part23);
                default:
                    break;
		    }
		}
		
		throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组:\tcurrentIndex:" + currentCharIndex + "\tstartIndex:" + startCharIndex + "\tendIndex:" + endCharIndex);
	}

    private int[] matchExpressionMessage01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            if(startCharIndex == endCharIndex) {
                return packMatchExpressionMessage01(1, 0);
            }
            
            if(inFixExpression[currentCharIndex - 1] == '+') {
                return packMatchExpressionMessage01(2, currentCharIndex - 1);
            }
            if(inFixExpression[currentCharIndex - 1] == '-') {
                return packMatchExpressionMessage01(3, currentCharIndex - 1);
            }
        }
        
        if(inFixExpression[currentCharIndex] >= ')') {
            int matchParenthese = 1;
            for( int i = currentCharIndex - 1; i >= startCharIndex; i--) {
                if(inFixExpression[i] == '(') {
                    matchParenthese--;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese++;
                }
                
                if(matchParenthese == 0) {
                    if(i == startCharIndex) {
                        return packMatchFactoryMessage01(4, 0);
                    }
                    if(inFixExpression[i - 1] == '+') {
                        return packMatchFactoryMessage01(5, i - 1);
                    }
                    if(inFixExpression[i - 1] == '-') {
                        return packMatchFactoryMessage01(6, i - 1);
                    }
                }
            }
        }
        
        int matchParenthese = 0;
        int matchExpression = 0;
        for(int i = currentCharIndex; i >= startCharIndex; i--) {
            if (inFixExpression[i] == '(') {
                matchParenthese--;
            } else if (inFixExpression[i] == ')') {
                matchParenthese++;
            } else if (inFixExpression[i] == '+' || inFixExpression[i] == '-') {
                matchExpression++;
            }
            
            if (matchParenthese == 0 && matchExpression > 0) {
                return packMatchFactoryMessage01(7, i - 1);
            }
        }
        
        return packMatchExpressionMessage01(0, 0);
    }

    private int[] packMatchExpressionMessage01(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
        int[] matchExpressionMessage = new int[2];
        matchExpressionMessage[0] = matchFactoryType;
        matchExpressionMessage[1] = multiplyOrDivideOrModulusIndex;
        
        return matchExpressionMessage;
    }
    
    private int matchDigit01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(endCharIndex != startCharIndex) {
            return 0;
        }
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            return 1;
        }
        return 0;
    }

    private char[] doMatchDigit01(char[] inFixExpression, int currentCharIndex) {
        char[] returnChar = new char[1];
        returnChar[0] = inFixExpression[currentCharIndex];
        
        return returnChar;
    }
    
    private int matchTermMessage01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(startCharIndex == endCharIndex && inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            return 1;
        }
        
        if(inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
            int matchParenthese = 1;
            for( int i = currentCharIndex - 1; i >= startCharIndex; i--) {
                if(inFixExpression[i] == '(') {
                    matchParenthese--;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese++;
                }
                
                if(matchParenthese == 0) {
                    if(i == startCharIndex) {
                        return 2;
                    }
                    break;
                }
            }
        }
        
        return 0;
    }
    
    private int[] matchFactoryMessage01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if(inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            if(startCharIndex == endCharIndex) {
                return packMatchFactoryMessage01(1, 0);
            }
            
            int[] matchFactoryMessage = matchFactoryMessage01(inFixExpression, currentCharIndex - 2, startCharIndex, endCharIndex - 2);
            if(matchFactoryMessage[0] == 0) {
                return packMatchFactoryMessage01(0, 0);
            }
            
            if(inFixExpression[currentCharIndex - 1] == '*') {
                return packMatchFactoryMessage01(3, currentCharIndex - 1);
            }
            if(inFixExpression[currentCharIndex - 1] == '/') {
                return packMatchFactoryMessage01(4, currentCharIndex - 1);
            }
            if(inFixExpression[currentCharIndex - 1] == '%') {
                return packMatchFactoryMessage01(5, currentCharIndex - 1);
            }
        }
        
        if(inFixExpression[currentCharIndex] == ')') {
            int matchParenthese = 1;
            for( int i = currentCharIndex - 1; i >= startCharIndex; i--) {
                if(inFixExpression[i] == '(') {
                    matchParenthese--;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese++;
                }
                
                if(matchParenthese == 0) {
                    if(i == startCharIndex) {
                        return packMatchFactoryMessage01(2,0);
                    }
                    int[] matchFactorymessage = matchFactoryMessage01(inFixExpression, i - 2, startCharIndex, i - 2);
                    if(matchFactorymessage[0] == 0) {
                        return packMatchFactoryMessage01(0, 0);
                    }
                    
                    if(inFixExpression[i - 1] == '*') {
                        return packMatchFactoryMessage01(6, i - 1);
                    }
                    if(inFixExpression[i - 1] == '/') {
                        return packMatchFactoryMessage01(7, i - 1);
                    }
                    if(inFixExpression[i - 1] == '%') {
                        return packMatchFactoryMessage01(8, i - 1);
                    }
                    break;
                }
            }
        }
        
        return packMatchFactoryMessage01(0, 0);
    }
    
    private int[] packMatchFactoryMessage01(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
        int[] matchFactoryMessage = new int[2];
        matchFactoryMessage[0] = matchFactoryType;
        matchFactoryMessage[1] = multiplyOrDivideOrModulusIndex;
        
        return matchFactoryMessage;
    }
    
    private char[] doMatchFactory01(char[] part1, char part2, char part3) {
        char[] part = new char[part1.length + 1 + 1];

        for (int i = 0, j = 0; j < part1.length; i++, j++) {
            part[i] = part1[j];
        }
        part[part.length - 2] = part3;
        part[part.length - 1] = part2;

        return part;
    }

    private char[] doMatchFactory01(char[] part1, char part2, char[] part3) {
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

    public static void main(String[] args) {
		SimpleParser02 simpleParser = new SimpleParser02();
		char[] inFixExpression = "(1+(9-5)*(2))".toCharArray();
		
        char[] postFixExpression1 = simpleParser.postFixScanFromRightToLeft(inFixExpression);
        String postFix1 = new String(postFixExpression1);
        System.out.println(postFix1);
        
        /*char[] pretFixExpression1 = simpleParser.preFixScanFromRightToLeft(inFixExpression);
        String preFix1 = new String(pretFixExpression1);
        System.out.println(preFix1);
        
        char[] postFixExpression2 = simpleParser.postFixScanFromLeftToRight(inFixExpression);
        String postFix2 = new String(postFixExpression2);
        System.out.println(postFix2);
        
        char[] pretFixExpression2 = simpleParser.preFixScanFromLeftToRight(inFixExpression);
        String preFix2 = new String(pretFixExpression2);
        System.out.println(preFix2);*/
	}
}