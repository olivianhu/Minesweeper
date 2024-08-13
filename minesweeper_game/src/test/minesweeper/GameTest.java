package org.cis1200.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    int mines = 10;
    int rows = 8;
    int cols = 10;

    Minesweeper m = new Minesweeper(mines, rows, cols);
    int[][] mLocations = m.mineLocations();

    @Test
    public void testPlayTurnFlagged() {
        m.flagCell(2, 2);
        assertFalse(m.playTurn(2, 2));
    }

    @Test
    public void testCheckWinner() {
        for (int r = 0; r < m.getHeight(); r++) {
            for (int c = 0; c < m.getWidth(); c++) {
                int count = 0;
                for (int i = 0; i < 10; i++) {
                    if (r != mLocations[i][0] || c != mLocations[i][1]) {
                        count++;
                    }
                }
                if (count == 10) {
                    m.playTurn(r, c);
                }
            }
        }
        assertTrue(m.isGameOver());
        assertTrue(m.won());
    }

    @Test
    public void testPlayTurnGameOver() {
        m.setGameOver(true);
        assertFalse(m.playTurn(2, 2));
    }

    @Test
    public void testPlayTurnReveal() {
        m.revealTiles(2, 2);
        assertFalse(m.playTurn(2, 2));
    }

    @Test
    public void testPlayTurnMineGameOver() {
        m.playTurn(mLocations[0][0], mLocations[0][1]);
        assertTrue(m.isGameOver());
    }

    @Test
    public void testCountMinesRemove() {
        m.getCell(4, 4).setMine(true);
        int expected = m.countMines(4, 3);
        m.getCell(4, 4).setMine(false);
        assertEquals(expected - 1, m.countMines(4, 3));
    }

    @Test
    public void testCountMinesTopLeftCorner() {
        m.getCell(0, 1).setMine(true);
        m.getCell(1, 1).setMine(false);
        m.getCell(1, 0).setMine(true);
        m.getCell(0, 0).setMine(false);
        int actual = m.countMines(0, 0);
        assertEquals(2, actual);
    }

    @Test
    public void testCountMinesRightEdge() {
        m.getCell(3, 9).setMine(true);
        m.getCell(3, 8).setMine(false);
        m.getCell(4, 8).setMine(true);
        m.getCell(5, 8).setMine(true);
        m.getCell(5, 9).setMine(false);
        m.getCell(4, 9).setMine(false);
        int actual = m.countMines(4, 9);
        assertEquals(3, actual);
    }

    @Test
    public void testPlayTurnWon() {
        m.playTurn(mLocations[0][0], mLocations[0][1]);
        assertTrue(m.isGameOver());
        assertFalse(m.won());
    }

}
