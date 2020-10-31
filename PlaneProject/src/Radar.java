import java.awt.*;

public class Radar {

    private TypeRadar typeRadar;
    public int countRadars;

    Radar(int countRadars){
        this.countRadars = countRadars;
        typeRadar = createTypeRadar(this.countRadars);
    }

    TypeRadar createTypeRadar(int countRadars) {
        switch (countRadars) {
            case 1:
                return TypeRadar.COUNT_1;
            case 2:
                return TypeRadar.COUNT_2;
            case 3:
                return TypeRadar.COUNT_3;
            default: return TypeRadar.COUNT_2;
        }
    }

    public void DrawElement(Graphics g, int _startPosX, int _startPosY, Color DopColor) {
        g.setColor(DopColor);
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
                case COUNT_1:{
                    g.setColor(DopColor);
                    g.drawOval(_startPosX + 95, _startPosY + 50, 20, 20);
                    g.setColor(DopColor);
                    g.fillOval(_startPosX + 95, _startPosY + 50, 20, 20);
                }
                break;
            }
    }
}
