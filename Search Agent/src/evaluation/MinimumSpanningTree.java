package evaluation;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import data_structures.MST;
import maze.Location;
import maze.Maze;
import maze.MazeState;
import searchProblem.State;

public class MinimumSpanningTree extends EvalOperation{
	MST m;
	
	public MinimumSpanningTree() {
		m = new MST();
	}

	@Override
	public Double apply(State startState) {
		BitSet bs = ((MazeState)startState).getPokemonsAvailable();
		List<Location> availablePokemons = new ArrayList<Location>();
		for(int i=bs.nextSetBit(0);i!=-1;i = bs.nextSetBit(i+1)){
			availablePokemons.add(Maze.getMaze().getPokemonsByIndex().get(i));
		}
		m.getMST(availablePokemons, 
				new Location(Maze.getMaze().getStartY(), Maze.getMaze().getStartX()),
				new Location(Maze.getMaze().getEndY(), Maze.getMaze().getEndX()));
		return m.getCost()*1.0;
	}

}
