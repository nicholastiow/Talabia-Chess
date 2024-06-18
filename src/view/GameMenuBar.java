// Contributors: Wong Wei Ping
// Purpose of this class: To design the game menu bar

package view;

import javax.swing.*;
import controller.FileController;
import model.Board;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GameMenuBar extends JMenuBar {

    private GamePanel gamePanel;

    public GameMenuBar(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        JMenu fileMenu = new JMenu("Settings");

        // Create menu items
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem loadGame = new JMenuItem("Load Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add action listeners(new game) to menu items
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the newGame method in the GamePanel
                gamePanel.newGame();
            }
        });

        // Add action listeners(save game) to menu items
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController fileController = new FileController();
                fileController.savePiecesToFile(new File("./data/save.txt"), GamePanel.simulatedPieces,
                        GamePanel.currentColor, GamePanel.turn);
                // Show a success dialog
                JOptionPane.showMessageDialog(gamePanel, "Game saved successfully", "Save Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add action listeners(load game) to menu items
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController fileController = new FileController();
                gamePanel.newGame();
                fileController.loadPiecesFromFile(new File("./data/save.txt"));

                gamePanel.repaint();

                // Show a success dialog
                JOptionPane.showMessageDialog(gamePanel, "Game loaded successfully", "Load Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenu themeMenu = new JMenu("Game Theme");

        JRadioButtonMenuItem theme1 = new JRadioButtonMenuItem("Theme1");
        JRadioButtonMenuItem theme2 = new JRadioButtonMenuItem("Theme2");
        JRadioButtonMenuItem theme3 = new JRadioButtonMenuItem("Theme3");
        JRadioButtonMenuItem theme4 = new JRadioButtonMenuItem("Theme4");
        JRadioButtonMenuItem theme5 = new JRadioButtonMenuItem("Theme5");

        // Create a ButtonGroup to ensure that only one radio button is selected at a time
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(theme1);
        themeGroup.add(theme2);
        themeGroup.add(theme3);
        themeGroup.add(theme4);
        themeGroup.add(theme5);

        themeMenu.add(theme1);
        themeMenu.add(theme2);
        themeMenu.add(theme3);
        themeMenu.add(theme4);
        themeMenu.add(theme5);

        // Set the default theme
        theme1.setSelected(true);

        
        // Design the theme color(theme 1 - 5)
        theme1.addActionListener(e -> {
            Board.color1 = new Color(118, 150, 86);
            Board.color2 = new Color(238, 238, 210);
            theme1.setSelected(true);
            gamePanel.repaint();
        });

        theme2.addActionListener(e -> {
            Board.color1 = new Color(111,115,210);
            Board.color2 = new Color(157,172,255);
            theme2.setSelected(true);
            gamePanel.repaint();
        });

        theme3.addActionListener(e -> {
            Board.color1 = new Color(111,143,114);
            Board.color2 = new Color(173,189,143);
            theme3.setSelected(true);
            gamePanel.repaint();
        });

        theme4.addActionListener(e -> {
            Board.color1 = new Color(187,190,100);
            Board.color2 = new Color(234,240,206);
            theme4.setSelected(true);
            gamePanel.repaint();
        });

        theme5.addActionListener(e -> {
            Board.color1 = new Color(112,102,119);
            Board.color2 = new Color(204,183,174);
            theme5.setSelected(true);
            gamePanel.repaint();
        });

        JMenu sizeMenu = new JMenu("Window Size");

        JRadioButtonMenuItem smallSizeItem = new JRadioButtonMenuItem("Small");
        JRadioButtonMenuItem mediumSizeItem = new JRadioButtonMenuItem("Medium");
        JRadioButtonMenuItem largeSizeItem = new JRadioButtonMenuItem("Large");

        // Create a ButtonGroup to ensure that only one radio button is selected at a time
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(smallSizeItem);
        sizeGroup.add(mediumSizeItem);
        sizeGroup.add(largeSizeItem);

        sizeMenu.add(smallSizeItem);
        sizeMenu.add(mediumSizeItem);
        sizeMenu.add(largeSizeItem);

        // Set the default size
        mediumSizeItem.setSelected(true);


        // Design the window size(small, medium, large)
        smallSizeItem.addActionListener(e -> {
            Board.SQUARE_SIZE = 80;
            smallSizeItem.setSelected(true);
            gamePanel.resizeBoard();
        });

        mediumSizeItem.addActionListener(e -> {
            Board.SQUARE_SIZE = 100;
            mediumSizeItem.setSelected(true);
            gamePanel.resizeBoard();
        });

        largeSizeItem.addActionListener(e -> {
            Board.SQUARE_SIZE = 120;
            largeSizeItem.setSelected(true);
            gamePanel.resizeBoard();
        });


        // Add action listeners(exit) to menu items
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the program
            }
        });

        // Add menu items to the menu
        fileMenu.add(themeMenu);
        fileMenu.addSeparator(); // Add a separator line between menu items
        fileMenu.add(sizeMenu);
        fileMenu.addSeparator(); // Add a separator line between menu items
        fileMenu.add(newGameItem);
        fileMenu.addSeparator(); // Add a separator line between menu items
        fileMenu.add(saveGame);
        fileMenu.addSeparator(); // Add a separator line between menu items
        fileMenu.add(loadGame);
        fileMenu.addSeparator(); // Add a separator line between menu items
        fileMenu.add(exitItem);

        // Add the menu to the menu bar
        add(fileMenu);
    }
}
