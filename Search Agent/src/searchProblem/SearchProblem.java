package searchProblem;
/* 
 * this class describes any Search Problem
 * to use this class simply instantiate an instance from it
*/
import java.util.List;
import java.util.function.Function;

public class SearchProblem {
	private State initState; // initialState of problem
	private List<Operator> actions; // list of actions
	private Function<State, Boolean> goalTest; // function representing the Goal Test
	// out of search problem definition
	private State goalState;
	
	public SearchProblem(State initState, List<Operator> actions,
			Function<State, Boolean> goalTest){
		this.setInitState(initState);
		this.setActions(actions);
		this.setGoalTest(goalTest);
	}

	public State getInitState() {
		return initState;
	}

	public void setInitState(State initState) {
		this.initState = initState;
	}

	public List<Operator> getActions() {
		return actions;
	}

	public void setActions(List<Operator> actions) {
		this.actions = actions;
	}

	public Function<State, Boolean> getGoalTest() {
		return goalTest;
	}

	public void setGoalTest(Function<State, Boolean> goalTest) {
		this.goalTest = goalTest;
	}
	
	public State getGoalState(){
		return goalState;
	}
	
	public void setGoalState(State goalState){
		this.goalState = goalState;
	}
}
