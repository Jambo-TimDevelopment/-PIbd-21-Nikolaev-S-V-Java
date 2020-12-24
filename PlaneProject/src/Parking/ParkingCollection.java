package Parking;

import Plane.APlane;
import Plane.BasePlane;
import Plane.IPlane;
import Plane.RadarPlane;
import Radar.IRadar;

import java.io.*;
import java.util.HashMap;

public class ParkingCollection {
    public HashMap<String, Parking<IPlane, IRadar>> airportStages;

    public String[] keys() {
        return airportStages.keySet().toArray(new String[airportStages.keySet().size()]);
    }

    private int pictureWidth;

    private int pictureHeight;

    private final String separator = ":";

    public ParkingCollection(int pictureWidth, int pictureHeight) {
        airportStages = new HashMap<String, Parking<IPlane, IRadar>>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public void AddParking(String name) {
        if (airportStages.containsKey( name )) {
            return;
        }
        airportStages.put( name, new Parking<IPlane, IRadar>( pictureWidth, pictureHeight ) );
    }

    public void DelParking(String name) {
        if (airportStages.containsKey( name )) {
            airportStages.remove( name );
        }
    }

    public void delParking(int index) {
        if (airportStages.containsKey(index)) {
            airportStages.remove(index);
        }
    }

    public Parking<IPlane, IRadar> get(String ind) {
        if (airportStages.containsKey( ind )) {
            return airportStages.get( ind );
        }
        return null;
    }

    public APlane getIndex(String ind, int index) {
        if (!airportStages.containsKey(ind)) {
            return null;
        }
        return (APlane) airportStages.get(ind).get(index);
    }

    public boolean SaveData(String filename, String selectedName) {
        if (!airportStages.containsKey(selectedName)) {
            return false;
        }
        try {
            File dataFile = new File(filename);
            if (dataFile.exists()) {
                if (!dataFile.delete())
                    return false;
            }
            try {
                if (!dataFile.createNewFile()) {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
            writer.write("Airport" + separator + selectedName);
            writer.newLine();
            IPlane plane;
            for (int i = 0; (plane = airportStages.get(selectedName).get(i)) != null; i++) {
                if (plane.getClass() == BasePlane.class) {
                    writer.write("BasePlane" + separator);
                }
                if (plane.getClass() == RadarPlane.class) {
                    writer.write("RadarPlane" + separator);
                }
                //Записываемые параметры
                writer.write(plane.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean SaveAllData(String filename) {
        try {
            File dataFile = new File(filename);
            if (dataFile.exists()) {
                if (!dataFile.delete())
                    return false;
            }
            if (!dataFile.createNewFile()) {
                return false;
            }
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(dataFile));
            bufferWriter.write("AirplaneCollection");
            bufferWriter.newLine();
            airportStages.forEach((key, value) -> {
                try {
                    bufferWriter.write("Airplane" + separator + key);
                    bufferWriter.newLine();
                    IPlane plane;
                    for (int i = 0; (plane = value.get(i)) != null; i++) {
                        if (plane.getClass() == BasePlane.class) {
                            bufferWriter.write("BasePlane" + separator);
                        }
                        if (plane.getClass() == RadarPlane.class) {
                            bufferWriter.write("RadarPlane" + separator);
                        }
                        //Записываемые параметры
                        bufferWriter.write(plane.toString());
                        bufferWriter.newLine();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            bufferWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean LoadData(String filename) {
        try {
            File dataFile = new File(filename);
            if (!dataFile.exists()) {
                System.out.println(" ----> файл не найден!");
                return false;
            }
            boolean airportHead = true;
            String str;
            APlane plane = null;
            String key = "";
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            while ((str = reader.readLine()) != null) {
                str = str.replace("\r", "");
                if (airportHead) {
                    if (str.contains("Airport")) {
                        key = str.split(separator)[1];
                        if (airportStages.containsKey(key)) {
                            airportStages.get(key).clear();
                        } else {
                            airportStages.put(key, new Parking(pictureWidth, pictureHeight));
                        }
                        airportHead = false;
                    } else {
                        System.out.println("----> Airport wrong name ");
                        return false;
                    }
                } else {
                    if (str.equals("")) {
                        continue;
                    }
                    if (str.split(separator)[0].equals("BasePlane")) {
                        plane = new BasePlane(str.split(separator)[1]);
                    } else if (str.split(separator)[0].equals("RadarPlane")) {
                        plane = new RadarPlane(str.split(separator)[1]);
                    }
                    boolean isAdd = airportStages.get(key).addPlane(plane);
                    if (!isAdd) {
                        System.out.println("----> Add plane is wrong!");
                        return false;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean LoadAllData(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println(" ----> файл не найден!");
                return false;
            }
            boolean airportHead = true;
            String line;
            APlane plane = null;
            String key = "";
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                line = line.replace("\r", "");
                if (airportHead) {
                    if (line.contains("AirplaneCollection")) {
                        airportStages.clear();
                        airportHead = false;
                    } else {
                        System.out.println("--- Проблемы в названии AirplaneCollections---");
                        return false;
                    }
                } else {
                    if (line.contains("Airplane")) {
                        key = line.split(separator)[1];
                        airportStages.put(key, new Parking<>(pictureWidth, pictureHeight));
                        continue;
                    }
                    if (line.equals("")) {
                        continue;
                    }
                    if (line.split(separator)[0].equals("BasePlane")) {
                        plane = new BasePlane(line.split(separator)[1]);
                    } else if (line.split(separator)[0].equals("RadarPlane")) {
                        plane = new RadarPlane(line.split(separator)[1]);
                    }
                    boolean isAdd = airportStages.get(key).addPlane(plane);
                    if (!isAdd) {
                        System.out.println("----> Add plane is wrong!");
                        return false;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
