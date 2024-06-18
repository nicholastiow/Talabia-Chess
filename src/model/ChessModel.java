// Contributors: Wong Jui Hong
// Purpose of this class: To initialize the position of the pieces on the board

package model;

import java.util.ArrayList;

import view.GamePanel;

public class ChessModel {
    private ArrayList<Piece> pieces;
    private ArrayList<Piece> simPieces;
    private Piece selectedPiece;

    public ChessModel() {
        pieces = new ArrayList<Piece>();
        simPieces = new ArrayList<Piece>();
        initPieces();
        copyPieces(pieces, simPieces);
    }

    // Contributed by Wong Jui Hong
    // Initialize the pieces positions on the board
    public void initPieces() {
        pieces.add(new Point(GamePanel.BLUE, 0, 1, true));
        pieces.add(new Point(GamePanel.BLUE, 1, 1, true));
        pieces.add(new Point(GamePanel.BLUE, 2, 1, true));
        pieces.add(new Point(GamePanel.BLUE,3, 1, true));
        pieces.add(new Point(GamePanel.BLUE, 4, 1, true));
        pieces.add(new Point(GamePanel.BLUE, 5, 1, true));
        pieces.add(new Point(GamePanel.BLUE, 6, 1, true));

        pieces.add(new Plus(GamePanel.BLUE, 0, 0, false));
        pieces.add(new HourGlass(GamePanel.BLUE, 1, 0, false));
        pieces.add(new Time(GamePanel.BLUE, 2, 0, false));
        pieces.add(new Sun(GamePanel.BLUE, 3, 0, false));
        pieces.add(new Time(GamePanel.BLUE, 4, 0, false));
        pieces.add(new HourGlass(GamePanel.BLUE, 5, 0, false));
        pieces.add(new Plus(GamePanel.BLUE, 6, 0, false));

        pieces.add(new Point(GamePanel.YELLOW, 0, 4, false));
        pieces.add(new Point(GamePanel.YELLOW, 1, 4, false));
        pieces.add(new Point(GamePanel.YELLOW, 2, 4, false));
        pieces.add(new Point(GamePanel.YELLOW, 3, 4, false));
        pieces.add(new Point(GamePanel.YELLOW, 4, 4, false));
        pieces.add(new Point(GamePanel.YELLOW, 5, 4, false));
        pieces.add(new Point(GamePanel.YELLOW, 6, 4, false));

        pieces.add(new Plus(GamePanel.YELLOW, 0, 5, false));
        pieces.add(new HourGlass(GamePanel.YELLOW, 1, 5, false));
        pieces.add(new Time(GamePanel.YELLOW, 2, 5, false));
        pieces.add(new Sun(GamePanel.YELLOW, 3, 5, false));
        pieces.add(new Time(GamePanel.YELLOW, 4, 5, false));
        pieces.add(new HourGlass(GamePanel.YELLOW, 5, 5, false));
        pieces.add(new Plus(GamePanel.YELLOW, 6, 5, false));
    }


    // Contributed by Wong Jui Hong
    // Copy arraylist of pieces
    public void copyPieces(ArrayList<Piece> source, ArrayList<Piece> destination) {
        destination.clear();
        for(int i = 0; i < source.size(); i++) {
            destination.add(source.get(i));
        }
    }


    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public ArrayList<Piece> getSimPieces() {
        return simPieces;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    
}
