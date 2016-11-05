package evaluation;

import maze.Maze;
import maze.MazeState;
import searchProblem.State;

/**
 * Created by Kareem-Adel on 10/5/2016.
 */
public class EuclideanOperation extends EvalOperation {

    @Override
    public Double apply(State startState) {
        int startStateX =  ((MazeState)startState).getX();
        int startStateY =  ((MazeState)startState).getY();
        int endStateX =  Maze.getMaze().getEndX();
        int endStateY =  Maze.getMaze().getEndY();
        return Math.sqrt(((endStateX - startStateX) ^ 2) + ((endStateY - startStateY) ^ 2));
    }
}
