package evaluation;

import java.util.BitSet;
import java.util.List;

import maze.Location;
import maze.Maze;
import maze.MazeState;
import searchProblem.State;

public class FarthestTwoPoints extends EvalOperation{

	BitSet bs;
	
	@Override
	public Double apply(State state) {
		bs = ((MazeState)state).getPokemonsAvailable();
		if(bs.cardinality() == 0) return (double) ( Math.abs( ((MazeState)state).getX() - Maze.getMaze().getEndX() ) +
				Math.abs( ((MazeState)state).getY() - Maze.getMaze().getEndY() ) );
		
		List <Location> pokemons = Maze.getMaze().getPokemonsByIndex();
		
		int maxD = 0;
		int p1 = 0, p2 = 0;
		
		 for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
			 for (int j = bs.nextSetBit(0); j >= 0; j = bs.nextSetBit(j+1)) {
				 if(i != j) {
					 int temp = Math.abs( pokemons.get(i).getCol() - pokemons.get(j).getCol() )
							  + Math.abs( pokemons.get(i).getRow() - pokemons.get(j).getRow() );
					 if( temp > maxD) {
						 maxD = temp;
						 p1 = i;
						 p2 = j;
					 }
				 } 
			 }
		 }
		
		return (double) Math.min( Math.abs( ((MazeState)state).getX() - pokemons.get(p1).getCol() )
							      +  Math.abs( ((MazeState)state).getY() - pokemons.get(p1).getRow() )
							      +  maxD
							      +  Math.abs( Maze.getMaze().getEndX() - pokemons.get(p2).getCol() )
							      +  Math.abs( Maze.getMaze().getEndY() - pokemons.get(p2).getRow() ) ,
							      Math.abs( ((MazeState)state).getX() - pokemons.get(p2).getCol() )
							      +  Math.abs( ((MazeState)state).getY() - pokemons.get(p2).getRow() )
							      +  maxD
							      +  Math.abs( Maze.getMaze().getEndX() - pokemons.get(p1).getCol() )
							      +  Math.abs( Maze.getMaze().getEndY() - pokemons.get(p1).getRow() ) );
								  
	}

}
