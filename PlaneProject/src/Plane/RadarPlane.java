package Plane;

import Radar.IRadar;
import Radar.RadarDefault;
import Radar.RadarOval;
import Radar.RadarSquare;

import java.awt.*;

public class RadarPlane extends BasePlane {

    public boolean Antenna;

    private boolean haveRadar;

    private int typeRadar;

    private int countRadar;

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
        DopColor = dopColor;
        this.typeRadar = typeRadar;
        this.countRadar = countRadar;
        if (typeRadar == 0) {
            radar = new RadarDefault(countRadar, DopColor);
        } else if (typeRadar == 1) {
            radar = new RadarSquare(countRadar, DopColor);
        } else if (typeRadar == 2) {
            radar = new RadarOval(countRadar, DopColor);
        }
        Antenna = antenna;
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

    public RadarPlane(String s) {
        super(Integer.parseInt(s.split(separator)[0]), Float.parseFloat(s.split(separator)[1]), Color.decode(s.split(separator)[2]));
        String[] info = s.split(separator);
        if (info.length == 8) {
            DopColor = Color.decode(info[3]);
            haveRadar = Boolean.parseBoolean(info[4]);
            int typeRadar = Integer.parseInt(info[5]);
            int countRadar = Integer.parseInt(info[6]);
            Antenna = Boolean.parseBoolean(info[7]);
            if (typeRadar == 0) {
                radar = new RadarDefault(countRadar, DopColor);
            } else if (typeRadar == 1) {
                radar = new RadarSquare(countRadar, DopColor);
            } else if (typeRadar == 2) {
                radar = new RadarOval(countRadar, DopColor);
            }
        }
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

    @Override
    public String toString(){
        return super.toString() + separator
                + DopColor.getRGB() + separator
                + haveRadar + separator
                + typeRadar + separator
                + countRadar + separator
                + Antenna + separator;
    }
}
