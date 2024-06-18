// Contributors: Wong Jui Hong
// Purpose of this class: To draw the 7x6 game board and set colors for the board

package model;

import java.awt.*;

public class Board {
    public final static int MAX_COL = 7;
    public final static int MAX_ROW = 6;
    public static int SQUARE_SIZE = 100;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;
    public static final int BORDER_SIZE = 1; // Adjust the border size as needed

    public static Color color1 = new Color(118, 150, 86);
    public static Color color2 = new Color(238, 238, 210);


    // Draw the board
    public void draw(Graphics g) {
        for (int i = 0; i < MAX_COL; i++) {
            for (int j = 0; j < MAX_ROW; j++) {
                int x = i * SQUARE_SIZE;
                int y = j * SQUARE_SIZE;

                // Draw filled rectangle
                if ((i + j) % 2 == 0) {
                    g.setColor(color1);
                } else {
                    g.setColor(color2);
                }
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                // Draw border
                g.setColor(Color.BLACK); // Set the border color to black
                for (int k = 0; k < BORDER_SIZE; k++) {
                    g.drawRect(x + k, y + k, SQUARE_SIZE - 2 * k - 1, SQUARE_SIZE - 2 * k - 1);
                }
            }
        }
    }
}
