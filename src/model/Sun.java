// Contributors: Nicholas Tiow Kai Bo
// Purpose of this class: To create a Sun piece with its properties (such as loadImage of pieces) and valid movement

package model;

import view.GamePanel;

public class Sun extends Piece{
    public Sun(int color, int col, int row, boolean flip) {
        super(color, col, row, flip);
        if(color == GamePanel.YELLOW) {
            image = loadImage("/resources/sun1.png");
        } else {
            image = loadImage("/resources/sun2.png");
        }
    }

     // The Sun piece can move only one step in any direction
    @Override
    public boolean isValidMove(int targetCol, int targetRow) {
        if (targetCol == preCol && targetRow == preRow) {
            return false;
        }
        if (isWithinBoard(targetCol, targetRow) && isValidTile(targetCol, targetRow)) {
            int colDiff = Math.abs(targetCol - preCol);
            int rowDiff = Math.abs(targetRow - preRow);

            return colDiff <= 1 && rowDiff <= 1;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %b", getClass().getSimpleName() ,color, col, row, flip);
    }
}
