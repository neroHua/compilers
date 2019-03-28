package main.chapter03.part02;

public class NFAScanner {
    
    private char[] specialKey = {'\\', '|', '*', '(', ')', '[', ']', '{', '}', ','};

    private char[] expression;
    private int scannedIndex;
    private boolean hasNextScanedIndexBeenAddConcateNation;
    
    public NFAScanner (char[] expression) {
        this.expression = expression;
        this.scannedIndex = -1;
        this.hasNextScanedIndexBeenAddConcateNation = false;
    }
    
    public NFAScanner (char[] expression, int startIndex, int endIndex) {
        this.expression = new char[endIndex - startIndex + 1];
        for (int i = 0; i < this.expression.length; i++) {
            this.expression[i] = expression[i + startIndex];
        }
        this.scannedIndex = -1;
        this.hasNextScanedIndexBeenAddConcateNation = false;
    }
    
    public int getScannedIndex() {
        return this.scannedIndex;
    }
    
    public NFAOperation getNextOperation() {
        if (scannedIndex >= expression.length - 1) {
            throw new RuntimeException("no more char need to be scanned");
        }
        if (scannedIndex == -1) {
            if (expression[scannedIndex + 1] == '|') {
                throw new RuntimeException("| should not in the first place of one expression:" + String.copyValueOf(expression));
            }
            
            if (expression[scannedIndex + 1] == '*') {
                throw new RuntimeException("* should not in the first place of one expression:" + String.copyValueOf(expression));
            }

            if (expression[scannedIndex + 1] == '(') {
                scannedIndex++;
                return new NFAOperationParentheseLeft();
            }
            if (expression[scannedIndex + 1] == ')') {
                throw new RuntimeException(") should not in the first place of one expression:" + String.copyValueOf(expression));
            }
            
            if (expression[scannedIndex + 1] == '[') {
                int oldScanedIndex = scannedIndex;
                int i = scannedIndex + 2;
                for (; i < expression.length; i++) {
                    if (expression[i] == ']') {
                        break;
                    }
                }
                if (i < expression.length) {
                    scannedIndex = i + 1;
                    return new NFAOperationScopeUnion(expression, oldScanedIndex + 1, i);
                }
                throw new RuntimeException("cant't find a ] int the " + String.copyValueOf(expression));
            }
            
            if (expression[scannedIndex + 1] == ']') {
                throw new RuntimeException("] should not in the first place of one expression:" + String.copyValueOf(expression));
            }

            if (expression[scannedIndex + 1] == '{') {
                throw new RuntimeException("{ should not in the first place of one expression:" + String.copyValueOf(expression));
            }

            if (expression[scannedIndex + 1] == '}') {
                throw new RuntimeException("} should not in the first place of one expression:" + String.copyValueOf(expression));
            }
            
            scannedIndex++;
            return new NFAOperationBase(expression, scannedIndex, scannedIndex);
        } else {
            if (expression[scannedIndex + 1] == '|') {
                scannedIndex++;
                return new NFAOperationUnion();
            }
            
            if (expression[scannedIndex + 1] == '*') {
                return new NFAOperationClosure();
            }
            
            if (expression[scannedIndex + 1] == '(') {
               if (expression[scannedIndex] == '|') {
                   scannedIndex++;
                   return new NFAOperationParentheseLeft();
               } 
               
               if (!hasNextScanedIndexBeenAddConcateNation) {
                   hasNextScanedIndexBeenAddConcateNation = true;
                   return new NFAOperationConcatenation();
               } else {
                   scannedIndex++;
                   hasNextScanedIndexBeenAddConcateNation = false;
                   return new NFAOperationParentheseLeft();
               }
            }
            
            if (expression[scannedIndex + 1] == ')') {
                return new NFAOperationParentheseLeft();
            }
            
            if (expression[scannedIndex + 1] == '[') {
                if (expression[scannedIndex] == '|') {
                    int oldScanedIndex = scannedIndex;
                    int i = scannedIndex + 2;
                    for (; i < expression.length; i++) {
                        if (expression[i] == ']') {
                            break;
                        }
                    }
                    if (i < expression.length) {
                        scannedIndex = i + 1;
                        return new NFAOperationScopeUnion(expression, oldScanedIndex, i);
                    }
                    throw new RuntimeException("cant't find a ] int the " + String.copyValueOf(expression));
                } 
                
                if (!hasNextScanedIndexBeenAddConcateNation) {
                    hasNextScanedIndexBeenAddConcateNation = true;
                    return new NFAOperationConcatenation();
                } else {
                    hasNextScanedIndexBeenAddConcateNation = false;
                    int oldScanedIndex = scannedIndex;
                    int i = scannedIndex + 2;
                    for (; i < expression.length; i++) {
                        if (expression[i] == ']') {
                            break;
                        }
                    }
                    if (i < expression.length) {
                        scannedIndex = i + 1;
                        return new NFAOperationScopeUnion(expression, oldScanedIndex, i);
                    }
                    throw new RuntimeException("cant't find a ] int the " + String.copyValueOf(expression));
                }   
            }

            if (expression[scannedIndex + 1] == ']') {
                throw new RuntimeException("] should after[ in one expression:" + String.copyValueOf(expression));
            }

            if (expression[scannedIndex + 1] == '{') {
                int oldScanedIndex = scannedIndex;
                int i = scannedIndex + 2;
                for (; i < expression.length; i++) {
                    if (expression[i] == '}') {
                        break;
                    }
                }
                if (i < expression.length) {
                    scannedIndex = i + 1;
                    return new NFAOperationScopeUnion(expression, oldScanedIndex, i);
                }
                throw new RuntimeException("cant't find a } int the " + String.copyValueOf(expression));
            }

            if (expression[scannedIndex + 1] == '}') {
                throw new RuntimeException("} should after{ in one expression:" + String.copyValueOf(expression));
            }
            
            if (expression[scannedIndex] == '|') {
                scannedIndex++;
                return new NFAOperationParentheseLeft();
            } 
            
            if (!hasNextScanedIndexBeenAddConcateNation) {
                hasNextScanedIndexBeenAddConcateNation = true;
                return new NFAOperationConcatenation();
            } else {
                scannedIndex++;
                hasNextScanedIndexBeenAddConcateNation = false;
                return new NFAOperationParentheseLeft();
            }           
        }

    }
    
}