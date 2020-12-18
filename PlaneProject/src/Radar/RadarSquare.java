package Radar;

import java.awt.*;

public class RadarSquare extends RadarDefault {

    public RadarSquare(int countRadars, Color color) {
        super(countRadars, color);
    }

    public RadarSquare(int type) {
        super(type);
    }

    @Override
    public void DrawElement(Graphics g, int _startPosX, int _startPosY) {
        if(typeRadar != null){

            g.setColor(DopColor);

            switch (typeRadar) {
                case COUNT_3: {
                    g.setColor(DopColor);
                    g.drawRect(_startPosX + 35, _startPosY + 50, 20, 20);
                    g.setColor(DopColor);
                    g.fillRect(_startPosX + 35, _startPosY + 50, 20, 20);
                }

                case COUNT_2: {
                    g.setColor(DopColor);
                    g.drawRect(_startPosX + 65, _startPosY + 50, 20, 20);
                    g.setColor(DopColor);
                    g.fillRect(_startPosX + 65, _startPosY + 50, 20, 20);
                }

                case COUNT_1: {
                    g.setColor(DopColor);
                    g.drawRect(_startPosX + 95, _startPosY + 50, 20, 20);
                    g.setColor(DopColor);
                    g.fillRect(_startPosX + 95, _startPosY + 50, 20, 20);
                }
                break;
                default:
                    System.out.println(typeRadar);
                    break;
            }
        }else {
            System.out.println("typeRadar Square == null");
        }
    }
}
