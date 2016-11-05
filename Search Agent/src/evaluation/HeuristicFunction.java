package evaluation;

import java.util.ArrayList;

import searchProblem.State;

/**
 * Created by Kareem-Adel on 10/5/2016.
 */
public class HeuristicFunction {
    private ArrayList<EvalOperation> evalOperations;

    public HeuristicFunction() {
        this.evalOperations = new ArrayList<>();
    }

    public void addEvalOperation(EvalOperation evalOperation) {
        evalOperations.add(evalOperation);
    }
    // for heuristics needing two states ex. start & end
    public Integer apply(State startState) { 
        int heuristicCost = 0;
        for (EvalOperation evalOperation : evalOperations) {
            heuristicCost += evalOperation.apply(startState);
        }
        return heuristicCost;
    }
}
