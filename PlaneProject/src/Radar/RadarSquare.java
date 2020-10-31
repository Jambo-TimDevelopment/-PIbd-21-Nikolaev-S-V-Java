package Radar;

import java.awt.*;

public class RadarSquare extends RadarDefault {

    public RadarSquare(int countRadars) {
        super(countRadars);
    }

    @Override
    public void DrawElement(Graphics g, int _startPosX, int _startPosY) {
        if(typeRadar != null){
            Color DopColor = Color.BLACK;
            Color MainColor = Color.blue;
            g.setColor(MainColor);

            switch (typeRadar) {
                case COUNT_3: {
                    g.setColor(DopColor);
                    g.drawRect(_startPosX + 35, _startPosY + 50, 20, 20);
                    g.setColor(MainColor);
                    g.fillRect(_startPosX + 35, _startPosY + 50, 20, 20);
                }

                case COUNT_2: {
                    g.setColor(DopColor);
                    g.drawRect(_startPosX + 65, _startPosY + 50, 20, 20);
                    g.setColor(MainColor);
                    g.fillRect(_startPosX + 65, _startPosY + 50, 20, 20);
                }

                case COUNT_1: {
                    g.setColor(DopColor);
                    g.drawRect(_startPosX + 95, _startPosY + 50, 20, 20);
                    g.setColor(MainColor);
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
