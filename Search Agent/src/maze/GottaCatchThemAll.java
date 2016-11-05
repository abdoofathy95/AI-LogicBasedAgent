package maze;

import java.util.List;
import java.util.function.Function;

import searchProblem.Operator;
import searchProblem.SearchProblem;
import searchProblem.State;

public class GottaCatchThemAll extends SearchProblem {
	
	private Maze maze;
	
	public GottaCatchThemAll(State initState, List<Operator> actions, Function<State, Boolean> goalTest) {
		super(initState, actions, goalTest);
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	

}
