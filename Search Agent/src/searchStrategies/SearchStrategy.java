package searchStrategies;

import evaluation.HeuristicFunction;

public class SearchStrategy {
	
	public enum type {
		BREADTH_FIRST, UNIFORM_COST, DEPTH_FIRST, ITERATIVE_DEEPENING, 
		GREEDY, ASTAR;
	}
	private type type;
	private int currentLimit;
	private HeuristicFunction heuristicFunction;
	
	public SearchStrategy(type type) {
		this.type = type;
	}
	
	public SearchStrategy(type type, int currentLimit) {
		this.type = type;
		this.currentLimit = currentLimit;
	}
	
	public SearchStrategy(type type, HeuristicFunction heuristicFunction) {
		this.type = type;
		this.heuristicFunction = heuristicFunction;
	}
	
	public type getType() {
		return type;
	}
	
	public int getCurrentLimit() {
		return currentLimit;
	}
	
	public void setCurrentLimit(int value) {
		currentLimit = value;
	}
	
	public boolean equal(SearchStrategy other) {
		return type == other.getType();
	}
	
	public HeuristicFunction getHeuristicFunction(){
		return heuristicFunction;
	}
	
	public void setHeuristicFunction(HeuristicFunction h){
		heuristicFunction = h;
	}
}
