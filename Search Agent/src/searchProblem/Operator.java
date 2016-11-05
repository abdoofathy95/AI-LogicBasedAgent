package searchProblem;

import java.util.List;

public abstract class Operator {
	public int cost;
	public abstract List<Node> apply(Node node, SearchProblem searchProblem);
}
