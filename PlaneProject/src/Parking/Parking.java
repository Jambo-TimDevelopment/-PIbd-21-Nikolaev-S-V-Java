package Parking;

import Plane.APlane;
import Plane.IPlane;
import Radar.IRadar;

import java.awt.*;
import java.util.ArrayList;

public class Parking<T extends IPlane, V extends IRadar>  {

    public ArrayList<T> _places;

    private int pictureWidth;

    private int pictureHeight;

    private int _placeSizeWidth = 260;

    private int _placeSizeHeight = 130;

    private int _maxCount = 6;

    public Parking(int picWidth, int picHeight)
    {
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _places = new ArrayList<T>();
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }

    public boolean addPlane(T plane)
    {
        if (_places.size() >= _maxCount) {
            return false;
        }
        _places.add( plane );
        return true;
    }

    public T takePLane(int index)
    {
        if (index >= _places.size() || index < 0) {
            return null;
        }
        return _places.get( index );
    }

    public void Draw(Graphics g)
    {
        DrawParking(g);
        for (int i = 0; i < _places.size(); i++) {
            if (_places.get( i ) != null) {
                _places.get( i ).SetPosition( 5 + i / 3 * _placeSizeWidth + 5, i % 3 *
                        _placeSizeHeight + 15, pictureWidth, pictureHeight );
                _places.get( i ).DrawTransport( g );
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
        return _places.size();
    }

    public boolean isMore(Parking otherParking){
        return  _places.size() > otherParking.countPlaceParking();
    }

    public boolean isSmaller(Parking otherParking){
        return  _places.size() < otherParking.countPlaceParking();
    }

    public T get(int index) {
        if (index >= _places.size() || index < 0) {
            return null;
        }
        return _places.get(index);
    }

    public void removePlane(int index){
        _places.remove(index);
    }
}
