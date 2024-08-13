package org.cis1200.minesweeper;

import java.io.*;

public class Minesweeper {

    private Tile[][] board;
    private int numTurns;
    private boolean gameOver;
    private final int numOfMines;
    private final int height;
    private final int width;
    private int flags;
    private boolean won;
    private BufferedReader br;
    private BufferedWriter bw;

    /**
     * Constructor sets up game state.
     */
    public Minesweeper() {
        numOfMines = 20;
        height = 10;
        width = 16;

        reset();
    }

    public Minesweeper(int mines, int h, int w) {
        numOfMines = mines;
        height = h;
        width = w;

        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a tile that is already
     * revealed or flagged, or after the game has ended. If the turn is successful,
     * then the tile is revealed. If the tile revealed is a mine, the game ends.
     * Otherwise, the game continues.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(int r, int c) {
        if (board[r][c].isRevealed()
                || gameOver
                || board[r][c].isFlagged()) {
            return false;
        }
        numTurns++;
        revealTiles(r, c);
        if (board[r][c].isMine()) {
            gameOver = true;
            won = false;
        }
        if (checkWinner()) {
            gameOver = true;
            won = true;
        }
        return true;
    }

    // recursive function that reveals tiles
    // if the tile revealed has no adj mines to it, the function is called again
    // until
    // the tile has mines, is already revealed, or is flagged
    public void revealTiles(int r, int c) {
        if (board[r][c].getNumOfMines() == 0 && !board[r][c].isRevealed()
                && !board[r][c].isFlagged()) {
            board[r][c].reveal();
            for (int i = Math.max(r - 1, 0); i < Math.min(r + 1 + 1, height); i++) {
                for (int j = Math.max(c - 1, 0); j < Math.min(c + 1 + 1, width); j++) {
                    revealTiles(i, j);
                }
            }
        } else if (board[r][c].getNumOfMines() != 0 && !board[r][c].isFlagged()) {
            board[r][c].reveal();
        }
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     *
     * @return true if the player has won, false otherwise.
     */
    public boolean checkWinner() {
        int tileCount = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].isRevealed() && !board[r][c].isMine()) {
                    tileCount++;
                }
            }
        }
        // the board has dimensions 10x16 and there are 20 mines.
        return tileCount == height * width - numOfMines;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < width - 1) {
                    System.out.print(" | ");
                }
            }
            if (i < height - 1) {
                System.out.println("\n-------------------------------------");
            }
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Tile();
            }
        }

        numTurns = 0;
        gameOver = false;
        won = false;
        flags = 0;

        for (int i = 0; i < numOfMines; i++) {
            boolean valid = false;
            while (!valid) {
                int tileRow = (int) (Math.random() * height);
                int tileCol = (int) (Math.random() * width);
                if (!board[tileRow][tileCol].isMine()) {
                    board[tileRow][tileCol].makeMine();
                    valid = true;
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!board[i][j].isMine()) {
                    board[i][j].setNumOfMines(countMines(i, j));
                }
            }
        }

    }

    // counts the adjacent mines for the given tile
    // the bounds of the loops accounts for edge cases
    public int countMines(int row, int col) {
        int count = 0;

        for (int i = Math.max(row - 1, 0); i < Math.min(row + 1 + 1, height); i++) {
            for (int j = Math.max(col - 1, 0); j < Math.min(col + 1 + 1, width); j++) {
                if (board[i][j].isMine()) {
                    count++;
                }
            }
        }
        if (board[row][col].isMine()) {
            count--;
        }

        return count;

    }

    public int[][] mineLocations() {
        int[][] result = new int[numOfMines][];

        int count = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].isMine()) {
                    int[] arr = { r, c };
                    result[count] = arr;
                    count++;
                }
            }
        }
        return result;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return a Tile denoting the contents of the corresponding cell on the
     *         game board.
     */
    public Tile getCell(int r, int c) {
        return board[r][c];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean won() {
        return won;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void flagCell(int r, int c) {
        board[r][c].flag();
        flags++;
    }

    public void unflagCell(int r, int c) {
        board[r][c].setFlagged(false);
        flags--;
    }

    public int getFlags() {
        return flags;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void readFile(String filePath) throws IOException {
        reset();
        if (filePath == null) {
            throw new IllegalArgumentException("Filepath is null");
        }
        try {
            File file = new File(filePath);
            br = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new FileNotFoundException("File does not exist");
        }

        try {
            // reads the file and updates the board with the number of mines adj to each
            // tile
            for (int r = 0; r < board.length; r++) {
                String row = br.readLine();
                String[] nums = row.split(" ");
                for (int c = 0; c < nums.length; c++) {
                    if (Integer.parseInt(nums[c]) == -1) {
                        board[r][c].makeMine();
                    } else {
                        board[r][c].setMine(false);
                        board[r][c].setNumOfMines(Integer.parseInt(nums[c]));
                    }
                }
            }

            // reads the file and updates board with whether the tile is flagged for each
            // tile
            // 1 means the tile is flagged
            // 0 means the tile is not flagged
            for (int r = 0; r < board.length; r++) {
                String row = br.readLine();
                String[] nums = row.split(" ");
                for (int c = 0; c < nums.length; c++) {
                    if (Integer.parseInt(nums[c]) == 1) {
                        board[r][c].flag();
                        flags++;
                    }
                }
            }

            // reads the file and updates board with whether the tile is revealed for each
            // tile
            // 1 means the tile is revealed
            // 0 means the tile is not revealed
            for (int r = 0; r < board.length; r++) {
                String row = br.readLine();
                String[] nums = row.split(" ");
                for (int c = 0; c < nums.length; c++) {
                    if (Integer.parseInt(nums[c]) == 1) {
                        board[r][c].reveal();
                    } else {
                        board[r][c].setRevealed(false);
                    }
                }
            }
            br.close();

            if (checkWinner()) {
                gameOver = true;
                won = true;
            }

            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (board[r][c].isMine() && board[r][c].isRevealed()) {
                        gameOver = true;
                        won = false;
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new IOException("Incorrect formatting");
        }
    }

    public void writeFile(String filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("Filepath is null");
        }
        try {
            File file = new File(filePath);
            bw = new BufferedWriter(new FileWriter(file, false));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new FileNotFoundException("File does not exist.");
        }

        try {
            // writes the board to the file with the number of adj mines for each tile
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    bw.write(Integer.toString(board[r][c].getNumOfMines()));
                    bw.write(" ");
                }
                bw.newLine();
            }

            // writes the board to the file with whether the tile is flagged for each tile
            // 1 means the tile is flagged
            // 0 means the tile is not flagged
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (board[r][c].isFlagged()) {
                        bw.write("1 ");
                    } else {
                        bw.write("0 ");
                    }
                }
                bw.newLine();
            }

            // writes the board to the file with whether the tile is revealed for each tile
            // 1 means the tile is revealed
            // 0 means the tile is not revealed
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (board[r][c].isRevealed()) {
                        bw.write("1 ");
                    } else {
                        bw.write("0 ");
                    }
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new IOException("Incorrect formatting.");
        }

    }

    public static void main(String[] args) {

        int mines = 20;
        int rows = 10;
        int cols = 16;

        Minesweeper m = new Minesweeper(mines, rows, cols);

        m.flagCell(5, 5);
        m.flagCell(5, 8);
        m.flagCell(2, 3);
        m.flagCell(4, 9);

        while (!m.isGameOver()) {
            boolean valid = false;
            while (!valid) {
                int tileRow = (int) (Math.random() * rows);
                int tileCol = (int) (Math.random() * cols);
                if (m.playTurn(tileRow, tileCol)) {
                    valid = true;
                }
            }
            m.printGameState();
        }

        System.out.println();
        System.out.println();
        if (m.won()) {
            System.out.println("You won!");
        } else {
            System.out.println("You lost!");
        }

    }
}
