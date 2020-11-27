package Forms;

import Parking.Parking;

import javax.swing.*;
import java.awt.*;

public class ParkingPanel extends JPanel {
    private Parking parking;

    ParkingPanel(){
        super();
    }

    public void setParking(Parking parking){
        this.parking = parking;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.gray);
        if(parking != null) {
            parking.Draw(g);

        }
    }
}
