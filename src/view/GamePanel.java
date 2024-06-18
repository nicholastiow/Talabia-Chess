// Contributors: Wong Jui Hong, Low Kai Yan
// Purpose of this class: To handle the whole game view such as (draw the pieces, board and setup the game menu bar)
//                        and update the game 

package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.FileController;
import controller.GameController;
import controller.Mouse;
import model.Board;
import model.ChessModel;
import model.Piece;


public class GamePanel extends JPanel implements Runnable{
    
    // Dimensions
    public int WIDTH_CONSTANT = Board.SQUARE_SIZE * 7;
    public int HEIGHT_CONSTANT = (Board.SQUARE_SIZE * 6) + 20;

    // FPS
    final int FPS = 60;
    
    // Objects
    private Board board = new Board();
    public Mouse mouse = new Mouse();
    public ChessModel chessModel = new ChessModel();
    private GameController gameController = new GameController(this);

    //Pieces
    public static ArrayList<Piece> gamePieces = new ArrayList<Piece>(); // Acts as the backup for the pieces, if invalid move, the pieces will be reset to this
    public static ArrayList<Piece> simulatedPieces = new ArrayList<Piece>(); // Acts as the simulated pieces, if valid move, the pieces will be copied to this
    public static Piece selectedPiece; // The selected piece

    // Color
    public static final int YELLOW = 1;
    public static final int BLUE = 2;
    public static int currentColor = YELLOW;

    //Turn
    public static int turn = 0;
    
    // Boolean For Move 
    public boolean isValidMove;
    public boolean isValidTile;

    public static JLabel turnLabel; // Label to display the current turn
    

    // Constructor
    public GamePanel(ChessModel chessModel) {
        this.chessModel = chessModel;
        gamePieces = chessModel.getPieces();
        simulatedPieces = chessModel.getSimPieces();
        selectedPiece = chessModel.getSelectedPiece();


        turnLabel = new JLabel("Yellow's Turn"); // Initial label text
        turnLabel.setFont(new Font("Arial", Font.BOLD, 30));
        turnLabel.setForeground(Color.BLACK);
        turnLabel.setBounds(350, 0, 200, 30); // Adjust position and size as needed
        add(turnLabel);
        

        setPreferredSize(new Dimension(WIDTH_CONSTANT, HEIGHT_CONSTANT));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        chessModel.copyPieces(gamePieces, simulatedPieces);

    }

    // Setup Menu Bar
    public void setupMenuBar() {
        // Create an instance of GameMenuBar and pass the current GamePanel instance
        GameMenuBar menuBar = new GameMenuBar(this);

        // Get the JFrame ancestor
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Set the menu bar for the JFrame
        frame.setJMenuBar(menuBar);
    }


    // Repaint Whole Board
    public void repaintWholeBoard() {
        repaint();
    }

    // Contributed by Low Kai Yan
    // Game Loop Method, it will repeat the update and repaint method
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/drawInterval;
            lastTime = now;
            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }


    // Update Method For Piece When Mouse Clicked
    private void update() {
        gameController.update();
    }


    // Contributed by Low Kai Yan
    // New Game Method, reset the pieces and the turn
    public void newGame() {
        gamePieces.clear();
        simulatedPieces.clear();

        chessModel.initPieces();
        gamePieces = chessModel.getPieces();
        simulatedPieces = chessModel.getSimPieces();
        selectedPiece = chessModel.getSelectedPiece();
        chessModel.copyPieces(gamePieces, simulatedPieces);

        currentColor = YELLOW;
        turnLabel.setText("Yellow's Turn");
        selectedPiece = null;
        repaint();

        mouse.pressed = false;
    }


    // Contributed by Low Kai Yan
    // Flip the position of pieces on the board every moves
    public void flipBoard() {
        ArrayList<Piece> backupPieces = new ArrayList<Piece>();
        FileController fileController = new FileController();
        
        // Loop through the simulatedPieces list and flip the position of the pieces
        for(int i = 0; i < simulatedPieces.size(); i++) {
            Piece originalPiece = simulatedPieces.get(i);
            String[] tokens = originalPiece.toString().split(" ");
            
            String classString = tokens[0];
            int color = Integer.parseInt(tokens[1]);
            int col = Integer.parseInt(tokens[2]);
            int row = Integer.parseInt(tokens[3]);
            boolean flip = Boolean.parseBoolean(tokens[4]);

            // Flip the position of the pieces
            col = 6 - col;
            row = 5 - row;
            flip = !flip;
        
            // Recreate the piece with the flipped position
            Piece flippedPiece = fileController.createPieceInstance(classString, color, col, row, flip);

            // System.out.println(flippedPiece.toString());

            backupPieces.add(flippedPiece);
        }

        simulatedPieces.clear();
        gamePieces.clear();

        simulatedPieces.addAll(backupPieces);
        gamePieces.addAll(backupPieces);
        repaintWholeBoard();
    }


    // Contributed by Wong Jui Hong
    // Make the gameboard resizable
    public void resizeBoard() {
        simulatedPieces.forEach(piece -> piece.updatePostion()); // Update the positions of all pieces
        repaintWholeBoard(); // Repaint the whole board

        // Calculate the new preferred size of the panel
        int newWidth =  Board.SQUARE_SIZE * 7;
        int newHeight = Board.SQUARE_SIZE * 6;

        // Set the new preferred size
        setPreferredSize(new Dimension(newWidth, newHeight));

        // Get the JFrame ancestor
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Pack the frame to adjust its size
        frame.pack();
    }

    
    // Contributed by Wong Jui Hong
    // Paint the board and pieces
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // Draw Board
        Graphics2D g2d = (Graphics2D) g;
        board.draw(g2d);
    
        // Create a copy of the gamePieces list to avoid ConcurrentModificationException
        ArrayList<Piece> gamePiecesCopy = new ArrayList<>(gamePieces);
    
        // Draw gamePieces
        Iterator<Piece> iterator = gamePiecesCopy.iterator();
        while (iterator.hasNext()) {
            Piece piece = iterator.next();
            piece.draw(g2d);
        }


        // Fill the color of path for the pieces, if valid move, it will be green, else it will be red
        if (selectedPiece != null) {
            float alpha = 0.5f;
            Color highlightColor = isValidMove ? Color.GREEN : Color.RED; // Change the highlight color whether the move is valid or not
    
            g2d.setColor(highlightColor);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.fillRect(selectedPiece.col * Board.SQUARE_SIZE, selectedPiece.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    
            selectedPiece.draw(g2d);
        }
    }

}

