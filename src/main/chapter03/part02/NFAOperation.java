package main.chapter03.part02;

/**
 * 
 * 运算顶级父类
 * 
 *
 */
public interface NFAOperation {
    
    NFAGraph getNFAGraph();
    
    boolean hasInnerOperation();
}