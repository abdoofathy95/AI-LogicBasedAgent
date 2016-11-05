package maze;

import java.util.BitSet;

import searchProblem.State;

public class MazeState extends State {
    enum Direction {NORTH, SOUTH, EAST, WEST}; // all possible directions

    private int locX, locY; // location on the grid
    private BitSet PokemonsAvailable;
    private int distanceLeftToHatch;
    private Direction currentDirection; // current orientation of the agent

    MazeState(Direction currentDirection, int locX, int locY,
    		BitSet PokemonsAvailable, int distanceLeftToHatch) {
        this.currentDirection = currentDirection;
        this.locX = locX;
        this.locY = locY;
        this.PokemonsAvailable = PokemonsAvailable;
        this.distanceLeftToHatch = distanceLeftToHatch;
    }

    // setters and getters
    public int getX() {
        return locX;
    }

    public int getY() {
        return locY;
    }

    public void setX(int locX) {
        this.locX = locX;
    }

    public void setY(int locY) {
        this.locY = locY;
    }

    public BitSet getPokemonsAvailable() {
        return PokemonsAvailable;
    }

    public void catchPokemon(int index) {
    	PokemonsAvailable.clear(index);
    }

    public int getDistanceLeftToHatch() {
        return distanceLeftToHatch;
    }

    public void updateDistanceLeftToHatch(int d) { // distance shouldn't be < 0
    	if(d < 0) return;
        distanceLeftToHatch = d;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void setDirection(Direction d) {
        currentDirection = d;
    }

    @Override
    public String toString() {
    	return "{" + locY + ", " + locX + ", " + currentDirection + "}";
        //return "{X:" + locX + ",Y:" + locY + ",D:" + currentDirection + "}";
    }

}
