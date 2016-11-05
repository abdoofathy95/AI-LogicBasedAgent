package data_structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import maze.Location;

public class MST {
	private int cost;
	
	public List<Vertex> getMST(List<Location> pokemons, Location current, Location end ){
		List<Vertex> shortestPath = new ArrayList<Vertex>(); // result
		cost = 0;
		Set<Location> unvisitedLocations = new HashSet<Location>();
		unvisitedLocations.add(current);
		unvisitedLocations.addAll(pokemons); // set to pick from 
		unvisitedLocations.add(end);
		
		// edges holds all the Vertices connecting locations (sorted)
		Queue<Vertex> edges = new PriorityQueue<Vertex>(); // priority to pick the shortest

		while(!unvisitedLocations.isEmpty()){
			
			for(Location l: unvisitedLocations){
				if(unvisitedLocations.contains(current) && !current.equals(l))
					edges.add(new Vertex(current,l));
			}
			
			Vertex shortestEdge = edges.poll();
			unvisitedLocations.remove(shortestEdge.from);
			unvisitedLocations.remove(shortestEdge.to);
			shortestPath.add(shortestEdge);
			cost += shortestEdge.cost;
			current = shortestEdge.to;
		}
		return shortestPath;
	}
	
	public int getCost(){
		return cost;
	}
}
