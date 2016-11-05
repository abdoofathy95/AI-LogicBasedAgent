package searchStrategies;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

import searchProblem.Node;

public class Queue<T extends Comparable<T>> {
	private ArrayDeque<T> arr;
	private PriorityQueue<T> pq;
	private SearchStrategy searchStrategy;

	
	public Queue(SearchStrategy searchStrategy) {
		if(searchStrategy.getType() != SearchStrategy.type.UNIFORM_COST &&
				 searchStrategy.getType() != SearchStrategy.type.GREEDY && 
				 searchStrategy.getType() != SearchStrategy.type.ASTAR)
			arr = new ArrayDeque<T>();
		else
			pq = new PriorityQueue<T>();
		this.searchStrategy = searchStrategy;
	}

	public void insert(T n) {
		switch(searchStrategy.getType()) {
			case BREADTH_FIRST: arr.addLast(n); break;
			case DEPTH_FIRST: arr.addFirst(n); break;
			case UNIFORM_COST: 
			case GREEDY:
			case ASTAR: pq.add(n); 
						break;
			default: if(((Node)n).getDepth() <= searchStrategy.getCurrentLimit()) arr.addFirst(n);
		}
	}
	
	public T remove() {
		switch(searchStrategy.getType()) {
			case UNIFORM_COST:
			case GREEDY:
			case ASTAR: 
				return pq.poll();
			default: return arr.removeFirst();
		}
	}
	
	public T getMinimum() {
		switch(searchStrategy.getType()) {
			case UNIFORM_COST: 
			case GREEDY:
			case ASTAR:
				return pq.peek();
			default: return arr.getFirst();
		}
	}

	public int size()
	{
		switch(searchStrategy.getType()) {
			case BREADTH_FIRST: return arr.size();
			case UNIFORM_COST: 
			case GREEDY:
			case ASTAR: 
				return pq.size();
			default: return arr.size();
		}
	}
	
	public boolean isEmpty() {
		switch(searchStrategy.getType()) {
			case UNIFORM_COST:
			case GREEDY:
			case ASTAR:
				return pq.isEmpty();
			default: return arr.isEmpty();
		}
	}

	 @Override
	public String toString() {
		 
		 StringBuilder sb = new StringBuilder();
		 sb.append("[");
		 if(searchStrategy.getType() != SearchStrategy.type.UNIFORM_COST &&
				 searchStrategy.getType() != SearchStrategy.type.GREEDY && 
				 searchStrategy.getType() != SearchStrategy.type.ASTAR) {
			 for(T e: arr){
				 sb.append(e);
			 }
		 }
		 else {
			 for(T e: pq){
				 sb.append(e);
			 } 
		 }
		 sb.append("]");
		 return sb.toString();
	}
}
