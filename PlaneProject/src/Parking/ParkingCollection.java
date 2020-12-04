package Parking;

import Plane.APlane;
import Plane.IPlane;
import Radar.IRadar;

import java.util.HashMap;

public class ParkingCollection {
    public HashMap<String, Parking<IPlane, IRadar>> harbourStages;

    public String[] keys() {
        return harbourStages.keySet().toArray(new String[harbourStages.keySet().size()]);
    }

    private int pictureWidth;

    private int pictureHeight;

    public ParkingCollection(int pictureWidth, int pictureHeight) {
        harbourStages = new HashMap<String, Parking<IPlane, IRadar>>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public void AddParking(String name) {
        if (harbourStages.containsKey( name )) {
            return;
        }
        harbourStages.put( name, new Parking<IPlane, IRadar>( pictureWidth, pictureHeight ) );
    }

    public void DelParking(String name) {
        if (harbourStages.containsKey( name )) {
            harbourStages.remove( name );
        }
    }

    public void delParking(int index) {
        if (harbourStages.containsKey(index)) {
            harbourStages.remove(index);
        }
    }

    public Parking<IPlane, IRadar> get(String ind) {
        if (harbourStages.containsKey( ind )) {
            return harbourStages.get( ind );
        }
        return null;
    }

    public APlane getIndex(String ind, int index) {
        if (!harbourStages.containsKey(ind)) {
            return null;
        }
        return (APlane) harbourStages.get(ind).get(index);
    }
}
