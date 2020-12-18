package Radar;

import java.awt.*;

import static Radar.TypeRadar.*;

public class RadarDefault implements IRadar {

    protected TypeRadar typeRadar;

    protected Color DopColor;

    public RadarDefault(int countRadars, Color color){
        typeRadar = createTypeRadar(countRadars);
        DopColor = color;
    }

    public RadarDefault(int type) {
        typeRadar = createTypeRadar(type);
    }

    @Override
    public void setConfig(int config) {
        switch (config) {
            case 4:
                typeRadar = TypeRadar.COUNT_1;
                break;
            case 5:
                typeRadar = TypeRadar.COUNT_2;
                break;
            default:
                typeRadar = TypeRadar.COUNT_3;
        }
    }

    private TypeRadar createTypeRadar(int countRadars) {
        switch (countRadars) {
            case 1:
                return COUNT_1;
            case 3:
                return COUNT_3;
            default: return COUNT_2;
        }
    }

    @Override
    public void setTypeRadar(int countRadar) {
        switch (countRadar) {
            case 2:
                typeRadar = COUNT_2;
                break;
            case 3:
                typeRadar = COUNT_3;
                break;
            default: typeRadar = COUNT_1;
            break;
        }
    }

    public void DrawElement(Graphics g, int _startPosX, int _startPosY) {
        g.setColor(DopColor);

        if(typeRadar != null){
            switch (typeRadar) {
                case COUNT_3: {
                    g.setColor(DopColor);
                    g.drawOval(_startPosX + 35, _startPosY + 50, 20, 20);
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 35, _startPosY + 50, 20, 20);
                }

                case COUNT_2: {
                    g.setColor(DopColor);
                    g.drawOval(_startPosX + 65, _startPosY + 50, 20, 20);
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 65, _startPosY + 50, 20, 20);
                }

                case COUNT_1: {
                    g.setColor(DopColor);
                    g.drawOval(_startPosX + 95, _startPosY + 50, 20, 20);
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 95, _startPosY + 50, 20, 20);
                }
                break;
                default:
                    System.out.println(typeRadar);
                    break;
            }
        }else{
              System.out.println("typeRadar Default == null");
        }
    }

    public void setDopColor(Color Color) {
        DopColor = Color;
    }
}
