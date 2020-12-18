package Forms;

import Parking.ParkingCollection;
import Parking.Parking;
import Plane.APlane;
import Plane.BasePlane;
import Plane.IPlane;
import Plane.RadarPlane;
import Radar.IRadar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Stack;

import javax.swing.*;

public class FormParking {

    private JFrame frame;
    private JTextField textFieldGetPlace;
    private JButton btnTakeLastPlane;
    private Parking<IPlane, IRadar> planeParking;
    ParkingPanel panel = new ParkingPanel();

    private ParkingCollection collectionParking;
    private JList<String> jListOfParking;
    private final DefaultListModel<String> listParking = new DefaultListModel<>();
    private Stack<BasePlane> deletePlane = new Stack<>();


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FormParking window = new FormParking();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public FormParking() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        int width = 733;
        int height = 390;

        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        panel.setBounds(0, 0, 700, 500);
        frame.getContentPane().add(panel);
        planeParking = new Parking(panel.getWidth(), panel.getHeight());
        panel.setParking(planeParking);

        JLabel stationsLabel = new JLabel("Аэропорта:");
        stationsLabel.setBounds(width, 20, 100, 20);
        frame.add(stationsLabel);

        JTextField addNewStationTextField = new JTextField(2);
        addNewStationTextField.setFont(addNewStationTextField.getFont().deriveFont(20f));
        addNewStationTextField.setBounds(width, 40, 200, 20);
        frame.add(addNewStationTextField);

