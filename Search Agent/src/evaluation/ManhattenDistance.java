package evaluation;

import maze.Maze;
import maze.MazeState;
import searchProblem.State;

public class ManhattenDistance extends EvalOperation{

	@Override
	public Double apply(State startState) {
		return
				Math.abs(    (double) Maze.getMaze().getEndX() - ((MazeState)startState).getX() )
			 + Math.abs( (double) Maze.getMaze().getEndY() - ((MazeState)startState).getY() );
	}

}
