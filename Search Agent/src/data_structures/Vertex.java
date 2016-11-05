package data_structures;

import maze.Location;

public class Vertex implements Comparable<Vertex>{
	
	int cost;
	Location from, to;
	public Vertex(Location from, Location to){
		this.from = from;
		this.to = to;
		this.cost = Math.abs(from.getCol() - to.getCol()) + 
				Math.abs(from.getRow() - to.getRow());
	}

	@Override
	public int compareTo(Vertex o) {
		return cost - o.cost;
	}
	
	public Location getToVertex(){
		return to;
	}
	
	@Override
	public String toString() {
		return "[" +from + "," + to + "]" + cost;
	}
	
	
}
