package Forms;

import Plane.BasePlane;

import java.awt.*;

import javax.swing.JPanel;

public class PlanePanel extends JPanel {

    private BasePlane basePlane;
    private Color backgroundColor = new Color(202, 236, 240);

    public PlanePanel(BasePlane basePlane) {
        this.basePlane = basePlane;
        this.setBackground(backgroundColor);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(basePlane != null) {
            basePlane.DrawTransport(g);
        }
    }

    public void setPlane(BasePlane basePlane) {
        this.basePlane = basePlane;
    }
}
