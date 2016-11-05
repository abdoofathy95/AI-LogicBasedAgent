package maze;

import maze.MazeState.Direction;

interface IMaze {
    public void genMaze();

    public Direction getInitialDirection();

    public int getDistanceLeftToHatch();

    public boolean isCellHasPokemon(int x, int y);

    public boolean isWayOpen(int x, int y, Direction direction); // can move to it (has no wall)
}
