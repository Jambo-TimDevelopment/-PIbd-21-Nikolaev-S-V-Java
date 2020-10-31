import java.awt.Color;
import java.awt.Graphics;

public class Plane {

    protected int _startPosX;

    protected int _startPosY;

    protected int _pictureWidth;

    protected int _pictureHeight;

    public int MaxSpeed;

    public float Weight;

    public Color MainColor;

    public Color DopColor;

    public boolean EngineType;

    private int  countRadar;

    public Radar radar;

    public boolean Antenna;

    private int planeWidth = 150;

    private int planeHeight = 120;

    public Plane(int maxSpeed, float weight, Color mainColor, Color dopColor, int countRadar,
                 boolean antenna) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        DopColor = dopColor;
        this.countRadar = countRadar;
        radar = new Radar(this.countRadar);
        Antenna = antenna;
    }

    public void SetPosition(int x, int y, int width, int height) {
        _startPosX = x;
        _startPosY = y;
        _pictureHeight = height;
        _pictureWidth = width;
    }

    public void MoveTransport(Direction direction) {
        float step = MaxSpeed * 100 / Weight;
        switch (direction) {
            case Right:
                if (_startPosX + step < _pictureWidth - planeWidth) {
                    _startPosX += step;
                }
                break;
            case Left:
                if (_startPosX - step > 0) {
                    _startPosX -= step;
                }
                break;
            case Up:
                if (_startPosY - step > 0) {
                    _startPosY -= step;
                }
                break;
            case Down:
                if (_startPosY + step < _pictureHeight - planeHeight) {
                    _startPosY += step;
                }
                break;
                default:
                    System.out.println("IT IS FAIL");
        }
    }

    public void DrawTransport(Graphics g) {
        Color brMain = MainColor;
        g.setColor(brMain);

        g.fillOval(_startPosX + 80, _startPosY + 50, 50, 20);
        g.fillOval(_startPosX, _startPosY + 50, 40, 20);
        g.fillRect(_startPosX + 10, _startPosY + 50, 100, 20);
        g.fillOval(_startPosX + 10, _startPosY + 30, 20, 60);
        g.fillOval(_startPosX + 70, _startPosY, 20, 120);

        if (Antenna) {
            g.drawLine(_startPosX + 150, _startPosY + 60, _startPosX + 130, _startPosY + 60);
        }
        radar.DrawElement(g, _startPosX, _startPosY, DopColor);
    }
}