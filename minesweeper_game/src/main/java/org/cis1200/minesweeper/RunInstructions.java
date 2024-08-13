package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;

public class RunInstructions implements Runnable {
    public void run() {
        // Top-level frame in which components live
        final JFrame frame = new JFrame("Instructions");
        frame.setLocation(500, 400);

        final Instructions instructions = new Instructions();
        frame.add(instructions);

        // Put the frame on the screen
        frame.pack();
        frame.setVisible(true);

    }
}