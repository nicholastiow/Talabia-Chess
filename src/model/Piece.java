// Contributors: Low Kai Yan, Nicholas Tiow Kai Bo
// Purpose of this class: To initialize basic functions for all types of pieces 

package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import view.GamePanel;

public abstract class Piece {
    public BufferedImage image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public boolean flip;
    public Piece blockPiece;

    public Piece(int color, int col, int row, boolean flip) {
        this.color = color;
        this.col = col;
        this.row = row;
        this.flip = flip;

        x = getX(col);
        y = getY(row);

        preCol = col;
        preRow = row;
    }

    // Contributed by Low Kai Yan
    // Load image
    public BufferedImage loadImage(String imagePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(imagePath));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    public int getX(int col) {
        return col * Board.SQUARE_SIZE;
    }

    public int getY(int row) {
        return row * Board.SQUARE_SIZE;
    }

    public int getColumn(int x) {
        return (x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public int getRow(int y) {
        return (y + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public int getIndex() {
        return GamePanel.simulatedPieces.indexOf(this);
    }

    // Need HALF_SQUARE_SIZE to center the piece
    public void setColunm(int x) {
        col = (x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public void setRow(int y) {
        row = (y + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    
    
    // Update the latest piece position
    public void updatePostion() {
        x = getX(col);
        y = getY(row);

        preCol = getColumn(x);
        preRow = getRow(y);
    }

    // Reset the piece position to the previous position
    public void resetPostion() {
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }


    // Contributed by Low Kai Yan
    // Check if the target position is within the board
    public boolean isWithinBoard(int targetCol, int targetRow) {
        return targetCol >= 0 && targetCol < Board.MAX_COL && targetRow >= 0 && targetRow < Board.MAX_ROW;
    }
    


    // Contributed by Nicholas Tiow Kai Bo
    // Check if there is any piece at the target position
    public Piece isPieceAt(int targetCol, int targetRow) {
        for(Piece piece : GamePanel.simulatedPieces) {
            if(piece.col == targetCol && piece.row == targetRow && piece != this) {
                return piece;
            }
        }
        return null;
    }

    // Contributed by Nicholas Tiow Kai Bo
    // Check if there is any piece in a straight line between the current position and the target position
    public boolean hasPieceInLine(int targetCol, int targetRow) {
        int colDiff = targetCol - preCol;
        int rowDiff = targetRow - preRow;
    
        int colDirection = Integer.compare(colDiff, 0);
        int rowDirection = Integer.compare(rowDiff, 0);
    
        for (int i = 1; i < Math.max(Math.abs(colDiff), Math.abs(rowDiff)); i++) {
            int checkCol = preCol + i * colDirection;
            int checkRow = preRow + i * rowDirection;
    
            for (Piece piece : GamePanel.simulatedPieces) {
                if (piece.col == checkCol && piece.row == checkRow) {
                    blockPiece = piece;
                    return true;
                }
            }
        }
    
        return false;
    }
    

    public boolean isValidMove(int targetCol, int targetRow) {
        return false;
    }

    // Contributed by Nicholas Tiow Kai Bo
    // Check if the target position is a valid tile and the color is different
    public boolean isValidTile(int targetCol, int targetRow) {
        blockPiece = isPieceAt(targetCol, targetRow);
        return blockPiece == null || blockPiece.color != this.color;
    }
    
    // Contributed by Low Kai Yan
    // Draw the piece
    public void draw(Graphics2D g) {
        int pieceSize = 70; // Adjust size as needed
        int xOffset = (Board.SQUARE_SIZE - pieceSize) / 2;
        int yOffset = (Board.SQUARE_SIZE - pieceSize) / 2;
    
        g.drawImage(image, x + xOffset, y + yOffset, pieceSize, pieceSize, null);
    }

    public abstract String toString();
    
}
