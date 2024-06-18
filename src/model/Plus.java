// Contributors: Nicholas Tiow Kai Bo
// Purpose of this class: To create a Plus piece with its properties (such as loadImage of pieces) and valid movement



package model;

import view.GamePanel;

public class Plus extends Piece{
    public Plus(int color, int col, int row, boolean flip) {
        super(color, col, row, flip);
        if(color == GamePanel.YELLOW) {
            image = loadImage("/resources/plus1.png");
        } else {
            image = loadImage("/resources/plus2.png");
        }
    }

    // The Plus piece can move horizontally and vertically only but can go any distance.
    // It cannot skip over other pieces.
    @Override
    public boolean isValidMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && hasPieceInLine(targetCol, targetRow) == false) {
            int colDiff = Math.abs(targetCol - preCol);
            int rowDiff = Math.abs(targetRow - preRow);
        
            if (isValidTile(targetCol, targetRow)) {
                if ((colDiff == 0 && rowDiff > 0) || (colDiff > 0 && rowDiff == 0)) {
                    int colDirection = 0;
                    int rowDirection = 0;
                
                    // Check if colDiff is not zero to avoid division by zero
                    if (colDiff != 0) {
                        colDirection = (targetCol - preCol) / colDiff;
                    }
                
                    // Check if rowDiff is not zero to avoid division by zero
                    if (rowDiff != 0) {
                        rowDirection = (targetRow - preRow) / rowDiff;
                    }
                
                    for (int i = 1; i < Math.max(colDiff, rowDiff); i++) {
                        int checkCol = preCol + i * colDirection;
                        int checkRow = preRow + i * rowDirection;
                    
                        if (isPieceAt(checkCol, checkRow) != null) {
                            return false; // There is a piece in the path
                        }
                    }
                
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %b", getClass().getSimpleName() ,color, col, row, flip);
    }

}

