// Contributors: Nicholas Tiow Kai Bo
// Purpose of this class: To create a Point piece with its properties (such as loadImage of pieces) and valid movement
//                        and also to flip the piece when it reaches the end of the board

package model;

import java.awt.Graphics2D;

import view.GamePanel;

public class Point extends Piece{
    // private boolean flipped = false;
    public Point(int color, int col, int row, boolean flip) {
        super(color, col, row, flip);
        updateImagePath();
    }

    // Update the image path based on the color and flip
    private void updateImagePath() {
        if (color == GamePanel.YELLOW) {
            image = loadImage(flip ? "/resources/flip_point1.png" : "/resources/point1.png"); // if flipped, use flip image
        } else {
            image = loadImage(flip ? "/resources/flip_point2.png" : "/resources/point2.png"); // if flipped, use flip image
        }
    }

    @Override
    public void draw(Graphics2D g) {
        updateImagePath();
        super.draw(g);
    }

    
    
    //The Point piece can only move forward, 1 or 2 steps. If it reaches the end of the board, it turns around and starts heading back the other way. It cannot skip over other pieces.
    public boolean isValidMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            int colDiff = Math.abs(targetCol - preCol);
            int rowDiff = targetRow - preRow;
    
            if (isValidTile(targetCol, targetRow) && hasPieceInLine(targetCol, targetRow) == false) {
                if(flip) {
                        if ((rowDiff == 1 || rowDiff == 2) && colDiff == 0) {
                            return true;
                        }
                    } else {
                        if ((rowDiff == -1 || rowDiff == -2) && colDiff == 0) {
                            return true;
                        }
                    }
            }
        }
        return false;
    }
    
    @Override
    public void updatePostion() {
        super.updatePostion();

        // Check if the piece has reached row 0(Top row) and flip if needed
        if (row == 0) {
            flip = !flip;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %b", getClass().getSimpleName() ,color, col, row, flip);
    }
}

