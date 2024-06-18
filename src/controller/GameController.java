// Contributors: Low Kai Yan
// Purpose of this class: To control the game logic and update the game state when the mouse is clicked or released

package controller;

import view.GamePanel;
import model.Board;
import model.Piece;
import model.Sun;
import model.Time;
import model.Plus;

import javax.swing.JOptionPane;

public class GameController {
    private GamePanel gamePanel;
    private Thread gameThread;

    public GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void startGame() {
        gameThread = new Thread(gamePanel);
        gameThread.start();
    }


    // Contributed by Low Kai Yan
    // Update Method For Piece When Mouse Clicked
    public void update() {
        // When Mouse Clicked
        if (gamePanel.mouse.pressed) {
            if (GamePanel.selectedPiece == null) {
                for (Piece piece : GamePanel.simulatedPieces) {
                    if (piece.color == GamePanel.currentColor && piece.col == gamePanel.mouse.x / Board.SQUARE_SIZE
                            && piece.row == gamePanel.mouse.y / Board.SQUARE_SIZE) {
                        GamePanel.selectedPiece = piece;
                        break;
                    }
                }
            } else {
                simulate();
            }
        }
        // When Mouse Released
        if (!gamePanel.mouse.pressed) {
            if (GamePanel.selectedPiece != null) {
                if (gamePanel.isValidTile) {
                    GamePanel.turn++;

                    // Every 4 moves, the pieces will change
                    if(GamePanel.turn % 4 == 0) {
                        // if simulated pieces is Plus, it will turn into Times pieces, and vice versa
                        for (Piece piece : GamePanel.simulatedPieces) {
                            FileController fileController = new FileController();
                            if (piece instanceof Plus) {
                                int index = piece.getIndex();
                                GamePanel.simulatedPieces.set(index, fileController.createPieceInstance("Time", piece.color, piece.col, piece.row, piece.flip));
                                System.out.println("Plus Changed");
                            } else if (piece instanceof Time) {
                                int index = piece.getIndex();
                                GamePanel.simulatedPieces.set(index, fileController.createPieceInstance("Plus", piece.color, piece.col, piece.row, piece.flip));
                                System.out.println("Time Changed");
                            }
                        }                        
                    }

                    // DEBUG
                    System.out.println("Turn: " + GamePanel.turn);


                    gamePanel.chessModel.copyPieces(GamePanel.simulatedPieces, GamePanel.gamePieces);
                    GamePanel.selectedPiece.updatePostion();

                    // Check if the removed piece is a Sun when the mouse is released
                    if (GamePanel.selectedPiece.blockPiece != null
                            && GamePanel.selectedPiece.blockPiece instanceof Sun) {
                        gameOver();
                    } else {
                        GamePanel.selectedPiece = null;
                        changeTurn();
                    }

                } else {
                    // Reset the piece position if the move is invalid
                    gamePanel.chessModel.copyPieces(GamePanel.gamePieces, GamePanel.simulatedPieces);
                    GamePanel.selectedPiece.resetPostion();
                    GamePanel.selectedPiece = null;
                }
            }
        }
    }

    // Contributed by Low Kai Yan
    // Simulate Method For Piece When Mouse Clicked
    public void simulate() {
        gamePanel.isValidMove = false;
        gamePanel.isValidTile = false;

        gamePanel.chessModel.copyPieces(GamePanel.gamePieces, GamePanel.simulatedPieces);

        GamePanel.selectedPiece.x = gamePanel.mouse.x - Board.HALF_SQUARE_SIZE;
        GamePanel.selectedPiece.y = gamePanel.mouse.y - Board.HALF_SQUARE_SIZE;

        GamePanel.selectedPiece.col = GamePanel.selectedPiece.getColumn(GamePanel.selectedPiece.x);
        GamePanel.selectedPiece.row = GamePanel.selectedPiece.getRow(GamePanel.selectedPiece.y);

        if (GamePanel.selectedPiece.isValidMove(GamePanel.selectedPiece.col, GamePanel.selectedPiece.row)) {
            gamePanel.isValidMove = true;

            if (GamePanel.selectedPiece.blockPiece != null) {
                GamePanel.simulatedPieces.remove(GamePanel.selectedPiece.blockPiece.getIndex());
            }

            gamePanel.isValidTile = true;
        }
    }

    // Contributed by Low Kai Yan
    // Game Over
    private void gameOver() {
        System.out.println("Game Over");
        String message = (GamePanel.currentColor == GamePanel.YELLOW) ? "Yellow" : "Blue";
        message += " player wins! Game Over.";

        // Show a dialog with the winner
        JOptionPane.showMessageDialog(gamePanel, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        gamePanel.newGame();
    }


    // Contributed by Low Kai Yan
    // Change Player Turn
    private void changeTurn() {
        if (GamePanel.currentColor == GamePanel.YELLOW) {
            GamePanel.currentColor = GamePanel.BLUE;
            GamePanel.turnLabel.setText("Blue's Turn");
        } else {
            GamePanel.currentColor = GamePanel.YELLOW;
            GamePanel.turnLabel.setText("Yellow's Turn");
        }
        gamePanel.flipBoard();
        GamePanel.selectedPiece = null;
    }
}
