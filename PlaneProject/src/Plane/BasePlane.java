package Plane;

import Plane.APlane;

import java.awt.Color;
import java.awt.Graphics;

import static Plane.Direction.*;

public class BasePlane extends APlane {

    private int planeWidth = 150;

    private int planeHeight = 120;

    static protected String separator = ";";

    public BasePlane(int maxSpeed, float weight, Color mainColor) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
    }

    public BasePlane(String s) {
        String[] str = s.split(separator);
        if (str.length == 3) {
            MaxSpeed = Integer.parseInt(str[0]);
            Weight = Float.parseFloat(str[1]);
            MainColor = Color.decode(str[2]);
        }
    }

    public void SetPosition(int x, int y, int width, int height) {
        _startPosX = x;
        _startPosY = y;
        _pictureHeight = height;
        _pictureWidth = width;
    }

    @Override
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
                    System.out.println("ВСЁ ПЛОХО!");
        }
    }

    @Override
    public void DrawTransport(Graphics g) {
        Color pen =  Color.BLACK;
        Color brMain = MainColor;
        g.setColor(brMain);

        g.fillOval((int)_startPosX + 80, (int)_startPosY + 50, 50, 20);
        g.fillOval((int)_startPosX, (int)_startPosY + 50, 40, 20);
        g.fillRect((int)_startPosX + 10, (int)_startPosY + 50, 100, 20);
        g.fillOval((int)_startPosX + 10, (int)_startPosY + 30, 20, 60);
        g.fillOval((int)_startPosX + 70, (int)_startPosY, 20, 120);
    }

    @Override
    public String toString(){
        return getMaxSpeed() + separator + getWeight() + separator + getMainColor().getRGB();
    }
}