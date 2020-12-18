package Plane;

import Radar.IRadar;
import Radar.RadarDefault;
import Radar.RadarOval;
import Radar.RadarSquare;

import java.awt.*;

public class RadarPlane extends BasePlane {

    public boolean Antenna;

    private boolean haveRadar;

    private IRadar radar;

    public Color DopColor;

    public RadarPlane(int maxSpeed,
                      int weight,
                      Color mainColor,
                      Color dopColor,
                      int typeRadar,
                      int countRadar,
                      boolean antenna) {
        super(maxSpeed, weight, mainColor);
        if (typeRadar == 0) {
            radar = new RadarDefault(countRadar, DopColor);
        } else if (typeRadar == 1) {
            radar = new RadarSquare(countRadar, DopColor);
        } else if (typeRadar == 2) {
            radar = new RadarOval(countRadar, DopColor);
        }
        Antenna = antenna;
        DopColor = dopColor;
    }

    public RadarPlane(int maxSpeed,
                      int weight,
                      Color mainColor,
                      Color dopColor,
                      boolean haveRadar,
                      int typeRadar,
                      int countRadar,
                      boolean antenna) {
        super(maxSpeed, weight, mainColor);
        this.haveRadar = haveRadar;
        if (typeRadar == 0) {
            radar = new RadarDefault(countRadar, DopColor);
        } else if (typeRadar == 1) {
            radar = new RadarSquare(countRadar, DopColor);
        } else if (typeRadar == 2) {
            radar = new RadarOval(countRadar, DopColor);
        }
        Antenna = antenna;
        DopColor = dopColor;
    }

    @Override
    public void DrawTransport(Graphics g) {
        super.DrawTransport(g);

        if (Antenna) {
            g.drawLine((int) _startPosX + 150, (int) _startPosY + 60, (int) _startPosX + 130, (int) _startPosY + 60);
        }

        if (haveRadar) {
            if (radar != null) {
                radar.DrawElement(g, (int) _startPosX, (int) _startPosY);
            } else {
                radar = new RadarDefault(2, DopColor);
                radar.DrawElement(g, (int) _startPosX, (int) _startPosY);
            }
        }
    }

    public void setDopColor(Color dopColor) {
        DopColor = dopColor;
        radar.setDopColor(dopColor);
    }

    public void setRadar(IRadar radar) {
        this.radar = radar;
    }
}
