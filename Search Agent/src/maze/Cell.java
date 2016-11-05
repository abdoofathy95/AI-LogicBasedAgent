package maze;

class Cell {
    private boolean valid;
    private boolean leftOpen = false;
    private boolean upOpen = false;
    private boolean rightOpen = false;
    private boolean downOpen = false;
    private boolean hasPokemon = false;
    private boolean visited = false;

    Cell() {
        this.setValid(valid);
    }

    void setValid(boolean valid) {
        this.valid = valid;
    }

    boolean isLeftOpen() {
        return leftOpen;
    }

    void setLeftOpen(boolean leftOpen) {
        this.leftOpen = leftOpen;
    }

    boolean isUpOpen() {
        return upOpen;
    }

    void setUpOpen(boolean upOpen) {
        this.upOpen = upOpen;
    }

    boolean isRightOpen() {
        return rightOpen;
    }

    void setRightOpen(boolean rightOpen) {
        this.rightOpen = rightOpen;
    }

    boolean isDownOpen() {
        return downOpen;
    }

    void setDownOpen(boolean downOpen) {
        this.downOpen = downOpen;
    }

    boolean isVisited() {
        return visited;
    }

    void setVisited(boolean visited) {
        this.visited = visited;
    }

	public boolean isHasPokemon() {
		return hasPokemon;
	}

	public void setHasPokemon(boolean hasPokemon) {
		this.hasPokemon = hasPokemon;
	}
}
