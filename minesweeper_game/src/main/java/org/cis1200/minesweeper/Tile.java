package org.cis1200.minesweeper;

public class Tile {
    /*
     * The tile class has three parameters.
     * - revealed boolean: true if the tile is revealed, false otherwise.
     * - mine boolean: true if the tile is a mine, false otherwise.
     * - numOfMines int: represents the number of mines adjacent to the tile.
     * if the tile is a mine, numOfMines is -1.
     * - flagged boolean: true if the tile is flagged, false otherwise.
     */
    private boolean revealed;
    private boolean mine;
    private int numOfMines;
    private boolean flagged;

    public Tile() {
        revealed = false;
        mine = false;
        numOfMines = 0;
        flagged = false;
    }

    public Tile(boolean revealed, boolean mine, int numOfMines, boolean flagged) {
        this.revealed = revealed;
        this.mine = mine;
        this.numOfMines = numOfMines;
        this.flagged = flagged;
    }

    public void reveal() {
        revealed = true;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void makeMine() {
        mine = true;
        numOfMines = -1;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isMine() {
        return mine;
    }

    public void setNumOfMines(int numOfMines) {
        // System.out.println("Set tile to have " + numOfMines + " mines");

        this.numOfMines = numOfMines;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void flag() {
        flagged = true;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    @Override
    public String toString() {
        if (flagged) {
            return "<";
        } else if (!revealed) {
            return ".";
        } else if (!mine) {
            return Integer.toString(numOfMines);
        } else {
            return "-1";
        }
    }
}
