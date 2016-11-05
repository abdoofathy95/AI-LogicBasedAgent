package searchProblem;

import java.util.function.Function;

/* 
 * this abstract class describes the generalState of any problem
 */
public abstract class State {
	public boolean isPassGoalTest(Function<State, Boolean> goalTest){
		return goalTest.apply(this);
	}

}