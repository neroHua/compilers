package main.chapter03.part02;

/**
 * 
 * 左括号运算： 主要用于创建子树，配合完成从左往右扫描，而不用递归扫描
 *
 */
public class NFAOperationParentheseLeft implements NFAOperation {

    @Override 
    public NFAGraph getNFAGraph() {
        return null;
    }
    
    @Override
    public boolean hasInnerOperation() {
        return false;
    }

}