package evaluation;

import java.util.BitSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import maze.Location;
import maze.Maze;
import maze.MazeState;
import searchProblem.State;

public class Xdistance extends EvalOperation{
	BitSet bs;

	
	// calculate sum Of X distances from current State to all pokemons and end state
	@Override
	public Double apply(State state) {
		bs = ((MazeState)state).getPokemonsAvailable();
		if(bs.cardinality() == 0) return (double) Math.abs( ((MazeState)state).getX() - Maze.getMaze().getEndX() );
		
		int rightMostX = Integer.MIN_VALUE;
		int leftMostX = Integer.MAX_VALUE;
		List <Location> pokemons = Maze.getMaze().getPokemonsByIndex();
		
		 for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
			 for (int j = bs.nextSetBit(0); j >= 0; j = bs.nextSetBit(j+1)) {
			 rightMostX = Math.max(rightMostX, pokemons.get(i).getCol());
			 leftMostX = Math.min(leftMostX, pokemons.get(i).getCol());
			 }
		 }
		
		return (double) Math.min( Math.abs(rightMostX - ((MazeState)state).getX()) + Math.abs(rightMostX - leftMostX)
								  + Math.abs(Maze.getMaze().getEndX() - leftMostX),
								  Math.abs(((MazeState)state).getX() - leftMostX) + Math.abs(rightMostX - leftMostX)
								  + Math.abs(Maze.getMaze().getEndX() - rightMostX) );
	}

}
