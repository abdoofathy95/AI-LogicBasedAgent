package maze;

import maze.MazeState.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public final class Maze implements IMaze {
    private Cell[][] cells;
    private Random random = new Random();
    private int gridWidth;
    private int gridHeight;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private BitSet pokemons;
    private int numberOfAvailablePokemons;
    private int distanceLeftToHatch;
    private Direction initDirection;
    private List<Location> pokemonsByIndex;
    private HashMap<Location, Integer> pokemonsByLocation;
    
    private static Maze instance;
    
    
    public static Maze getMaze(){
    	if(instance == null){
    		instance = new Maze();
    	}
    	return instance;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getNumberOfAvailablePokemons() {
        return numberOfAvailablePokemons;
    }
    
    public BitSet getPokemonsBitSet(){
    	return pokemons;
    }
    

    public void setNumberOfAvailablePokemons(int numberOfAvailablePokemons) {
        this.numberOfAvailablePokemons = numberOfAvailablePokemons;
    }

    @Override
    public Direction getInitialDirection() {
        return initDirection;
    }

    @Override
    public int getDistanceLeftToHatch() {
        return distanceLeftToHatch;
    }

    @Override
    public boolean isWayOpen(int x, int y, Direction direction) {
        switch (direction) {
            case WEST: {
                return cells[y][x].isLeftOpen();
            }
            case NORTH: {
                return cells[y][x].isUpOpen();
            }
            case EAST: {
                return cells[y][x].isRightOpen();
            }
            case SOUTH: {
                return cells[y][x].isDownOpen();
            }
        }
        return false;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    @Override
    public boolean isCellHasPokemon(int x, int y) {
        return cells[y][x].isHasPokemon();
    }

    public BitSet getPokemons() {
        return pokemons;
    }

    public Maze() {
        pokemonsByIndex = new ArrayList<Location>();
        pokemonsByLocation = new HashMap<Location, Integer>();
        genMaze();
        printMaze();
        System.out.println("Number of Available Pokemons: " + numberOfAvailablePokemons);
    }

    //Playground for testing a specific Maze
    public void genMazeWithFixedShape() {
        gridWidth = 2;
        gridHeight = 2;
        startY = 0;
        startX = 1;

        endY = 0;
        endX = 0;

        pokemons = new BitSet();
        distanceLeftToHatch = 1;

        initDirection = Direction.WEST;

        cells = new Cell[gridHeight][gridWidth];

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
            	boolean hasPokemon = i == 1 && j == 1;
                //boolean hasPokemon = (Math.abs(random.nextInt()) % 2 == 1);
                cells[i][j] = new Cell();
                if (!(i == startY && j == startX) && !(i == endY && j == endX) && hasPokemon) {
                    pokemons.set(numberOfAvailablePokemons);
                    Location temp = new Location(i, j);
                    pokemonsByIndex.add(temp);
                    pokemonsByLocation.put(temp, numberOfAvailablePokemons++);
                    cells[i][j].setHasPokemon(true);
                }
            }
        }

        cells[0][0].setDownOpen(true);
        cells[1][0].setUpOpen(true);
        cells[1][0].setRightOpen(true);
        cells[1][1].setUpOpen(true);
        cells[1][1].setLeftOpen(true);
        cells[0][1].setDownOpen(true);
    }

    @Override
    public void genMaze() {
        pokemons = new BitSet();
        gridWidth = 3;
        gridHeight = 3;

        while (startX == endX && startY == endY) {
            startX = Math.abs(random.nextInt()) % gridWidth;
            startY = Math.abs(random.nextInt()) % gridHeight;

            endX = Math.abs(random.nextInt()) % gridWidth;
            endY = Math.abs(random.nextInt()) % gridHeight;
        }

        distanceLeftToHatch = 5;//Math.abs(random.nextInt()) % (gridWidth * gridHeight);

        //numOfPokemonsLeft = 1;
        // if set to < actual number or > would result in infinite loop since goaltest
        // checks only if numOfPokemonsLeft == 0 not <= 0

        switch (Math.abs(random.nextInt()) % 4) {
            case 0: {
                initDirection = Direction.WEST;
                break;
            }
            case 1: {
                initDirection = Direction.NORTH;
                break;
            }
            case 2: {
                initDirection = Direction.EAST;
                break;
            }
            case 3: {
                initDirection = Direction.SOUTH;
                break;
            }
        }

        cells = new Cell[gridHeight][gridWidth];
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                boolean hasPokemon = (Math.abs(random.nextInt()) % 2 == 1);
                cells[i][j] = new Cell();
                if (!(i == startY && j == startX) && !(i == endY && j == endX) && hasPokemon) {
                    pokemons.set(numberOfAvailablePokemons);
                    Location temp = new Location(i, j);
                    pokemonsByIndex.add(temp);
                    pokemonsByLocation.put(temp, numberOfAvailablePokemons++);
                    cells[i][j].setHasPokemon(true);
                }
            }
        }

        int row = 0;
        int col = 0;

        ArrayList<Location> locationTrace = new ArrayList<>();
        locationTrace.add(new Location(row, col));

        while (locationTrace.size() > 0) {
            cells[row][col].setVisited(true);

            ArrayList<Character> validMoves = new ArrayList<>();
            if (col > 0 && !cells[row][col - 1].isVisited()) {
                validMoves.add('L');
            }
            if (row > 0 && !cells[row - 1][col].isVisited()) {
                validMoves.add('U');
            }
            if (col < gridWidth - 1 && !cells[row][col + 1].isVisited()) {
                validMoves.add('R');
            }
            if (row < gridHeight - 1 && !cells[row + 1][col].isVisited()) {
                validMoves.add('D');
            }

            if (validMoves.size() > 0) {
                locationTrace.add(new Location(row, col));

                int rnd = (Math.abs(random.nextInt()) % validMoves.size());
                char chosenDirection = validMoves.get(rnd);

                switch (chosenDirection) {
                    case 'L': {
                        cells[row][col].setLeftOpen(true);
                        col--;
                        cells[row][col].setRightOpen(true);
                        break;
                    }
                    case 'U': {
                        cells[row][col].setUpOpen(true);
                        row--;
                        cells[row][col].setDownOpen(true);
                        break;
                    }
                    case 'R': {
                        cells[row][col].setRightOpen(true);
                        col++;
                        cells[row][col].setLeftOpen(true);
                        break;
                    }
                    case 'D': {
                        cells[row][col].setDownOpen(true);
                        row++;
                        cells[row][col].setUpOpen(true);
                        break;
                    }
                }
            } else {
                Location location = locationTrace.remove(0);
                row = location.getRow();
                col = location.getCol();
            }
        }
    }

    public void genMazeWithASetNumberOfPokemons(int numberOfPokemons, int gridHeight, int gridWidth) {
    	pokemons = new BitSet(numberOfPokemons);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        distanceLeftToHatch = 5;
        numberOfAvailablePokemons = numberOfPokemons;

        while (startX == endX && startY == endY) {
            startX = Math.abs(random.nextInt()) % gridWidth;
            startY = Math.abs(random.nextInt()) % gridHeight;

            endX = Math.abs(random.nextInt()) % gridWidth;
            endY = Math.abs(random.nextInt()) % gridHeight;
        }

        switch (Math.abs(random.nextInt()) % 4) {
            case 0: {
                initDirection = Direction.WEST;
                break;
            }
            case 1: {
                initDirection = Direction.NORTH;
                break;
            }
            case 2: {
                initDirection = Direction.EAST;
                break;
            }
            case 3: {
                initDirection = Direction.SOUTH;
                break;
            }
        }
        
        int totalSize = gridWidth * gridHeight;
        List<Location> temp = new ArrayList<Location>(totalSize-2);
        cells = new Cell[gridHeight][gridWidth];
        
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
            	cells[i][j] = new Cell();
            	if (!(i == startY && j == startX) && !(i == endY && j == endX))
            		temp.add(new Location(i, j)); 
            }
        }
        
        Collections.shuffle(temp);
        
        for (int i = 0; i < numberOfPokemons; i++) {
        	pokemonsByIndex.add(temp.get(i));
        	pokemonsByLocation.put(temp.get(i), i);
        	cells[temp.get(i).getRow()][temp.get(i).getCol()].setHasPokemon(true);
        }
        
        int row = 0;
        int col = 0;

        ArrayList<Location> locationTrace = new ArrayList<>();
        locationTrace.add(new Location(row, col));

        while (locationTrace.size() > 0) {
            cells[row][col].setVisited(true);

            ArrayList<Character> validMoves = new ArrayList<>();
            if (col > 0 && !cells[row][col - 1].isVisited()) {
                validMoves.add('L');
            }
            if (row > 0 && !cells[row - 1][col].isVisited()) {
                validMoves.add('U');
            }
            if (col < gridWidth - 1 && !cells[row][col + 1].isVisited()) {
                validMoves.add('R');
            }
            if (row < gridHeight - 1 && !cells[row + 1][col].isVisited()) {
                validMoves.add('D');
            }

            if (validMoves.size() > 0) {
                locationTrace.add(new Location(row, col));

                int rnd = (Math.abs(random.nextInt()) % validMoves.size());
                char chosenDirection = validMoves.get(rnd);

                switch (chosenDirection) {
                    case 'L': {
                        cells[row][col].setLeftOpen(true);
                        col--;
                        cells[row][col].setRightOpen(true);
                        break;
                    }
                    case 'U': {
                        cells[row][col].setUpOpen(true);
                        row--;
                        cells[row][col].setDownOpen(true);
                        break;
                    }
                    case 'R': {
                        cells[row][col].setRightOpen(true);
                        col++;
                        cells[row][col].setLeftOpen(true);
                        break;
                    }
                    case 'D': {
                        cells[row][col].setDownOpen(true);
                        row++;
                        cells[row][col].setUpOpen(true);
                        break;
                    }
                }
            } else {
                Location location = locationTrace.remove(0);
                row = location.getRow();
                col = location.getCol();
            }
        }
    }

    public List<Location> getPokemonsByIndex() {
        return pokemonsByIndex;
    }

    public HashMap<Location, Integer> getPokemonsByLocation() {
        return pokemonsByLocation;
    }

    public void printMaze() {
        printMaze(null, null);
    }

    public void printMaze(Point currentAgentPointForPrint, String currentAgentOrientationForPrint) {
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if (!cells[i][j].isUpOpen()) {
                    System.out.print(" -");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
            for (int j = 0; j < gridWidth; j++) {
                if (!cells[i][j].isLeftOpen()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                if (currentAgentPointForPrint != null && currentAgentPointForPrint.x == j && currentAgentPointForPrint.y == i) {
                    System.out.print(currentAgentOrientationForPrint);
                } else if (pokemonsByLocation.containsKey(new Location(i, j))) {
                    System.out.print("P");
                } else if (startX == j && startY == i) {
                    System.out.print("S");
                } else if (endX == j && endY == i) {
                    System.out.print("E");
                } else {
                    System.out.print(" ");
                }
            }
            if (!cells[i][gridWidth - 1].isRightOpen()) {
                System.out.print("|");
            } else {
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int j = 0; j < gridWidth; j++) {
            if (!cells[gridHeight - 1][j].isDownOpen()) {
                System.out.print(" -");
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
    }
}
