package main.chapter02.part01;

/**
 * 此类用于将
 * 简单的中缀算术运算符表达式转换成对应的前缀和后缀算术运算表达式
 * 简单的算术表达式应该只包含+，-,0,1,2,3,4,5,6,7,8,9这些字符
 * 这里扩展支持()这两个字符
 * 这里扩展支持* \ % 这三个字符
 * 这里扩展支持多个一位数字组成一个多位数字(number)
 * 
 * 作者的话：
 * 这里发现之前的代码有些bug，但是我将不会去修复之前的那些代码
 * 从这一章节开始我将开始使用单元测试来尽量保证我的代码可靠性
 * 
 * @author neroHua
 * @time 2018年12月17日14:58:43
 *
 */
public class SimpleParser03 {

    /**
     * productions:
     *  expression: factory |
     *              expression + factory |
     *              expression - factory
     *              
     *  factory:    term |
     *              factory * term |
     *              factory \ term |
     *              factory % term
     *              
     *  term:       (number) |
     *              (expression) |
     *              (factory)
     *              number
     *              
     *  number:     digits
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
     *  expression: factory {'factory'} |
     *              expression + factory {'expression factory +'} |
     *              expression - factory {'expression factory -'} |
     *              
     *  factory:    term {'term'} |
     *              factory * term {'factory term *'}  |
     *              factory \ term {'factory term \'}  |
     *              factory % term {'factory term %'}
     *              
     *  term:       (number) {'number'} |
     *              (expression) {'expression'} |
     *              (factory) {'factory'} |
     *              number {'number'}
     *              
     *  number      digits {'digits'}
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
    public char[] postFixScanFromRightToLeft(char[] inFixExpression) {
        return subPostFixScanFromRightToLeft(inFixExpression, inFixExpression.length - 1, 0, inFixExpression.length - 1);
    }

    private char[] subPostFixScanFromRightToLeft(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if (currentCharIndex < 0) {
            return null;
        }

        int matchNumberMessage = matchNumber01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        if (matchNumberMessage != 0) {
            return doMatchNumber01(inFixExpression, startCharIndex, endCharIndex);
        }
        
        int matchTermMessage = matchTermMessage01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        switch (matchTermMessage) {
            case 1:
                return doMatchNumber01(inFixExpression, startCharIndex, endCharIndex);
            case 2:
                return subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
            default:
                break;
        }

        int[] matchFactoryMessage = matchFactoryMessage01(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
        if (matchFactoryMessage[0] != 0) {
            switch (matchFactoryMessage[0]) {
                case 1:
                    return doMatchNumber01(inFixExpression, startCharIndex, endCharIndex);
                case 2:
                    return subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
                case 3:
                case 4:
                case 5:
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
        if (matchExpressionMessage[0] != 0) {
            switch (matchExpressionMessage[0]) {
                case 1:
                    return doMatchNumber01(inFixExpression, startCharIndex, endCharIndex);
                case 2:
                    return subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    char[] part21 = subPostFixScanFromRightToLeft(inFixExpression, matchExpressionMessage[1] - 1, startCharIndex, matchExpressionMessage[1] - 1);
                    char part22 = inFixExpression[matchExpressionMessage[1]];
                    char[] part23 = subPostFixScanFromRightToLeft(inFixExpression, currentCharIndex, matchExpressionMessage[1] + 1, endCharIndex);
                    return doMatchExpress01(part21, part22, part23);
                default:
                    break;
                }
        }

        throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组:\tcurrentIndex:" + currentCharIndex + "\tstartIndex:" + startCharIndex + "\tendIndex:" + endCharIndex);
    }

    private int matchNumber01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        for (int i = endCharIndex; i >= startCharIndex; i--) {
            if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                return 0;
            }
        }
        
        return 1;
    }

    private char[] doMatchNumber01(char[] inFixExpression, int startCharIndex, int endCharIndex) {
        char[] returnChar = new char[endCharIndex - startCharIndex + 1];
        
        for (int i = 0; i < returnChar.length; i++) {
            returnChar[i] = inFixExpression[startCharIndex + i];
        }

        return returnChar;
    }
    
    private int matchTermMessage01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        for (int i = endCharIndex; i >= startCharIndex; i--) {
            if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                break;
            }
            
            if (i == startCharIndex) {
                return 1;
            }
        }

        if (inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
            int matchParenthese = 1;
            for (int i = currentCharIndex - 1; i >= startCharIndex; i--) {
                if (inFixExpression[i] == '(') {
                    matchParenthese--;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese++;
                }

                if (matchParenthese == 0) {
                    if (i == startCharIndex) {
                        return 2;
                    }
                    break;
                }
            }
        }

        return 0;
    }

    private int[] matchFactoryMessage01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            
            int i = endCharIndex;
            for (; i >= startCharIndex; i--) {
                if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                    break;
                }
                
                if (i == startCharIndex) {
                    return packMatchFactoryMessage01(1, 0);
                }
            }

            int[] matchFactoryMessage = matchFactoryMessage01(inFixExpression, i - 1, startCharIndex, i - 1);
            if (matchFactoryMessage[0] == 0) {
                return packMatchFactoryMessage01(0, 0);
            }

            if (inFixExpression[i] == '*') {
                return packMatchFactoryMessage01(3, i);
            }
            if (inFixExpression[i] == '/') {
                return packMatchFactoryMessage01(4, i);
            }
            if (inFixExpression[i] == '%') {
                return packMatchFactoryMessage01(5, i);
            }
        }

        if (inFixExpression[currentCharIndex] == ')') {
            int matchParenthese = 1;
            for (int i = currentCharIndex - 1; i >= startCharIndex; i--) {
                if (inFixExpression[i] == '(') {
                    matchParenthese--;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese++;
                }

                if (matchParenthese == 0) {
                    if (i == startCharIndex) {
                        return packMatchFactoryMessage01(2, 0);
                    }
                    int[] matchFactorymessage = matchFactoryMessage01(inFixExpression, i - 2, startCharIndex, i - 2);
                    if (matchFactorymessage[0] == 0) {
                        return packMatchFactoryMessage01(0, 0);
                    }

                    if (inFixExpression[i - 1] == '*') {
                        return packMatchFactoryMessage01(6, i - 1);
                    }
                    if (inFixExpression[i - 1] == '/') {
                        return packMatchFactoryMessage01(7, i - 1);
                    }
                    if (inFixExpression[i - 1] == '%') {
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

    private char[] doMatchFactory01(char[] part1, char part2, char[] part3) {
        char[] part = new char[part1.length + 1 + part3.length];

        for (int i = 0, j = 0; j < part1.length; i++, j++) {
            part[i] = part1[j];
        }
        for (int i = part1.length, j = 0; j < part3.length; i++, j++) {
            part[i] = part3[j];
        }
        part[part.length - 1] = part2;

        return part;
    }

    private int[] matchExpressionMessage01(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
        if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
            int i = endCharIndex;
            for (; i >= startCharIndex; i--) {
                if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                    break;
                }
                
                if (i == startCharIndex) {
                    return packMatchFactoryMessage01(1, 0);
                }
            }

            if (inFixExpression[i] == '+') {
                return packMatchExpressionMessage01(3, i);
            }
            if (inFixExpression[i] == '-') {
                return packMatchExpressionMessage01(4, i);
            }
        }

        if (inFixExpression[currentCharIndex] >= ')') {
            int matchParenthese = 1;
            for (int i = currentCharIndex - 1; i >= startCharIndex; i--) {
                if (inFixExpression[i] == '(') {
                    matchParenthese--;
                } else if (inFixExpression[i] == ')') {
                    matchParenthese++;
                }

                if (matchParenthese == 0) {
                    if (i == startCharIndex) {
                        return packMatchExpressionMessage01(2, 0);
                    }
                    if (inFixExpression[i - 1] == '+') {
                        return packMatchExpressionMessage01(5, i - 1);
                    }
                    if (inFixExpression[i - 1] == '-') {
                        return packMatchExpressionMessage01(6, i - 1);
                    }
                }
            }
        }

        int matchParenthese = 0;
        int matchExpression = 0;
        for (int i = currentCharIndex; i >= startCharIndex; i--) {
            if (inFixExpression[i] == '(') {
                matchParenthese--;
            } else if (inFixExpression[i] == ')') {
                matchParenthese++;
            } else if (inFixExpression[i] == '+' || inFixExpression[i] == '-') {
                matchExpression++;
            }

            if (matchParenthese == 0 && matchExpression > 0) {
                return packMatchExpressionMessage01(7, i);
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
    
    private char[] doMatchExpress01(char[] part1, char part2, char[] part3) {
        char[] part = new char[part1.length + 1 + part3.length];

        for (int i = 0, j = 0; j < part1.length; i++, j++) {
            part[i] = part1[j];
        }
        for (int i = part1.length, j = 0; j < part3.length; i++, j++) {
            part[i] = part3[j];
        }
        part[part.length - 1] = part2;

        return part;
    }

    /**
    * productions:
    *  expression: factory |
    *              expression + factory |
    *              expression - factory
    *              
    *  factory:    term |
    *              factory * term |
    *              factory \ term |
    *              factory % term
    *              
    *  term:       (number) |
    *              (expression) |
    *              (factory)
    *              number
    *              
    *  number:     digits
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
    *  expression: factory {'factory'} |
    *              expression + factory {'+ expression factory'} |
    *              expression - factory {'- expression factory'} |
    *              
    *  factory:    term {'term'} |
    *              factory * term {'* factory term'}  |
    *              factory \ term {'\ factory term'}  |
    *              factory % term {'% factory term'}
    *              
    *  term:       (number) {'number'} |
    *              (expression) {'expression'} |
    *              (factory) {'factory'} |
    *              number {'number'}
    *              
    *  number      digits {'digits'}
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

   private char[] subPreFixScanFromRightToLeft(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (currentCharIndex < 0) {
           return null;
       }

       int matchNumberMessage = matchNumber02(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchNumberMessage != 0) {
           return doMatchNumber02(inFixExpression, startCharIndex, endCharIndex);
       }
       
       int matchTermMessage = matchTermMessage02(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       switch (matchTermMessage) {
           case 1:
               return doMatchNumber02(inFixExpression, startCharIndex, endCharIndex);
           case 2:
               return subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
           default:
               break;
       }

       int[] matchFactoryMessage = matchFactoryMessage02(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchFactoryMessage[0] != 0) {
           switch (matchFactoryMessage[0]) {
               case 1:
                   return doMatchNumber02(inFixExpression, startCharIndex, endCharIndex);
               case 2:
                   return subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
                   char[] part21 = subPreFixScanFromRightToLeft(inFixExpression, matchFactoryMessage[1] - 1, startCharIndex, matchFactoryMessage[1] - 1);
                   char part22 = inFixExpression[matchFactoryMessage[1]];
                   char[] part23 = subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex, matchFactoryMessage[1] + 1, endCharIndex);
                   return doMatchFactory02(part21, part22, part23);
               default:
                   break;
           }
       }

       int[] matchExpressionMessage = matchExpressionMessage02(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchExpressionMessage[0] != 0) {
           switch (matchExpressionMessage[0]) {
               case 1:
                   return doMatchNumber02(inFixExpression, startCharIndex, endCharIndex);
               case 2:
                   return subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex - 1, startCharIndex + 1, endCharIndex - 1);
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
                   char[] part21 = subPreFixScanFromRightToLeft(inFixExpression, matchExpressionMessage[1] - 1, startCharIndex, matchExpressionMessage[1] - 1);
                   char part22 = inFixExpression[matchExpressionMessage[1]];
                   char[] part23 = subPreFixScanFromRightToLeft(inFixExpression, currentCharIndex, matchExpressionMessage[1] + 1, endCharIndex);
                   return doMatchExpress02(part21, part22, part23);
               default:
                   break;
               }
       }

       throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组:\tcurrentIndex:" + currentCharIndex + "\tstartIndex:" + startCharIndex + "\tendIndex:" + endCharIndex);
   }

   private int matchNumber02(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       for (int i = endCharIndex; i >= startCharIndex; i--) {
           if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
               return 0;
           }
       }
       
       return 1;
   }

   private char[] doMatchNumber02(char[] inFixExpression, int startCharIndex, int endCharIndex) {
       char[] returnChar = new char[endCharIndex - startCharIndex + 1];
       
       for (int i = 0; i < returnChar.length; i++) {
           returnChar[i] = inFixExpression[startCharIndex + i];
       }

       return returnChar;
   }
   
   private int matchTermMessage02(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       for (int i = endCharIndex; i >= startCharIndex; i--) {
           if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
               break;
           }
           
           if (i == startCharIndex) {
               return 1;
           }
       }

       if (inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
           int matchParenthese = 1;
           for (int i = currentCharIndex - 1; i >= startCharIndex; i--) {
               if (inFixExpression[i] == '(') {
                   matchParenthese--;
               } else if (inFixExpression[i] == ')') {
                   matchParenthese++;
               }

               if (matchParenthese == 0) {
                   if (i == startCharIndex) {
                       return 2;
                   }
                   break;
               }
           }
       }

       return 0;
   }

   private int[] matchFactoryMessage02(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
           
           int i = endCharIndex;
           for (; i >= startCharIndex; i--) {
               if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                   break;
               }
               
               if (i == startCharIndex) {
                   return packMatchFactoryMessage02(1, 0);
               }
           }

           int[] matchFactoryMessage = matchFactoryMessage02(inFixExpression, i - 1, startCharIndex, i - 1);
           if (matchFactoryMessage[0] == 0) {
               return packMatchFactoryMessage02(0, 0);
           }

           if (inFixExpression[i] == '*') {
               return packMatchFactoryMessage02(3, i);
           }
           if (inFixExpression[i] == '/') {
               return packMatchFactoryMessage02(4, i);
           }
           if (inFixExpression[i] == '%') {
               return packMatchFactoryMessage02(5, i);
           }
       }

       if (inFixExpression[currentCharIndex] == ')') {
           int matchParenthese = 1;
           for (int i = currentCharIndex - 1; i >= startCharIndex; i--) {
               if (inFixExpression[i] == '(') {
                   matchParenthese--;
               } else if (inFixExpression[i] == ')') {
                   matchParenthese++;
               }

               if (matchParenthese == 0) {
                   if (i == startCharIndex) {
                       return packMatchFactoryMessage02(2, 0);
                   }
                   int[] matchFactorymessage = matchFactoryMessage02(inFixExpression, i - 2, startCharIndex, i - 2);
                   if (matchFactorymessage[0] == 0) {
                       return packMatchFactoryMessage02(0, 0);
                   }

                   if (inFixExpression[i - 1] == '*') {
                       return packMatchFactoryMessage02(6, i - 1);
                   }
                   if (inFixExpression[i - 1] == '/') {
                       return packMatchFactoryMessage02(7, i - 1);
                   }
                   if (inFixExpression[i - 1] == '%') {
                       return packMatchFactoryMessage02(8, i - 1);
                   }
                   break;
               }
           }
       }

       return packMatchFactoryMessage02(0, 0);
   }

   private int[] packMatchFactoryMessage02(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
       int[] matchFactoryMessage = new int[2];
       matchFactoryMessage[0] = matchFactoryType;
       matchFactoryMessage[1] = multiplyOrDivideOrModulusIndex;

       return matchFactoryMessage;
   }

   private char[] doMatchFactory02(char[] part1, char part2, char[] part3) {
       char[] part = new char[part1.length + 1 + part3.length];

       part[0] = part2;
       for (int i = 1, j = 0; j < part1.length; i++, j++) {
           part[i] = part1[j];
       }
       for (int i = part1.length + 1, j = 0; j < part3.length; i++, j++) {
           part[i] = part3[j];
       }

       return part;
   }

   private int[] matchExpressionMessage02(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
           int i = endCharIndex;
           for (; i >= startCharIndex; i--) {
               if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                   break;
               }
               
               if (i == startCharIndex) {
                   return packMatchFactoryMessage02(1, 0);
               }
           }

           if (inFixExpression[i] == '+') {
               return packMatchExpressionMessage02(3, i);
           }
           if (inFixExpression[i] == '-') {
               return packMatchExpressionMessage02(4, i);
           }
       }

       if (inFixExpression[currentCharIndex] >= ')') {
           int matchParenthese = 1;
           for (int i = currentCharIndex - 1; i >= startCharIndex; i--) {
               if (inFixExpression[i] == '(') {
                   matchParenthese--;
               } else if (inFixExpression[i] == ')') {
                   matchParenthese++;
               }

               if (matchParenthese == 0) {
                   if (i == startCharIndex) {
                       return packMatchExpressionMessage02(2, 0);
                   }
                   if (inFixExpression[i - 1] == '+') {
                       return packMatchExpressionMessage02(5, i - 1);
                   }
                   if (inFixExpression[i - 1] == '-') {
                       return packMatchExpressionMessage02(6, i - 1);
                   }
               }
           }
       }

       int matchParenthese = 0;
       int matchExpression = 0;
       for (int i = currentCharIndex; i >= startCharIndex; i--) {
           if (inFixExpression[i] == '(') {
               matchParenthese--;
           } else if (inFixExpression[i] == ')') {
               matchParenthese++;
           } else if (inFixExpression[i] == '+' || inFixExpression[i] == '-') {
               matchExpression++;
           }

           if (matchParenthese == 0 && matchExpression > 0) {
               return packMatchExpressionMessage02(7, i);
           }
       }

       return packMatchExpressionMessage02(0, 0);
   }
   
   private int[] packMatchExpressionMessage02(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
       int[] matchExpressionMessage = new int[2];
       matchExpressionMessage[0] = matchFactoryType;
       matchExpressionMessage[1] = multiplyOrDivideOrModulusIndex;

       return matchExpressionMessage;
   }
   
   private char[] doMatchExpress02(char[] part1, char part2, char[] part3) {
       char[] part = new char[part1.length + 1 + part3.length];

       part[0] = part2;
       for (int i = 1, j = 0; j < part1.length; i++, j++) {
           part[i] = part1[j];
       }
       for (int i = part1.length + 1, j = 0; j < part3.length; i++, j++) {
           part[i] = part3[j];
       }

       return part;
   }

   /**
    * productions:
    *  expression: factory |
    *              factory + expression |
    *              factory - expression 
    *              
    *  factory:    term |
    *              term * factory |
    *              term \ factory |
    *              term % factory 
    *              
    *  term:       (number) |
    *              (expression) |
    *              (factory)
    *              number
    *              
    *  number:     digits
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
    *  expression: factory {'factory'} |
    *              factory + expression {'factory expression +'} |
    *              factory - expression {'factory expression -'} |
    *              
    *  factory:    term {'term'} |
    *              term * factory {'term factory *'} |
    *              term \ factory {'term factory \'} |
    *              term % factory {'term factory %'}
    *              
    *  term:       (number) {'number'} |
    *              (expression) {'expression'} |
    *              (factory) {'factory'} |
    *              number {'number'}
    *              
    *  number      digits {'digits'}
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

   private char[] subPostFixScanFromLeftToRight(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (currentCharIndex < 0) {
           return null;
       }

       int matchNumberMessage = matchNumber03(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchNumberMessage != 0) {
           return doMatchNumber03(inFixExpression, startCharIndex, endCharIndex);
       }
       
       int matchTermMessage = matchTermMessage03(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       switch (matchTermMessage) {
           case 1:
               return doMatchNumber03(inFixExpression, startCharIndex, endCharIndex);
           case 2:
               return subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
           default:
               break;
       }

       int[] matchFactoryMessage = matchFactoryMessage03(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchFactoryMessage[0] != 0) {
           switch (matchFactoryMessage[0]) {
               case 1:
                   return doMatchNumber03(inFixExpression, startCharIndex, endCharIndex);
               case 2:
                   return subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
                   char[] part21 = subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex, startCharIndex, matchFactoryMessage[1] - 1);
                   char part22 = inFixExpression[matchFactoryMessage[1]];
                   char[] part23 = subPostFixScanFromLeftToRight(inFixExpression, matchFactoryMessage[1] + 1, matchFactoryMessage[1] + 1, endCharIndex);
                   return doMatchFactory03(part21, part22, part23);
               default:
                   break;
           }
       }

       int[] matchExpressionMessage = matchExpressionMessage03(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchExpressionMessage[0] != 0) {
           switch (matchExpressionMessage[0]) {
               case 1:
                   return doMatchNumber03(inFixExpression, startCharIndex, endCharIndex);
               case 2:
                   return subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
                   char[] part21 = subPostFixScanFromLeftToRight(inFixExpression, currentCharIndex, startCharIndex, matchExpressionMessage[1] - 1);
                   char part22 = inFixExpression[matchExpressionMessage[1]];
                   char[] part23 = subPostFixScanFromLeftToRight(inFixExpression, matchExpressionMessage[1] + 1, matchExpressionMessage[1] + 1, endCharIndex);
                   return doMatchExpress03(part21, part22, part23);
               default:
                   break;
               }
       }

       throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组:\tcurrentIndex:" + currentCharIndex + "\tstartIndex:" + startCharIndex + "\tendIndex:" + endCharIndex);
   }

   private int matchNumber03(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       for (int i = currentCharIndex; i <= endCharIndex; i++) {
           if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
               return 0;
           }
       }
       
       return 1;
   }

   private char[] doMatchNumber03(char[] inFixExpression, int startCharIndex, int endCharIndex) {
       char[] returnChar = new char[endCharIndex - startCharIndex + 1];
       
       for (int i = 0; i < returnChar.length; i++) {
           returnChar[i] = inFixExpression[startCharIndex + i];
       }

       return returnChar;
   }
   
   private int matchTermMessage03(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       for (int i = currentCharIndex; i <= endCharIndex; i++) {
           if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
               break;
           }
           
           if (i == endCharIndex) {
               return 1;
           }
       }

       if (inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
           int matchParenthese = 1;
           for (int i = currentCharIndex + 1; i <= endCharIndex; i++) {
               if (inFixExpression[i] == ')') {
                   matchParenthese--;
               } else if (inFixExpression[i] == '(') {
                   matchParenthese++;
               }
   
               if (matchParenthese == 0) {
                   if (i == endCharIndex) {
                       return 2;
                   }
                   break;
               }
           }
       }

       return 0;
   }

   private int[] matchFactoryMessage03(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
           
           int i = startCharIndex;
           for (; i <= endCharIndex; i++) {
               if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                   break;
               }
               
               if (i == endCharIndex) {
                   return packMatchFactoryMessage03(1, 0);
               }
           }

           int[] matchFactoryMessage = matchFactoryMessage03(inFixExpression, i + 1, i + 1, endCharIndex);
           if (matchFactoryMessage[0] == 0) {
               return packMatchFactoryMessage03(0, 0);
           }

           if (inFixExpression[i] == '*') {
               return packMatchFactoryMessage03(3, i);
           }
           if (inFixExpression[i] == '/') {
               return packMatchFactoryMessage03(4, i);
           }
           if (inFixExpression[i] == '%') {
               return packMatchFactoryMessage03(5, i);
           }
       }

       if (inFixExpression[currentCharIndex] == '(') {
           int matchParenthese = 1;
           for (int i = currentCharIndex + 1; i <= endCharIndex; i++) {
               if (inFixExpression[i] == ')') {
                   matchParenthese--;
               } else if (inFixExpression[i] == '(') {
                   matchParenthese++;
               }

               if (matchParenthese == 0) {
                   if (i == endCharIndex) {
                       return packMatchFactoryMessage03(2, 0);
                   }
                   int[] matchFactorymessage = matchFactoryMessage03(inFixExpression, i + 2, i + 2, endCharIndex);
                   if (matchFactorymessage[0] == 0) {
                       return packMatchFactoryMessage03(0, 0);
                   }

                   if (inFixExpression[i + 1] == '*') {
                       return packMatchFactoryMessage03(6, i + 1);
                   }
                   if (inFixExpression[i + 1] == '/') {
                       return packMatchFactoryMessage03(7, i + 1);
                   }
                   if (inFixExpression[i + 1] == '%') {
                       return packMatchFactoryMessage03(8, i + 1);
                   }
                   break;
               }
           }
       }

       return packMatchFactoryMessage03(0, 0);
   }

   private int[] packMatchFactoryMessage03(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
       int[] matchFactoryMessage = new int[2];
       matchFactoryMessage[0] = matchFactoryType;
       matchFactoryMessage[1] = multiplyOrDivideOrModulusIndex;

       return matchFactoryMessage;
   }

   private char[] doMatchFactory03(char[] part1, char part2, char[] part3) {
       char[] part = new char[part1.length + 1 + part3.length];

       for (int i = 0, j = 0; j < part1.length; i++, j++) {
           part[i] = part1[j];
       }
       for (int i = part1.length, j = 0; j < part3.length; i++, j++) {
           part[i] = part3[j];
       }
       part[part.length - 1] = part2;

       return part;
   }

   private int[] matchExpressionMessage03(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
           int i = startCharIndex;
           for (; i <= endCharIndex; i++) {
               if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                   break;
               }
               
               if (i == endCharIndex) {
                   return packMatchFactoryMessage03(1, 0);
               }
           }

           if (inFixExpression[i] == '+') {
               return packMatchExpressionMessage03(3, i);
           }
           if (inFixExpression[i] == '-') {
               return packMatchExpressionMessage03(4, i);
           }
       }

       if (inFixExpression[currentCharIndex] == '(') {
           int matchParenthese = 1;
           int matchExpression = 0;
           for (int i = currentCharIndex + 1; i <= endCharIndex; i++) {
               if (inFixExpression[i] == ')') {
                   matchParenthese--;
                   if (matchParenthese == 0) {
                       matchExpression = 0;
                   }
               } else if (inFixExpression[i] == '(') {
                   matchParenthese++;
               } else if (inFixExpression[i] == '+' || inFixExpression[i] == '-') {
                   matchExpression++;
               }

               if (matchParenthese == 0) {
                   if (i == endCharIndex) {
                       return packMatchExpressionMessage03(2, 0);
                   }
                   if (matchExpression > 0) {
                       if (inFixExpression[i] == '+') {
                           return packMatchExpressionMessage03(5, i);
                       }
                       if (inFixExpression[i] == '-') {
                           return packMatchExpressionMessage03(6, i);
                       }
                   }
                   break;
               }
           }
       }

       int matchParenthese = 0;
       int matchExpression = 0;
       for (int i = currentCharIndex; i <= endCharIndex; i++) {
           if (inFixExpression[i] == ')') {
               matchParenthese--;
               if (matchParenthese == 0) {
                   matchExpression = 0;
               }
           } else if (inFixExpression[i] == '(') {
               matchParenthese++;
           } else if (inFixExpression[i] == '+' || inFixExpression[i] == '-') {
               matchExpression++;
           }

           if (matchParenthese == 0 && matchExpression > 0) {
               return packMatchExpressionMessage03(7, i);
           }
       }

       return packMatchExpressionMessage03(0, 0);
   }
   
   private int[] packMatchExpressionMessage03(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
       int[] matchExpressionMessage = new int[2];
       matchExpressionMessage[0] = matchFactoryType;
       matchExpressionMessage[1] = multiplyOrDivideOrModulusIndex;

       return matchExpressionMessage;
   }
   
   private char[] doMatchExpress03(char[] part1, char part2, char[] part3) {
       char[] part = new char[part1.length + 1 + part3.length];

       for (int i = 0, j = 0; j < part1.length; i++, j++) {
           part[i] = part1[j];
       }
       for (int i = part1.length, j = 0; j < part3.length; i++, j++) {
           part[i] = part3[j];
       }
       part[part.length - 1] = part2;

       return part;
   }

   /**
    * productions:
    *  expression: factory |
    *              factory + expression |
    *              factory - expression 
    *              
    *  factory:    term |
    *              term * factory |
    *              term \ factory |
    *              term % factory 
    *              
    *  term:       (number) |
    *              (expression) |
    *              (factory)
    *              number
    *              
    *  number:     digits
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
    *  expression: factory {'factory'} |
    *              factory + expression {'+ factory expression'} |
    *              factory - expression {'- factory expression'} |
    *              
    *  factory:    term {'term'} |
    *              term * factory {'* term factory'} |
    *              term \ factory {'\ term factory'} |
    *              term % factory {'% term factory'}
    *              
    *  term:       (number) {'number'} |
    *              (expression) {'expression'} |
    *              (factory) {'factory'} |
    *              number {'number'}
    *              
    *  number      digits {'digits'}
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
       return subPreFixScanFromLeftToRight(inFixExpression, 0, 0, inFixExpression.length - 1);
   }

   private char[] subPreFixScanFromLeftToRight(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (currentCharIndex < 0) {
           return null;
       }

       int matchNumberMessage = matchNumber04(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchNumberMessage != 0) {
           return doMatchNumber04(inFixExpression, startCharIndex, endCharIndex);
       }
       
       int matchTermMessage = matchTermMessage04(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       switch (matchTermMessage) {
           case 1:
               return doMatchNumber04(inFixExpression, startCharIndex, endCharIndex);
           case 2:
               return subPreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
           default:
               break;
       }

       int[] matchFactoryMessage = matchFactoryMessage04(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchFactoryMessage[0] != 0) {
           switch (matchFactoryMessage[0]) {
               case 1:
                   return doMatchNumber04(inFixExpression, startCharIndex, endCharIndex);
               case 2:
                   return subPreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
                   char[] part21 = subPreFixScanFromLeftToRight(inFixExpression, currentCharIndex, startCharIndex, matchFactoryMessage[1] - 1);
                   char part22 = inFixExpression[matchFactoryMessage[1]];
                   char[] part23 = subPreFixScanFromLeftToRight(inFixExpression, matchFactoryMessage[1] + 1, matchFactoryMessage[1] + 1, endCharIndex);
                   return doMatchFactory04(part21, part22, part23);
               default:
                   break;
           }
       }

       int[] matchExpressionMessage = matchExpressionMessage04(inFixExpression, currentCharIndex, startCharIndex, endCharIndex);
       if (matchExpressionMessage[0] != 0) {
           switch (matchExpressionMessage[0]) {
               case 1:
                   return doMatchNumber04(inFixExpression, startCharIndex, endCharIndex);
               case 2:
                   return subPreFixScanFromLeftToRight(inFixExpression, currentCharIndex + 1, startCharIndex + 1, endCharIndex - 1);
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
                   char[] part21 = subPreFixScanFromLeftToRight(inFixExpression, currentCharIndex, startCharIndex, matchExpressionMessage[1] - 1);
                   char part22 = inFixExpression[matchExpressionMessage[1]];
                   char[] part23 = subPreFixScanFromLeftToRight(inFixExpression, matchExpressionMessage[1] + 1, matchExpressionMessage[1] + 1, endCharIndex);
                   return doMatchExpress04(part21, part22, part23);
               default:
                   break;
               }
       }

       throw new RuntimeException("该字符数组不是能被当做简单算术运算字符串处理的数组:\tcurrentIndex:" + currentCharIndex + "\tstartIndex:" + startCharIndex + "\tendIndex:" + endCharIndex);
   }

   private int matchNumber04(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       for (int i = currentCharIndex; i <= endCharIndex; i++) {
           if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
               return 0;
           }
       }
       
       return 1;
   }

   private char[] doMatchNumber04(char[] inFixExpression, int startCharIndex, int endCharIndex) {
       char[] returnChar = new char[endCharIndex - startCharIndex + 1];
       
       for (int i = 0; i < returnChar.length; i++) {
           returnChar[i] = inFixExpression[startCharIndex + i];
       }

       return returnChar;
   }
   
   private int matchTermMessage04(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       for (int i = currentCharIndex; i <= endCharIndex; i++) {
           if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
               break;
           }
           
           if (i == endCharIndex) {
               return 1;
           }
       }

       if (inFixExpression[startCharIndex] == '(' && inFixExpression[endCharIndex] == ')') {
           int matchParenthese = 1;
           for (int i = currentCharIndex + 1; i <= endCharIndex; i++) {
               if (inFixExpression[i] == ')') {
                   matchParenthese--;
               } else if (inFixExpression[i] == '(') {
                   matchParenthese++;
               }
   
               if (matchParenthese == 0) {
                   if (i == endCharIndex) {
                       return 2;
                   }
                   break;
               }
           }
       }

       return 0;
   }

   private int[] matchFactoryMessage04(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
           
           int i = startCharIndex;
           for (; i <= endCharIndex; i++) {
               if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                   break;
               }
               
               if (i == endCharIndex) {
                   return packMatchFactoryMessage04(1, 0);
               }
           }

           int[] matchFactoryMessage = matchFactoryMessage04(inFixExpression, i + 1, i + 1, endCharIndex);
           if (matchFactoryMessage[0] == 0) {
               return packMatchFactoryMessage04(0, 0);
           }

           if (inFixExpression[i] == '*') {
               return packMatchFactoryMessage04(3, i);
           }
           if (inFixExpression[i] == '/') {
               return packMatchFactoryMessage04(4, i);
           }
           if (inFixExpression[i] == '%') {
               return packMatchFactoryMessage04(5, i);
           }
       }

       if (inFixExpression[currentCharIndex] == '(') {
           int matchParenthese = 1;
           for (int i = currentCharIndex + 1; i <= endCharIndex; i++) {
               if (inFixExpression[i] == ')') {
                   matchParenthese--;
               } else if (inFixExpression[i] == '(') {
                   matchParenthese++;
               }

               if (matchParenthese == 0) {
                   if (i == endCharIndex) {
                       return packMatchFactoryMessage04(2, 0);
                   }
                   int[] matchFactorymessage = matchFactoryMessage04(inFixExpression, i + 2, i + 2, endCharIndex);
                   if (matchFactorymessage[0] == 0) {
                       return packMatchFactoryMessage04(0, 0);
                   }

                   if (inFixExpression[i + 1] == '*') {
                       return packMatchFactoryMessage04(6, i + 1);
                   }
                   if (inFixExpression[i + 1] == '/') {
                       return packMatchFactoryMessage04(7, i + 1);
                   }
                   if (inFixExpression[i + 1] == '%') {
                       return packMatchFactoryMessage04(8, i + 1);
                   }
                   break;
               }
           }
       }

       return packMatchFactoryMessage04(0, 0);
   }

   private int[] packMatchFactoryMessage04(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
       int[] matchFactoryMessage = new int[2];
       matchFactoryMessage[0] = matchFactoryType;
       matchFactoryMessage[1] = multiplyOrDivideOrModulusIndex;

       return matchFactoryMessage;
   }

   private char[] doMatchFactory04(char[] part1, char part2, char[] part3) {
       char[] part = new char[part1.length + 1 + part3.length];

       part[0] = part2;
       for (int i = 1, j = 0; j < part1.length; i++, j++) {
           part[i] = part1[j];
       }
       for (int i = part1.length + 1, j = 0; j < part3.length; i++, j++) {
           part[i] = part3[j];
       }

       return part;
   }

   private int[] matchExpressionMessage04(char[] inFixExpression, int currentCharIndex, int startCharIndex, int endCharIndex) {
       if (inFixExpression[currentCharIndex] >= '0' && inFixExpression[currentCharIndex] <= '9') {
           int i = startCharIndex;
           for (; i <= endCharIndex; i++) {
               if (inFixExpression[i] < '0' || inFixExpression[i] > '9') {
                   break;
               }
               
               if (i == endCharIndex) {
                   return packMatchFactoryMessage04(1, 0);
               }
           }

           if (inFixExpression[i] == '+') {
               return packMatchExpressionMessage04(3, i);
           }
           if (inFixExpression[i] == '-') {
               return packMatchExpressionMessage04(4, i);
           }
       }

       if (inFixExpression[currentCharIndex] == '(') {
           int matchParenthese = 1;
           int matchExpression = 0;
           for (int i = currentCharIndex + 1; i <= endCharIndex; i++) {
               if (inFixExpression[i] == ')') {
                   matchParenthese--;
                   if (matchParenthese == 0) {
                       matchExpression = 0;
                   }
               } else if (inFixExpression[i] == '(') {
                   matchParenthese++;
               } else if (inFixExpression[i] == '+' || inFixExpression[i] == '-') {
                   matchExpression++;
               }

               if (matchParenthese == 0) {
                   if (i == endCharIndex) {
                       return packMatchExpressionMessage04(2, 0);
                   }
                   if (matchExpression > 0) {
                       if (inFixExpression[i] == '+') {
                           return packMatchExpressionMessage04(5, i);
                       }
                       if (inFixExpression[i] == '-') {
                           return packMatchExpressionMessage04(6, i);
                       }
                   }
                   break;
               }
           }
       }

       int matchParenthese = 0;
       int matchExpression = 0;
       for (int i = currentCharIndex; i <= endCharIndex; i++) {
           if (inFixExpression[i] == ')') {
               matchParenthese--;
               if (matchParenthese == 0) {
                   matchExpression = 0;
               }
           } else if (inFixExpression[i] == '(') {
               matchParenthese++;
           } else if (inFixExpression[i] == '+' || inFixExpression[i] == '-') {
               matchExpression++;
           }

           if (matchParenthese == 0 && matchExpression > 0) {
               return packMatchExpressionMessage04(7, i);
           }
       }

       return packMatchExpressionMessage04(0, 0);
   }
   
   private int[] packMatchExpressionMessage04(int matchFactoryType, int multiplyOrDivideOrModulusIndex) {
       int[] matchExpressionMessage = new int[2];
       matchExpressionMessage[0] = matchFactoryType;
       matchExpressionMessage[1] = multiplyOrDivideOrModulusIndex;

       return matchExpressionMessage;
   }
   
   private char[] doMatchExpress04(char[] part1, char part2, char[] part3) {
       char[] part = new char[part1.length + 1 + part3.length];

       part[0] = part2;
       for (int i = 1, j = 0; j < part1.length; i++, j++) {
           part[i] = part1[j];
       }
       for (int i = part1.length + 1, j = 0; j < part3.length; i++, j++) {
           part[i] = part3[j];
       }

       return part;
   }
   
    public static void main(String[] args) {
        SimpleParser03 simpleParser = new SimpleParser03();
        char[] inFixExpression = "((111*121)+131*141*151+(161-171)*(181)+191)".toCharArray();

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
