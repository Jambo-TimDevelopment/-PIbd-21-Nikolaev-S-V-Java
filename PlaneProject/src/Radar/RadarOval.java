package Radar;

import java.awt.*;

public class RadarOval extends RadarDefault {


    public RadarOval(int countRadars, Color color) {
        super(countRadars, color);
    }

    public RadarOval(int type) {
        super(type);

    }

    @Override
    public void DrawElement(Graphics g, int _startPosX, int _startPosY) {

        g.setColor(DopColor);

        if(typeRadar != null)
            switch (typeRadar) {
                case COUNT_3: {
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 35, _startPosY + 40, 20, 40);
                    g.setColor(DopColor);
                    g.drawOval(_startPosX + 35, _startPosY + 40, 20, 40);
                }

                case COUNT_2: {
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 65, _startPosY + 40, 20, 40);
                    g.setColor(DopColor);
                    g.drawOval(_startPosX + 65, _startPosY + 40, 20, 40);
                }

                case COUNT_1: {
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 95, _startPosY + 40, 20, 40);
                    g.setColor(DopColor);
                    g.drawOval(_startPosX + 95, _startPosY + 40, 20, 40);
                }
                break;
                default:
                    System.out.println(typeRadar);
                    break;

            }else {

            }
    }
}
