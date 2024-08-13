package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;

public class Instructions extends JPanel {
    public Instructions() {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, 380, 300);
        g.setColor(Color.black);
        g.drawString("Welcome to Minesweeper!", 50, 50);
        g.drawString("Minesweeper is a game where you reveal and", 50, 70);
        g.drawString("flag tiles to find all the mines in a board.", 50, 85);
        g.drawString("When you reveal a tile, it can either show", 50, 100);
        g.drawString("a mine or the number of mines that it is ", 50, 115);
        g.drawString("adjacent to. Use these numbers to flag ", 50, 130);
        g.drawString("squares that are mines. ", 50, 145);

        g.drawString("You win if you reveal all the tiles that ", 50, 165);
        g.drawString("are not mines. You lose if you click on", 50, 180);
        g.drawString("a mine.", 50, 195);

        g.drawString("Left click to reveal a tile.", 50, 215);
        g.drawString("Right click to flag a tile.", 50, 230);

        g.drawString("Have fun!", 50, 250);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(380, 300);
    }
}
