package searchStrategies;

import java.util.List;
import java.util.ListIterator;

import evaluation.HeuristicFunction;
import maze.MazeNode;
import maze.MazeState;
import searchProblem.Node;
import searchProblem.Operator;
import searchProblem.SearchProblem;

public final class GeneralSearch { // not to be extended
    private int nodesVisitedCount;

    public GeneralSearch() {
    	nodesVisitedCount = 0;
    }

    public Node search(SearchProblem searchProblem, SearchStrategy searchStrategy){
    	if(searchStrategy.getType() == (SearchStrategy.type.ITERATIVE_DEEPENING))
    		return iterativeDeepeningSearch(searchProblem, searchStrategy);
    	else
    		return generalSearch(searchProblem, searchStrategy);
    				
    }

    public Node generalSearch(SearchProblem searchProblem,
    		SearchStrategy searchStrategy) {
    	boolean useCostPath = false;
    	boolean useHeuristic = false;
    	MazeState endState = (MazeState)searchProblem.getGoalState();
    	switch(searchStrategy.getType()){
    	case UNIFORM_COST:
    		useCostPath = true;
    		break;
    	case GREEDY:
    		useHeuristic = true;
    		break;
    	case ASTAR:
    		useCostPath = true;
    		useHeuristic = true;
    		break;
		default:
			break;
    	}
        Queue<Node> nodesQueue = new Queue<Node>(searchStrategy);
        Node initNode = new MazeNode(searchProblem.getInitState(), useCostPath,
        		useHeuristic);
        setHeuristicValuesIfAny(initNode, searchStrategy.getHeuristicFunction(), endState);
        nodesQueue.insert(initNode);
        while (!nodesQueue.isEmpty()) {
//        	System.out.println(nodesQueue);
        	Node node = nodesQueue.remove();
            nodesVisitedCount++;
            if (searchProblem.getGoalTest().apply(node.getState())) {
            	if(searchStrategy.getType() == SearchStrategy.type.ASTAR)
            		System.out.println("Cost of Goal Node: " + ((double) node.getPathCost() + node.getHeuristicCostToGoal()));
            	else if(searchStrategy.getType() == SearchStrategy.type.GREEDY)
        			System.out.println("Cost of Goal Node: " + node.getHeuristicCostToGoal());
            	else
            		System.out.println("Cost of Goal Node: " + node.getPathCost());
                return node; // found goal
            } else {
            	ListIterator<Operator> iterator;
            	if(searchStrategy.getType() != SearchStrategy.type.DEPTH_FIRST && searchStrategy.getType() !=  SearchStrategy.type.ITERATIVE_DEEPENING) {
	    			iterator = searchProblem.getActions().listIterator();
	
	                while (iterator.hasNext()) {
	                    iterator.next().apply(node, searchProblem);
	                }
            	}
            	else {
            		iterator = searchProblem.getActions().listIterator(searchProblem.getActions().size());
	                while (iterator.hasPrevious()) {
	                    iterator.previous().apply(node, searchProblem);
	                }
            	}
            	
                List<Node> children = node.getChildren();
                for(Node child : children){
                	setHeuristicValuesIfAny(child,
                			searchStrategy.getHeuristicFunction(), endState);
                	nodesQueue.insert(child); // should use different way
                }
                	
//                System.out.println("ava: "+((MazeState)node.getState()).getPokemonsAvailable());
//                System.out.println(node + "   " + node.getHeuristicCostToGoal());
//                System.out.println(nodesQueue);
////                System.out.println(nodesQueue.getMinimum());
//                System.out.println(((MazeState)node.getState()).getDistanceLeftToHatch());
//                System.out.println("----------------------------");
            }
        }
        return null; // failure
    }

    public Node iterativeDeepeningSearch(SearchProblem searchProblem, 
    		SearchStrategy searchStrategy){
    	for(int i = 0; i <= Integer.MAX_VALUE; i++) {
    		searchStrategy.setCurrentLimit(i);
    		Node node = generalSearch(searchProblem, searchStrategy);
    		if(node != null){
    			return node;
    		}
    	}
    	return null;
    }
    
    private void setHeuristicValuesIfAny(Node node, HeuristicFunction f, MazeState endState){
    	if(f == null) return;
    	// get end state here 
    	double heuristicValue = f.apply(node.getState());
//    	System.out.println("-----------------------------------");
//    	System.out.println(((MazeState)node.getState()).getX()+","+((MazeState)endState).getX());
//    	System.out.println(node+","+heuristicValue);
    	node.setHeuristicCostToGoal(heuristicValue);
    }

    public int getNodesVisitedCount() {
        return nodesVisitedCount;
    }

    public void setNodesVisitedCount(int nodesVisitedCount) {
        this.nodesVisitedCount = nodesVisitedCount;
    }
}
