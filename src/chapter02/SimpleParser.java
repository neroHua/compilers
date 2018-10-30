package chapter02;

/**
 * 此类用于将
 * 简单的中缀算术运算符表达式转换成对应的前缀和后缀算术运算表达式
 * 简单的算术表达式应该只包含+，-,0,1,2,3,4,5,6,7,8,9这些字符
 * 
 * 可以从下面的四个转换方式中可以看到
 * 转换的最终结果受语法和转换的方法影响
 * 
 * @author neroHua
 * @time 2018年9月19日09:02:34
 *
 */
public class SimpleParser {

	/**
	 * productions:
	 * 	expression---->	digit |
	 * 			expression + digit |
	 * 			expression - digit
	 * 	digit---->	0 |
	 * 			1 |
	 * 			2 |
	 * 			3 |
	 * 			4 |
	 * 			5 |
	 * 			6 |
	 * 			7 |
	 * 			8 |
	 * 			9
	 * 
	 * translate schemes:
	 * 	expression---->	expression + digit {'expression digit +'} |
	 * 			expression - digit {'expression digit -'}
	 * 	digit---->	0 {'0'} |
	 * 			1 {'1'} |
	 * 			2 {'2'} |
	 * 			3 {'3'} |
	 * 			4 {'4'} |
	 * 			5 {'5'} |
	 * 			6 {'6'} |
	 * 			7 {'7'} |
	 * 			8 {'8'} |
	 * 			9 {'9'}
	 * 
	 * @param expression
	 * @return
	 */
	public char[] postFixScanFromRightToLeft(char[] inFixExpression) {
		char[] postExpression = null;

		postExpression = subPostFixScanFromRightToLeft(inFixExpression, inFixExpression.length - 1);

		return postExpression;
	}

	private char[] subPostFixScanFromRightToLeft(char[] inFixExpression, int currentCharIndex) {
		if(currentCharIndex < 0) {
			return null;
		}
		
		if (matchExpression01(inFixExpression, currentCharIndex)) {
			char[] part1 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 2);
			char part2 = inFixExpression[currentCharIndex - 1];
			char[] part3 = doMatchDigit01(inFixExpression, currentCharIndex);
			
			return doMatchExpression01(part1, part2, part3);
		}

		if (matchDigit01(inFixExpression, currentCharIndex)) {
			return doMatchDigit01(inFixExpression, currentCharIndex);
		}

		throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组");
	}

	private boolean matchExpression01(char[] inFixExpression, int currentCharIndex) {
		if (currentCharIndex <= 0) {
			return false;
		}

		if(!matchDigit01(inFixExpression, currentCharIndex)) {
			return false;
		}
		
		if (inFixExpression[currentCharIndex - 1] == '+' || inFixExpression[currentCharIndex - 1] == '-') {
			return true;
		}

		return false;
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
	 * 	expression---->	digit |
	 * 			digit + expression |
	 * 			digit - expression
	 * 	digit---->	0 |
	 * 			1 |
	 * 			2 |
	 * 			3 |
	 * 			4 |
	 * 			5 |
	 * 			6 |
	 * 			7 |
	 * 			8 |
	 * 			9
	 * 
	 * translate schemes:
	 * 	expression---->	digit + expression {'+ digit expression'} |
	 * 			digit - expression {'- digit expression'}
	 * 	digit---->	0 {'0'} |
	 * 			1 {'1'} |
	 * 			2 {'2'} |
	 * 			3 {'3'} |
	 * 			4 {'4'} |
	 * 			5 {'5'} |
	 * 			6 {'6'} |
	 * 			7 {'7'} |
	 * 			8 {'8'} |
	 * 			9 {'9'}
	 * 
	 * @param expression
	 * @return
	 */
	public char[] preFixScanFromLeftToRight(char[] inFixExpression) {
		return subPreFixScanFromLeftToRight(inFixExpression, 0);
	}
	
	private char[] subPreFixScanFromLeftToRight(char[] inFixExpression, int currentCharIndex) {
		if(currentCharIndex >= inFixExpression.length) {
			return null;
		}
		
		if(matchExpression02(inFixExpression, currentCharIndex)) {
			char[] part1 = doMatchDigit02(inFixExpression, currentCharIndex);
			char part2 = inFixExpression[currentCharIndex + 1];
			char[] part3 = subPreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 2);
			
			return doMatchExpression02(part1, part2, part3);
		}
		
		if(matchDigit02(inFixExpression, currentCharIndex)) {
			
			return doMatchDigit02(inFixExpression, currentCharIndex);
		}
		
		return null;
	}

	private boolean matchExpression02(char[] inFixExpression, int currentCharIndex) {
		if (currentCharIndex < 0 || currentCharIndex >= inFixExpression.length - 2) {
			return false;
		}

		if(!matchDigit02(inFixExpression, currentCharIndex)) {
			return false;
		}
		
		if (inFixExpression[currentCharIndex + 1] == '+' || inFixExpression[currentCharIndex + 1] == '-') {
			return true;
		}

		return false;
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
	 * 	expression---->	digit |
	 * 			digit + expression |
	 * 			digit - expression
	 * 	digit---->	0 |
	 * 			1 |
	 * 			2 |
	 * 			3 |
	 * 			4 |
	 * 			5 |
	 * 			6 |
	 * 			7 |
	 * 			8 |
	 * 			9
	 * 
	 * translate schemes:
	 * 	expression---->	digit + expression {'digit expression +'} |
	 * 			digit - expression {'digit expression -'}
	 * 	digit---->	0 {'0'} |
	 * 			1 {'1'} |
	 * 			2 {'2'} |
	 * 			3 {'3'} |
	 * 			4 {'4'} |
	 * 			5 {'5'} |
	 * 			6 {'6'} |
	 * 			7 {'7'} |
	 * 			8 {'8'} |
	 * 			9 {'9'}
	 * 
	 * @param expression
	 * @return
	 */
	public char[] postFixScanFromLeftToRight(char[] inFixExpression) {
		return subPostFixScanFromLeftToRight(inFixExpression, 0);
	}
	
	private char[] subPostFixScanFromLeftToRight(char[] inFixExpression, int currentCharIndex) {
		if(currentCharIndex >= inFixExpression.length) {
			return null;
		}
		
		if(matchExpression03(inFixExpression, currentCharIndex)) {
			char[] part1 = doMatchDigit03(inFixExpression, currentCharIndex);
			char part2 = inFixExpression[currentCharIndex + 1];
			char[] part3 = subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 2);
			
			return doMatchExpression03(part1, part2, part3);
		}
		
		if(matchDigit03(inFixExpression, currentCharIndex)) {
			
			return doMatchDigit03(inFixExpression, currentCharIndex);
		}
		
		return null;
	}

	private boolean matchExpression03(char[] inFixExpression, int currentCharIndex) {
		if (currentCharIndex < 0 || currentCharIndex >= inFixExpression.length - 2) {
			return false;
		}

		if(!matchDigit03(inFixExpression, currentCharIndex)) {
			return false;
		}
		
		if (inFixExpression[currentCharIndex + 1] == '+' || inFixExpression[currentCharIndex + 1] == '-') {
			return true;
		}

		return false;
	}

	private char[] doMatchExpression03(char[] part1, char part2, char[] part3) {
		char[] part = new char[part1.length + 1 + part3.length];
		
		for(int i = 0, j = 0; j < part1.length; i++, j++){
			part[i] = part1[j];
		}
		for(int i = part1.length, j = 0; j < part3.length; i++, j++ ) {
			part[i] = part3[j];
		}
		part[part1.length + part3.length] = part2;
		
		return part;
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
	 * 	expression---->	digit |
	 * 			expression + digit |
	 * 			expression - digit
	 * 	digit---->	0 |
	 * 			1 |
	 * 			2 |
	 * 			3 |
	 * 			4 |
	 * 			5 |
	 * 			6 |
	 * 			7 |
	 * 			8 |
	 * 			9
	 * 
	 * translate schemes:
	 * 	expression---->	expression + digit {'+ expression digit'} |
	 * 			expression - digit {'- expression digit'}
	 * 	digit---->	0 {'0'} |
	 * 			1 {'1'} |
	 * 			2 {'2'} |
	 * 			3 {'3'} |
	 * 			4 {'4'} |
	 * 			5 {'5'} |
	 * 			6 {'6'} |
	 * 			7 {'7'} |
	 * 			8 {'8'} |
	 * 			9 {'9'}
	 * 
	 * @param expression
	 * @return
	 */
	public char[] preFixScanFromRightToLeft(char[] inFixExpression) {
		char[] postExpression = null;

		postExpression = subPreFixScanFromRightToLeft(inFixExpression, inFixExpression.length - 1);

		return postExpression;
	}

	private char[] subPreFixScanFromRightToLeft(char[] inFixExpression, int currentCharIndex) {
		if(currentCharIndex < 0) {
			return null;
		}
		
		if (matchExpression04(inFixExpression, currentCharIndex)) {
			char[] part1 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 2);
			char part2 = inFixExpression[currentCharIndex - 1];
			char[] part3 = doMatchDigit04(inFixExpression, currentCharIndex);
			
			return doMatchExpression04(part1, part2, part3);
		}

		if (matchDigit04(inFixExpression, currentCharIndex)) {
			return doMatchDigit04(inFixExpression, currentCharIndex);
		}

		throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组");
	}

	private boolean matchExpression04(char[] inFixExpression, int currentCharIndex) {
		if (currentCharIndex <= 0) {
			return false;
		}

		if(!matchDigit04(inFixExpression, currentCharIndex)) {
			return false;
		}
		
		if (inFixExpression[currentCharIndex - 1] == '+' || inFixExpression[currentCharIndex - 1] == '-') {
			return true;
		}

		return false;
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
		SimpleParser simpleParser = new SimpleParser();
		char[] inFixExpression = "9-5+2".toCharArray();
		
		char[] postFixExpression1 = simpleParser.postFixScanFromRightToLeft(inFixExpression);
		String postFix1 = new String(postFixExpression1);
		System.out.println(postFix1);
		
		char[] preFixExpression1 = simpleParser.preFixScanFromLeftToRight(inFixExpression);
		String preFix1 = new String(preFixExpression1);
		System.out.println(preFix1);
		
		char[] postFixExpression2 = simpleParser.postFixScanFromLeftToRight(inFixExpression);
		String postFix2 = new String(postFixExpression2);
		System.out.println(postFix2);
		
		char[] preFixExpression2 = simpleParser.preFixScanFromRightToLeft(inFixExpression);
		String preFix2 = new String(preFixExpression2);
		System.out.println(preFix2);
	}
}
