package Radar;

import java.awt.*;

import static Radar.TypeRadar.*;
import static Radar.TypeRadar.COUNT_1;

public class RadarOval extends RadarDefault {


    public RadarOval(int countRadars) {
        super(countRadars);
    }

    @Override
    public void DrawElement(Graphics g, int _startPosX, int _startPosY) {
        Color DopColor = Color.BLACK;
        Color MainColor = Color.blue;
        g.setColor(MainColor);

        if(typeRadar != null)
            switch (typeRadar) {
                case COUNT_3: {
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 35, _startPosY + 40, 20, 40);
                    g.setColor(MainColor);
                    g.drawOval(_startPosX + 35, _startPosY + 40, 20, 40);
                }

                case COUNT_2: {
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 65, _startPosY + 40, 20, 40);
                    g.setColor(MainColor);
                    g.drawOval(_startPosX + 65, _startPosY + 40, 20, 40);
                }

                case COUNT_1: {
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 95, _startPosY + 40, 20, 40);
                    g.setColor(MainColor);
                    g.drawOval(_startPosX + 95, _startPosY + 40, 20, 40);
                }
                break;
                default:
                    System.out.println(typeRadar);
                    break;

            }else {
                System.out.println("typeRadar Oval == null");
            }
    }
}
