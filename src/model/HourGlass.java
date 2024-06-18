// Contributors: Nicholas Tiow Kai Bo
// Purpose of this class: To create a HourGlass piece with its properties (such as loadImage of pieces) and valid movement

package model;

import view.GamePanel;

public class HourGlass extends Piece{
    public HourGlass(int color, int col, int row, boolean flip) {
        super(color, col, row, flip);
        if(color == GamePanel.YELLOW) {
            image = loadImage("/resources/hourglass1.png");
        } else {
            image = loadImage("/resources/hourglass2.png");
        }
    }


    // The Hourglass piece moves in a 3x2 L shape in any orientation 
    // (kind of like the knight in standard chess.)
    //This is the only piece that can skip over other pieces.
    @Override
    public boolean isValidMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            int colDiff = Math.abs(targetCol - preCol);
            int rowDiff = Math.abs(targetRow - preRow);

            if (isValidTile(targetCol, targetRow)) {
                return (colDiff == 1 && rowDiff == 2) || (colDiff == 2 && rowDiff == 1);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %b", getClass().getSimpleName() ,color, col, row, flip);
    }
}
