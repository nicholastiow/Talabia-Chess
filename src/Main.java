// Contributors: Low Kai Yan, Nicholas Tiow Kai Bo, Wong Wei Ping, Wong Jui Hong
// Purpose of this class: The main class to run the game

import javax.swing.*;

import controller.GameController;
import model.ChessModel;
import view.GamePanel;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Talabia Chess");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

             ChessModel chessModel = new ChessModel(); // Create an instance of ChessModel

            GamePanel gamePanel = new GamePanel(chessModel);
            GameController gameController = new GameController(gamePanel); // Add a GameController

            
            window.add(gamePanel);
            window.pack();

            window.setLocationRelativeTo(null);
            window.setVisible(true);

            gamePanel.setupMenuBar(); // Setup the menu bar

            gameController.startGame();
        });
    }
}