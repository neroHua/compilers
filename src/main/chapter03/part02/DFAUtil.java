package main.chapter03.part02;

import java.util.ArrayList;
import java.util.List;

public class DFAUtil {
    
    public static DFAGraph convertNFAGraphToDFAGraph(NFAGraph NFAGraph) {
        if (null == NFAGraph) {
            throw new IllegalArgumentException("NFAGraph should not be null");
        }

        List<List<Integer>> DFAStateList = new ArrayList<List<Integer>>();
        List<Integer> DFAStartStateList = eClosure(NFAGraph, NFAGraph.getStartState());
        if (null == DFAStartStateList) {
            DFAStartStateList = new ArrayList<Integer>();
        } 
        DFAStartStateList.add(NFAGraph.getStartState());
        DFAStateList.add(DFAStartStateList);

        Integer DFAStartState = 0;
        List<Integer> DFAEndState = new ArrayList<Integer>();
        List<Integer[]> convertTable = new ArrayList<Integer[]>();

        for (int i = 0; i < DFAStateList.size(); i++) {
            Integer[] ithConvertTable = new Integer[DFAGraph.ALL_CHAR_LENGTH];

            for (int j = 0; j < DFAGraph.ALL_CHAR_LENGTH; j++) {
                List<Integer> nextStateList = move(NFAGraph, DFAStateList.get(i), (char) j);
                if (null == nextStateList || 0 == nextStateList.size()) {
                    continue;
                }
                Integer nextStateListIndex = alreadyHaveThisState(DFAStateList, nextStateList);
                if (null != nextStateListIndex) {
                    ithConvertTable[j] = nextStateListIndex;
                } else {
                    if (nextStateList.contains(NFAGraph.getEndState())) {
                        DFAEndState.add(DFAStateList.size());
                    }
                    ithConvertTable[j] = DFAStateList.size();
                    DFAStateList.add(DFAStateList.size(), nextStateList);
                }
            }

            convertTable.add(i, ithConvertTable);
        }
        
        return new DFAGraph(DFAStartState, DFAEndState, convertListToArray(convertTable));
    }
    
    private static Integer alreadyHaveThisState(List<List<Integer>> NFAStateList, List<Integer> nextStateList) {
        for (int i = 0; i < NFAStateList.size(); i++) {
            List<Integer> hadStateList = NFAStateList.get(i);
            boolean had = false;

            for (int j = 0; j < hadStateList.size(); j++) {
                if (!nextStateList.contains(hadStateList.get(j))) {
                    break;
                } else {
                    if (j == hadStateList.size() - 1) {
                        had = true;
                    }
                }
            }
            
            if (had) {
                return i;
            }
        }

        return null;
    }

    private static List<Integer> eClosure(NFAGraph NFAGraph, Integer NFAState) {
        List<Integer> eTransitionNFAList = NFAGraph.getNextStateByE(NFAState);

        if (null == eTransitionNFAList) {
            return null;
        }

        List<Integer> eTransitionNFAExpandList = new ArrayList<Integer>();
        for (Integer subNFAState : eTransitionNFAList) {
            List<Integer> subETransitionNFAList = eClosure(NFAGraph, subNFAState);
            if (null == subETransitionNFAList) {
                continue;
            }

            for (Integer state : subETransitionNFAList) {
                if (eTransitionNFAList.contains(state)) {
                    continue;
                }
                eTransitionNFAExpandList.add(state);
            }
        }

        for (Integer expandState : eTransitionNFAExpandList) {
            eTransitionNFAList.add(expandState);
        }

        return eTransitionNFAList;
    }
    
    private static List<Integer> eClosure(NFAGraph NFAGraph, List<Integer> NFAState) {
        if (null == NFAState) {
            return null;
        }

        List<Integer> eTransitionNFAList = new ArrayList<Integer>();
        for (Integer nfaState : NFAState) {
            List<Integer> subETransitionNFAList = eClosure(NFAGraph, nfaState);
            if (null == subETransitionNFAList) {
                continue;
            }

            for (Integer state : subETransitionNFAList) {
                if (eTransitionNFAList.contains(state)) {
                    continue;
                }
                eTransitionNFAList.add(state);
            }
        }

        return eTransitionNFAList;
    }
    
    private static List<Integer> move(NFAGraph NFAGraph, List<Integer> nfaStateList, Character nextChar) {
        if (null == nfaStateList || nfaStateList.size() == 0) {
            return null;
        }

        List<Integer> charTransitionNFAList = new ArrayList<Integer>();
        for (Integer nfaState : nfaStateList) {
            List<Integer> subCharTransitionNFAList = NFAGraph.getNextState(nfaState, nextChar);
            if (null == subCharTransitionNFAList) {
                continue;
            }

            for (Integer state : subCharTransitionNFAList) {
                if (charTransitionNFAList.contains(state)) {
                    continue;
                }
                charTransitionNFAList.add(state);
            }
        }
        
        List<Integer> eCharTransitionNFAList = eClosure(NFAGraph, charTransitionNFAList);
        if (null == eCharTransitionNFAList || 0 == eCharTransitionNFAList.size()) {
            return charTransitionNFAList;
        }
        for (Integer eState : eCharTransitionNFAList) {
            if (charTransitionNFAList.contains(eState)) {
                continue;
            }
            charTransitionNFAList.add(eState);
        }

        return charTransitionNFAList;
    }

    private static Integer[][] convertListToArray(List<Integer[]> convertTable) {
        Integer[][] array = new Integer[convertTable.size()][NFAGraph.ALL_CHAR_LENGTH];
        for (int i = 0; i < convertTable.size(); i++) {
            array[i] = convertTable.get(i);
        }
        return array;
    }

}