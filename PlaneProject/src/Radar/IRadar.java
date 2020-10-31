package Radar;

import java.awt.*;

public interface  IRadar {
    TypeRadar typeRadar = null;

    void setTypeRadar(int countRadar);

    void DrawElement(Graphics g, int startPosX, int startPosY);
}
