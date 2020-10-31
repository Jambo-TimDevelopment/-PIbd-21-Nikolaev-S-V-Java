package Plane;

import java.awt.*;

public interface IPlane {

    void SetPosition(int x, int y, int width, int height);

    void MoveTransport(Direction direction);

    void DrawTransport(Graphics g);
}
