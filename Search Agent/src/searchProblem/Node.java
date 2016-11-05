package searchProblem;

import java.util.ArrayList;
import java.util.List;

// This class describes the general node in any search tree
public class Node implements Comparable<Node> {
	private Node parentNode; // reference to parent node (null otherwise)
	private int pathCost; // holds total cost from the root (!IMPORTANT double is temporary).
	private double heuristicCostToGoal;
	private boolean useHeuristic, usePathCost;
	private List<Node> children; // children nodes (null if none)
	private State state; // state (holds additional info)
	private Operator operator; // action done to reach this node (null otherwise)
	private int depth = 0; // current depth of the node (0 at root)
	
	// constructor for only child nodes
	public Node(Node parentNode, int pathCost, State state,
			Operator operator){
		setParentNode(parentNode);
		setPathCost(pathCost);
		children = new ArrayList<Node>();
		setState(state);
		setOperator(operator);
		setDepth(parentNode.getDepth()+1);
		useHeuristic = parentNode.isUseHeuristic();
		usePathCost = parentNode.isUsePathCost();
	}
	
	// constructor for only parent node
	public Node(State state, boolean useCostPath,
			boolean useHeuristicCost){
		setState(state);
		setPathCost(0);
		setUseHeuristic(useHeuristicCost);
		setUsePathCost(useCostPath);
		setOperator(operator);
		setDepth(0);
		children = new ArrayList<Node>();
	}
	
	/*
	 * BEGINNING OF GETTERS & SETTERS
	 */

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	public void addToChildren(List<Node> children){
		this.children.addAll(children);
	}
	
	public void addToChildren(Node child){
		this.children.add(child);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/*
	 * END OF GETTERS & SETTERS
	 */
	
	@Override
	public int compareTo(Node o) {
		if(useHeuristic && !usePathCost){
			return compareUsingHeuristicOnly(o);
		}else if(useHeuristic && usePathCost){
			return compareUsingBoth(o);
		}
		int costDifference = pathCost - o.getPathCost();
//		if(costDifference != 0) return costDifference;
//		return depth - o.getDepth();
		return costDifference;
	}
	
	private int compareUsingHeuristicOnly(Node o){
		return (int) Math.round(heuristicCostToGoal - o.heuristicCostToGoal);
	}
	
	private int compareUsingBoth(Node o){
		return (int) Math.round( (heuristicCostToGoal + pathCost) -
			(o.heuristicCostToGoal + o.getPathCost()) );
	}
	
	@Override
	public String toString() {
		return state.toString() + ", H(n): " + getHeuristicCostToGoal();
	}

	public double getHeuristicCostToGoal() {
		return heuristicCostToGoal;
	}

	public void setHeuristicCostToGoal(double heuristicCostToGoal) {
		this.heuristicCostToGoal = heuristicCostToGoal;
	}

	public boolean isUseHeuristic() {
		return useHeuristic;
	}

	public void setUseHeuristic(boolean useHeuristic) {
		this.useHeuristic = useHeuristic;
	}

	public boolean isUsePathCost() {
		return usePathCost;
	}

	public void setUsePathCost(boolean usePathCost) {
		this.usePathCost = usePathCost;
	}
}
