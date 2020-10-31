import java.awt.*;

import javax.swing.JPanel;

public class PlanePanel extends JPanel {

    private Plane plane;
    private Color backgroundColor = new Color(202, 236, 240);

    public PlanePanel(Plane plane) {
        this.plane = plane;
        this.setBackground(backgroundColor);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(plane != null) {
            plane.DrawTransport(g);
        }
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }
}
