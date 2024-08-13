package org.cis1200.minesweeper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileObj {
    private String imgFile;
    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private int px;
    private int py;

    /* Size of object, in pixels. */
    private final int size;
    private final int number;
    private boolean mine;
    private boolean flagged;
    private boolean revealed;
    /*
     * Upper bounds of the area in which the object can be positioned. Maximum
     * permissible x, y positions for the upper-left hand corner of the object.
     */

    private BufferedImage img;

    public TileObj(int px, int py, int size, int number) {
        this.px = px;
        this.py = py;
        this.size = size;
        this.number = number;
        flagged = false;
        revealed = false;

        mine = number == -1;

        imgFile = "files/ms unopened.svg.png";

        try {
            img = ImageIO.read(new File(imgFile));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public int getSize() {
        return size;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    private void reset() {
        try {
            img = ImageIO.read(new File(imgFile));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public void reveal() {
        revealed = true;
        if (mine) {
            imgFile = "files/ms mine.png";
        } else {
            setImage();
        }
        reset();
    }

    public void unreveal() {
        revealed = false;
        imgFile = "files/ms unopened.svg.png";
        reset();
    }

    public void changeFlag() {
        if (!flagged) {
            imgFile = "files/ms flag.png";
        } else {
            imgFile = "files/ms unopened.svg.png";
        }
        flagged = !flagged;
        reset();
    }

    public void flag() {
        flagged = true;
        imgFile = "files/ms flag.png";

        reset();
    }

    public void unflag() {
        flagged = false;
        if (revealed) {
            reveal();
        } else {
            imgFile = "files/ms unopened.svg.png";
        }
        reset();
    }

    public void setImage() {
        if (number == 0) {
            imgFile = "files/mine 0.png";
        } else if (number == 1) {
            imgFile = "files/mine 1.png";
        } else if (number == 2) {
            imgFile = "files/mine 2.png";
        } else if (number == 3) {
            imgFile = "files/mine 3.png";
        } else if (number == 4) {
            imgFile = "files/mine 4.png";
        } else if (number == 5) {
            imgFile = "files/mine 5.png";
        } else if (number == 6) {
            imgFile = "files/mine 6.png";
        } else if (number == 7) {
            imgFile = "files/mine 7.png";
        } else {
            imgFile = "files/mine 8.png";
        }
    }

    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getSize(), this.getSize(), null);
    }
}
