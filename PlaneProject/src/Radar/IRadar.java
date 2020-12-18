package Radar;

import java.awt.*;
import java.io.Serializable;

public interface  IRadar extends Serializable {
    public TypeRadar typeRadar = null;

    void setTypeRadar(int countRadar);

    void setConfig(int config);

    void DrawElement(Graphics g, int startPosX, int startPosY);

    void setDopColor(Color dopColor);
}
