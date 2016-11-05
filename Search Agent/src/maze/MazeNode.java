package maze;

import searchProblem.Node;
import searchProblem.Operator;
import searchProblem.State;

public class MazeNode extends Node {
	public MazeNode(Node parentNode, int pathCost, State state, Operator operator) {
		super(parentNode, pathCost, state, operator);
	}
	
	public MazeNode(State state, boolean useCostPath,
			boolean useHeuristicCost){
		super(state, useCostPath, useHeuristicCost);
	}
	
	@Override
	public String toString() {
		return getState().toString();
	}
	
	

}
