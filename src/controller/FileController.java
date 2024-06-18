// Contributors: Wong Wei Ping, Low Kai Yan
// Purpose of this class: To implement the save and load function of the chess game

package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.HourGlass;
import model.Piece;
import model.Plus;
import model.Point;
import model.Sun;
import model.Time;
import view.GamePanel;

public class FileController {
    
    // Contributed by Wong Wei Ping, Low Kai Yan
    // Save the pieces to a txt file
    public void savePiecesToFile(File file, ArrayList<Piece> pieces, int currentColor, int turn) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(currentColor);
            writer.println(turn);
            for (Piece piece : pieces) {
                writer.println(piece.toString()); // Assuming toString provides the necessary format
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Contributed by Wong Wei Ping
    // Load the pieces from a txt file
    public void loadPiecesFromFile(File file) {
        ArrayList<Piece> pieces = new ArrayList<>(); // Create a new list to store loaded pieces
        int currentColor = 0; // Initialize currentColor variable
        int turn = 0; // Initialize turn variable

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Read the first line to get the current color
            String colorLine = reader.readLine();
            if (colorLine != null) {
                currentColor = Integer.parseInt(colorLine.trim());
            }

            String turnLine = reader.readLine();
            if (turnLine != null) {
                turn = Integer.parseInt(turnLine.trim());
            }

            // Read the remaining lines for piece details
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into tokens (assuming space-separated values)
                String[] tokens = line.split(" ");

                // Parse the tokens into the piece details
                String className = tokens[0];
                int color = Integer.parseInt(tokens[1]);
                int col = Integer.parseInt(tokens[2]);
                int row = Integer.parseInt(tokens[3]);
                boolean flip = Boolean.parseBoolean(tokens[4]);

                // Create a new Piece and add it to the pieces array
                Piece piece = createPieceInstance(className, color, col, row, flip);

                if (piece != null) {
                    // Add the created piece to the pieces array
                    pieces.add(piece);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Update whole game state of GamePanel
        GamePanel.gamePieces.clear();
        GamePanel.gamePieces.addAll(pieces);
        GamePanel.simulatedPieces.clear();
        GamePanel.simulatedPieces.addAll(pieces);
        GamePanel.selectedPiece = null;
        GamePanel.currentColor = currentColor;
        GamePanel.turn = turn;
        GamePanel.turnLabel.setText((currentColor == GamePanel.YELLOW) ? "Yellow's Turn" : "Blue's Turn");
    }

    //Contributed by Low Kai Yan
    // Helper method to create an instance of a specific Piece subclass
    public Piece createPieceInstance(String className, int color, int col, int row, boolean flip) {
        switch (className) {
            case "Point":
                return new Point(color, col, row, flip);
            case "Plus":
                return new Plus(color, col, row, flip);
            case "Time":
                return new Time(color, col, row, flip);
            case "Sun":
                return new Sun(color, col, row, flip);
            case "HourGlass":
                return new HourGlass(color, col, row, flip);
            default:
                // Handle unknown piece type or throw an exception
                return null;
        }
    }
    
}
