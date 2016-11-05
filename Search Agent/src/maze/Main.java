package maze;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.Function;

import evaluation.HeuristicFunction;
import evaluation.MinimumSpanningTree;
import searchProblem.Node;
import searchProblem.Operator;
import searchProblem.State;
import searchStrategies.GeneralSearch;
import searchStrategies.SearchStrategy;

public final class Main {

    public static void main(String[] args) throws FileNotFoundException {
//    	System.setOut(new PrintStream("output.txt"));
        Maze maze = Maze.getMaze();
//        maze.genMaze2();
//        maze.printMaze();

        System.out.println("-------------------BREADTH FIRST-------------------");
        Search(maze, new SearchStrategy(SearchStrategy.type.BREADTH_FIRST), true);
        System.out.println("-------------------DEPTH FIRST-------------------");
        Search(maze, new SearchStrategy(SearchStrategy.type.DEPTH_FIRST), true);
        System.out.println("-------------------ITERATIVE DEEPENING-------------------");
        Search(maze, new SearchStrategy(SearchStrategy.type.ITERATIVE_DEEPENING), true);
        System.out.println("-------------------UNIFORM_COST------------------");
        Search(maze, new SearchStrategy(SearchStrategy.type.UNIFORM_COST), true);
        System.out.println("-------------------ASTAR------------------");
//      System.out.println("-------------------GREEDY------------------");
       HeuristicFunction h1 = new HeuristicFunction();
////       EuclideanOperation o1 = new EuclideanOperation();
       MinimumSpanningTree o2 = new MinimumSpanningTree();
       h1.addEvalOperation(o2);
//       
       
       Search(maze, new SearchStrategy(SearchStrategy.type.ASTAR, h1), true);
//       Search(maze, new SearchStrategy(type.GREEDY, h1), true);

    }


    /* SEARCH FUNCTION
     *  @param: Object MazeObject,
     *  @param: Object SearchStrategyObject,
     *  @param: boolean visualize
     *  @returns: [ 
     *  			Sequence Of Moves To Reach Goal (If Reachable) , 
     *  			Cost, 
     *  			Number Of Nodes Expanded 
     *  		  ]
     */
    public static void Search(Maze maze, SearchStrategy searchStrategy,
                              boolean visualize) {
        GeneralSearch gs = new GeneralSearch();
        Node resNode = gs.search(extractSearchProblem(maze), searchStrategy);
        if (resNode == null) {
            System.out.println();
            System.out.println("No Solution Found!!!");
        } else {
            System.out.println();
            System.out.println("Start AT: (" + maze.getStartY() + "," + maze.getStartX() + ") " + maze.getInitialDirection());
            System.out.println("End AT: (" + maze.getEndY() + "," + maze.getEndX() + ")");
            System.out.println("Number Of Pokemons " + maze.getNumberOfAvailablePokemons());
            System.out.println("Distance Left To Hatch " + maze.getDistanceLeftToHatch());
            printTrace(maze, resNode, visualize);
            System.out.println("Total Nodes Visited: " + gs.getNodesVisitedCount());
        }

    }


    public static void printTrace(Maze maze, Node node, boolean visualize) {
        if (node == null) {
            return;
        }
        printTrace(maze, node.getParentNode(), visualize);
        if (node.getOperator() != null)
            System.out.println("TRACE: " + node.getOperator());
        System.out.println("TRACE: " + node);
//        if(node.isUseHeuristic()) System.out.println(" H(n): " + node.getHeuristicCostToGoal());
//        else System.out.println();
        if (visualize) {
            MazeState mazeState = (MazeState) node.getState();
            Point currentAgentPointForPrint = new Point(mazeState.getX(), mazeState.getY());
            String currentAgentOrientationForPrint = null;
            switch (mazeState.getDirection()) {
                case EAST:
                    currentAgentOrientationForPrint = ">";
                    break;
                case WEST:
                    currentAgentOrientationForPrint = "<";
                    break;
                case NORTH:
                    currentAgentOrientationForPrint = "^";
                    break;
                case SOUTH:
                    currentAgentOrientationForPrint = "V";
                    break;
            }
            maze.printMaze(currentAgentPointForPrint, currentAgentOrientationForPrint);
        }
    }

    // extract search problem
    public static GottaCatchThemAll extractSearchProblem(Maze maze) {
        State initState =
                new MazeState(maze.getInitialDirection(),
                        maze.getStartX(), maze.getStartY(),
                        maze.getPokemons(),
                        maze.getDistanceLeftToHatch());

        Function<State, Boolean> goalTest =
                state ->
                        ((MazeState) state).getPokemonsAvailable().cardinality() == 0 &&
                                ((MazeState) state).getDistanceLeftToHatch() == 0 &&
                                ((MazeState) state).getX() == maze.getEndX() &&
                                ((MazeState) state).getY() == maze.getEndY();

        List<Operator> actions = new ArrayList<Operator>();
        actions.add(new OperatorForward());
        actions.add(new OperatorTurnRight());
        actions.add(new OperatorTurnLeft());
        actions.add(new OperatorGoBack());

        GottaCatchThemAll searchProblem =
                new GottaCatchThemAll(initState, actions, goalTest);
        searchProblem.setMaze(maze);
        searchProblem.setActions(actions);
        // a goal state to pass to a heuristic function for calcualtions
        State goalState = new MazeState(null, maze.getEndX(), maze.getEndY(),
                new BitSet(), 0);
        searchProblem.setGoalState(goalState);

        return searchProblem;
    }

}
