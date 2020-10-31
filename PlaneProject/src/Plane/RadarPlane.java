package Plane;

import Plane.BasePlane;
import Radar.IRadar;
import Radar.RadarDefault;
import Radar.RadarOval;
import Radar.RadarSquare;

import java.awt.*;
import java.util.Random;

public class RadarPlane extends BasePlane {

    public RadarDefault Radar;

    public boolean Antenna;

    private IRadar radar;

    public Color DopColor;

    private int countRadar;

    public RadarPlane(int maxSpeed,
                      float weight,
                      Color mainColor,
                      Color dopColor,
                      int countRadar,
                      boolean antenna) {
        super(maxSpeed, weight, mainColor);
        countRadar = countRadar;
        Random rnd = new Random();
        int randomTypeRadar = rnd.nextInt() % 3;
        if(randomTypeRadar == 0){
            radar = new RadarDefault(countRadar);
        }else if(randomTypeRadar == 1){
            radar = new RadarSquare(countRadar);
        }else if(randomTypeRadar == 2){
            radar = new RadarOval(countRadar);
        }
        Antenna = antenna;
        DopColor = dopColor;
    }

    @Override
    public void DrawTransport(Graphics g) {
        super.DrawTransport(g);

        if (Antenna) {
            g.drawLine((int)_startPosX + 150, (int)_startPosY + 60, (int)_startPosX + 130, (int)_startPosY + 60);
        }

        if(radar != null){
            radar.DrawElement(g, (int)_startPosX, (int)_startPosY);
        }else{
            radar = new RadarDefault(countRadar);
            radar.DrawElement(g, (int)_startPosX, (int)_startPosY);
        }
    }
}
