// Contributors: Low Kai Yan
// Purpose of this class: To initialize the mouse functions

package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {

    public int x, y;
    public boolean pressed;


    // When Mouse Clicked
    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    // When Mouse Released
    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }

    // When Mouse Dragged
    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }


    // When Mouse Moved
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
