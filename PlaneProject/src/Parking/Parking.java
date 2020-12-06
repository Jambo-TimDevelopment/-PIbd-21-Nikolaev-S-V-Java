package Parking;

import Plane.IPlane;
import Radar.IRadar;

import java.awt.*;

public class Parking<T extends IPlane, V extends IRadar>  {

    private T[] _places;

    private int pictureWidth;

    private int pictureHeight;

    private int _placeSizeWidth = 260;

    private int _placeSizeHeight = 130;

    public Parking(int picWidth, int picHeight)
    {
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _places = (T[]) new IPlane[width * height];
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }

    public boolean addPlane(T plane)
    {
        for (int i = 0; i < this._places.length; i++)
        {
            if (this._places[i] == null)
            {
                this._places[i] = plane;
                this._places[i].SetPosition((i / 3) * 270, 5 + (i % 3) * 127, this.pictureWidth, this.pictureHeight);
                return true;
            }
        }
        return false;
    }

    public T takePLane(int index)
    {
        if (this._places[index] != null)
        {
            T tmp = this._places[index];
            this._places[index] = null;
            return tmp;
        }
        else
        {
            return null;
        }
    }

    public void Draw(Graphics g)
    {
        DrawParking(g);
        for (int i = 0; i < _places.length; i++)
        {
            if(_places[i] != null){
                _places[i].DrawTransport(g);
            }
        }
    }

    private void DrawParking(Graphics g)
    {
        Color pen = Color.BLACK;
        g.setColor(pen);
        for (int i = 0; i < pictureWidth / _placeSizeWidth; i++)
        {
            for (int j = 0; j < pictureHeight / _placeSizeHeight + 1; ++j)
            {//линия рамзетки места
                g.drawLine(i * _placeSizeWidth, j * _placeSizeHeight, i *
                        _placeSizeWidth + _placeSizeWidth / 2, j * _placeSizeHeight);
            }
            g.drawLine(i * _placeSizeWidth, 0, i * _placeSizeWidth,
                    (pictureHeight / _placeSizeHeight) * _placeSizeHeight);
        }
    }

    public int countPlaceParking(){
        return _places.length;
    }

    public boolean isMore(Parking otherParking){
        return  _places.length > otherParking.countPlaceParking();
    }

    public boolean isSmaller(Parking otherParking){
        return  _places.length < otherParking.countPlaceParking();
    }
}
