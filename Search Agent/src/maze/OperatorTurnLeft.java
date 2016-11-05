package maze;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import maze.MazeState.Direction;
import searchProblem.Node;
import searchProblem.Operator;
import searchProblem.SearchProblem;

public class OperatorTurnLeft extends Operator{
	
	public OperatorTurnLeft() {
		cost = 2; // not final weight
	}

	@Override
	public List<Node> apply(Node node, SearchProblem searchProblem) {
		Direction direction = ((MazeState)node.getState()).getDirection();
		List<Node> nodes = new ArrayList<Node>();
		Maze maze = ((GottaCatchThemAll) searchProblem).getMaze();
		int x = ((MazeState)node.getState()).getX();
		int y = ((MazeState)node.getState()).getY();
		int d = ((MazeState)node.getState()).getDistanceLeftToHatch();
		BitSet bs = (BitSet) (((MazeState) node.getState()).getPokemonsAvailable()).clone();
		
		switch(direction){
		case EAST:
            if (maze.isWayOpen(x, y, Direction.NORTH)) {
                MazeState state = new MazeState(Direction.NORTH, x, y - 1, bs, d);
                if (maze.isCellHasPokemon(x, y - 1)) {
                    state.catchPokemon(maze.getPokemonsByLocation().get(new Location(y-1, x)));
                }
                state.updateDistanceLeftToHatch(--d);
                nodes.add(new Node(node, node.getPathCost() + cost, state, this));

            }
			break;
		case WEST:
            if (maze.isWayOpen(x, y, Direction.SOUTH)) {
                MazeState state = new MazeState(Direction.SOUTH, x, y + 1, bs, d);
                if (maze.isCellHasPokemon(x, y + 1)) {
                    state.catchPokemon(maze.getPokemonsByLocation().get(new Location(y+1, x)));
                }
                state.updateDistanceLeftToHatch(--d);
                nodes.add(new Node(node, node.getPathCost() + cost, state, this));
            }
			break;
		case NORTH:
            if (maze.isWayOpen(x, y, Direction.WEST)) {
                MazeState state = new MazeState(Direction.WEST, x - 1, y, bs, d);
                if (maze.isCellHasPokemon(x - 1, y)) {
                    state.catchPokemon(maze.getPokemonsByLocation().get(new Location(y, x-1)));
                }
                state.updateDistanceLeftToHatch(--d);
                nodes.add(new Node(node, node.getPathCost() + cost, state, this));
            }
			break;
		case SOUTH:
			 if (maze.isWayOpen(x, y, Direction.EAST)) {
             	
                 MazeState state = new MazeState(Direction.EAST, x + 1, y, bs, d);
                 if (maze.isCellHasPokemon(x + 1, y)) {
                     state.catchPokemon(maze.getPokemonsByLocation().get(new Location(y, x+1)));
                 }
                 state.updateDistanceLeftToHatch(--d);
                 nodes.add(new Node(node, node.getPathCost() + cost, state, this));
             }
			break;
		}
		node.addToChildren(nodes);
		return nodes;
	}
	
	@Override
	public String toString() {
		return "Turn Left";
	}
}