        JButton addStationButton = new JButton("Добавить парковку");
        addStationButton.addActionListener(e -> {
            if (addNewStationTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "Введите название парковки", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            collectionParking.AddParking(addNewStationTextField.getText());
            ReloadParking();
        });
        addStationButton.setBounds(width, 60, 200, 20);
        frame.add(addStationButton);

        collectionParking = new ParkingCollection(width, height);

        for (String key : collectionParking.keys()) {
            listParking.addElement(key);
        }
        jListOfParking = new JList<>(listParking);
        jListOfParking.setLayoutOrientation(JList.VERTICAL);
        jListOfParking.setBounds(width + 20, 90, 130, 80);
        jListOfParking.addListSelectionListener(e -> {
            if (jListOfParking.getSelectedIndex() > -1) {
                panel.setParking(collectionParking.get(listParking.get(jListOfParking.getSelectedIndex())));
                panel.repaint();
            }
        });
        frame.add(jListOfParking);

        btnTakeLastPlane = new JButton( "Получить последний самолет" );
        btnTakeLastPlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (deletePlane.size() <= 0) {
                    JOptionPane.showMessageDialog( frame, "Нет улетевших самолетов", "Сообщение", JOptionPane.INFORMATION_MESSAGE );
                }
                else {
                    BasePlane lastPlane = deletePlane.pop();
                    EventQueue.invokeLater( () -> {
                                FormPlane formPlane;
                                try {
                                    formPlane = new FormPlane( frame );
                                    formPlane.frame.setVisible( true );
                                    frame.setVisible( false );
                                } catch (Exception exp) {
                                    exp.printStackTrace();
                                    return;
                                }
                        formPlane.setPlane( lastPlane );
                            }
                    );
                    deletePlane.remove(lastPlane);
                }
                panel.repaint();
            }
        } );
        btnTakeLastPlane.setBounds( 735, 381, 215, 29 );
        frame.getContentPane().add(btnTakeLastPlane);



        JButton deleteStationButton = new JButton("Удалить парковку");
        deleteStationButton.addActionListener(e -> {
            if (jListOfParking.getSelectedIndex() > -1) {
                if (JOptionPane.showConfirmDialog(frame, "Удалить парковку " + listParking.get(jListOfParking.getSelectedIndex()) + "?", "Удаление", JOptionPane.OK_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {
                    collectionParking.DelParking(listParking.get(jListOfParking.getSelectedIndex()));
                    ReloadParking();
                    panel.repaint();
                }
            }
        });
        deleteStationButton.setBounds(width, 180, 200, 20);
        frame.add(deleteStationButton);

        JButton btnParkingBasePlane = new JButton("Припарковать самолет");
        btnParkingBasePlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FormPlaneConfig newFrame = new FormPlaneConfig(frame);
                    newFrame.addEvent(this::addPlane);
                    newFrame.frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void addPlane(APlane aPlane) {
                if (aPlane != null && jListOfParking.getSelectedIndex() > -1) {
                    if (collectionParking.get(listParking.get(jListOfParking.getSelectedIndex())).addPlane(aPlane)) {
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Самолет не удалось поставить");
                    }
                }
            }
        });
        btnParkingBasePlane.setBounds(720, 216, 250, 30);
        frame.getContentPane().add(btnParkingBasePlane);

       /* JButton btnParkingRadarPlane = new JButton("Припарковать самолет с радаром");
        btnParkingRadarPlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jListOfParking.getSelectedIndex() > -1) {
                    Color mainColor = JColorChooser.showDialog(frame, "Выберите цвет самолета", Color.BLUE);
                    Color dopColor = JColorChooser.showDialog(frame, "Выберите цвет самолета", Color.BLUE);
                    if (mainColor != null) {
                        Random rnd = new Random();
                        int maxSpeed = rnd.nextInt() % 200 + 100;
                        int countRadar = rnd.nextInt() % 3 + 1;
                        boolean antenna = true;
                        BasePlane plane = new RadarPlane(
                                maxSpeed,
                                (float) (rnd.nextInt() % 1000 + 1000),
                                mainColor,
                                dopColor,
                                countRadar,
                                antenna);
                        if (collectionParking.get(listParking.get(jListOfParking.getSelectedIndex())).addPlane(plane)) {
                            panel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Парковка переполнена", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                        }
                        //panel.repaint();
                    }
                } else
                    JOptionPane.showMessageDialog(frame, "Нет места для парковки", "Сообщение", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        btnParkingRadarPlane.setBounds(720, 253, 250, 30);
        frame.getContentPane().add(btnParkingRadarPlane);
        */

        textFieldGetPlace = new JTextField();
        textFieldGetPlace.setBounds(830, 299, 84, 26);
        frame.getContentPane().add(textFieldGetPlace);
        textFieldGetPlace.setColumns(10);

        JButton btnTakePlane = new JButton("Забрать");
        btnTakePlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textFieldGetPlace.getText() != "") {
                    BasePlane plane = (BasePlane) collectionParking.
                                    get(listParking.get(jListOfParking.getSelectedIndex())).
                                    get(Integer.parseInt(textFieldGetPlace.getText()));

                    collectionParking.get(listParking.get(jListOfParking.getSelectedIndex())).
                            removePlane(Integer.parseInt(textFieldGetPlace.getText()));

                    if (plane != null) {
                        deletePlane.add(plane);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Парковочное место пусто",
                                "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                    }
                    panel.repaint();
                }
            }
        });

        btnTakePlane.setBounds(800, 341, 115, 29);
        frame.getContentPane().add(btnTakePlane);

        JLabel labelPlacePlane = new JLabel("Место");
        labelPlacePlane.setBounds(750, 302, 69, 20);
        frame.getContentPane().add(labelPlacePlane);

        JLabel lblPickUpPlane = new JLabel("Забрать самолет");
        lblPickUpPlane.setBounds(770, 263, 115, 20);
        frame.getContentPane().add(lblPickUpPlane);
    }

    private void ReloadParking() {
        int index = jListOfParking.getSelectedIndex();

        jListOfParking.setSelectedIndex(-1);
        listParking.clear();
        for (int i = 0; i < collectionParking.keys().length; i++) {
            listParking.addElement(collectionParking.keys()[i]);
        }

        if (listParking.size() > 0 && (index == -1 || index >= listParking.size())) {
            jListOfParking.setSelectedIndex(0);
        } else if (listParking.size() > 0 && index > -1 && index < listParking.size()) {
            jListOfParking.setSelectedIndex(index);
        }
    }
}