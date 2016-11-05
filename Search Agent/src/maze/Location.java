package maze;

public class Location implements Comparable<Location> {
    private int row;
    private int col;

    public Location(int row, int col) {
        setRow(row);
        setCol(col);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

	@Override
	public int compareTo(Location other) {
		if(row != other.getRow())
			return row - other.getRow();
		return col - other.getCol();
	}
	
	@Override
	public boolean equals(Object other) {
		return compareTo((Location)other) == 0;
	}
	
    @Override
    public int hashCode() {
        return row;
    }
    
    @Override
    public String toString() {
    	return "("+col+","+row+")";
    }
}