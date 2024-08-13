package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class instantiates a Minesweeper object, which is the model for the
 * game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private final Minesweeper ms; // model for the game
    private final TileObj[][] board;
    private final JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 480;
    public static final int BOARD_HEIGHT = 300;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ms = new Minesweeper(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        board = new TileObj[ms.getHeight()][ms.getWidth()];

        for (int i = 0; i < ms.getHeight(); i++) {
            for (int j = 0; j < ms.getWidth(); j++) {
                board[i][j] = new TileObj(
                        j * 30, i * 30, 30,
                        ms.getCell(i, j).getNumOfMines()
                );
            }
        }

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // if the click was a left click, reveal the tile
                // if the click was a right click, flag the tile
                if (p.y / 30 < board.length && p.x / 30 < board[0].length) {
                    if (e.getButton() == 1) {
                        if (ms.playTurn(p.y / 30, p.x / 30)) {
                            for (int r = 0; r < board.length; r++) {
                                for (int c = 0; c < board[0].length; c++) {
                                    if (ms.getCell(r, c).isRevealed()) {
                                        board[r][c].reveal();
                                    }
                                }
                            }
                        }
                    } else if (e.getButton() == 3) {
                        if (!ms.isGameOver()) {
                            if (!ms.getCell(p.y / 30, p.x / 30).isFlagged()) {
                                ms.flagCell(p.y / 30, p.x / 30);
                            } else {
                                ms.unflagCell(p.y / 30, p.x / 30);
                            }

                            board[p.y / 30][p.x / 30].changeFlag();
                        }
                    }
                }

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ms.reset();
        for (int i = 0; i < ms.getHeight(); i++) {
            for (int j = 0; j < ms.getWidth(); j++) {
                board[i][j] = new TileObj(
                        j * 30, i * 30, 30,
                        ms.getCell(i, j).getNumOfMines()
                );
            }
        }
        status.setText("Remaining Mines: " + (ms.getNumOfMines() - ms.getFlags()));
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void save() {
        try {
            ms.writeFile("files/GameSave");
        } catch (IllegalArgumentException | IOException e) {
            status.setText("An error occurred");
        }
    }

    public void load() {
        try {
            ms.readFile("files/GameSave");
            status.setText("Remaining Mines: " + (ms.getNumOfMines() - ms.getFlags()));
            for (int i = 0; i < ms.getHeight(); i++) {
                for (int j = 0; j < ms.getWidth(); j++) {
                    if (ms.getCell(i, j).isMine()) {
                        board[i][j].setMine(true);
                    } else {
                        board[i][j] = new TileObj(
                                j * 30, i * 30, 30,
                                ms.getCell(i, j).getNumOfMines()
                        );
                    }
                    if (ms.getCell(i, j).isRevealed()) {
                        board[i][j].reveal();
                    } else {
                        board[i][j].unreveal();
                    }
                    if (ms.getCell(i, j).isFlagged()) {
                        board[i][j].flag();
                    } else {
                        board[i][j].unflag();
                    }
                }
            }

            repaint();
            updateStatus();
            requestFocusInWindow();
        } catch (IllegalArgumentException | IOException e) {
            status.setText("An error occurred");
        }
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {

        boolean won = ms.won();
        boolean gameOver = ms.isGameOver();
        if (gameOver) {
            if (won) {
                status.setText("You won!");
            } else {
                status.setText("You lost!");
            }
        } else {
            status.setText("Remaining Mines: " + (ms.getNumOfMines() - ms.getFlags()));
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c].draw(g);
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
