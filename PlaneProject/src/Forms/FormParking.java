package Forms;

import Parking.Parking;
import Plane.BasePlane;
import Plane.IPlane;
import Plane.RadarPlane;
import Radar.IRadar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class FormParking {

    private JFrame frame;
    private JTextField textFieldGetPlace;
    private Parking<IPlane, IRadar> parking;

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
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        ParkingPanel panel = new ParkingPanel();
        panel.setBounds(0, 0, 700, 500);
        frame.getContentPane().add(panel);
        parking = new Parking(panel.getWidth(), panel.getHeight());
        panel.setParking(parking);

        JButton btnParkingBasePlane = new JButton("Припарковать самолет");
        btnParkingBasePlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color mainColor = JColorChooser.showDialog( frame, "Выберите цвет лодки", Color.BLUE );
                if (mainColor != null) {
                    BasePlane plane = new BasePlane( 100, 1000, mainColor );
                    if (parking.addPlane( plane )) {
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog( frame, "Гавань переполнена", "Сообщение", JOptionPane.INFORMATION_MESSAGE );
                    }
                }
                panel.repaint();
            }
        });
        btnParkingBasePlane.setBounds(720, 16, 250, 30);
        frame.getContentPane().add(btnParkingBasePlane);

        JButton btnParkingRadarPlane = new JButton("Припарковать самолет с радаром");
        btnParkingRadarPlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color mainColor = JColorChooser.showDialog( frame, "Выберите цвет лодки", Color.BLUE );
                Color dopColor = JColorChooser.showDialog( frame, "Выберите цвет лодки", Color.BLUE );
                if (mainColor != null) {
                    Random rnd = new Random();
                    int maxSpeed = rnd.nextInt() % 200 + 100;
                    int countRadar = rnd.nextInt() % 3 + 1;
                    boolean antenna = true;
                    BasePlane plane = new RadarPlane(
                            maxSpeed,
                            (float)(rnd.nextInt() % 1000 + 1000),
                            mainColor,
                            dopColor,
                            countRadar,
                            antenna);
                    if (parking.addPlane( plane )) {
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog( frame, "Гавань переполнена", "Сообщение", JOptionPane.INFORMATION_MESSAGE );
                    }
                }
            }
        });
        btnParkingRadarPlane.setBounds(720, 53, 250, 30);
        frame.getContentPane().add(btnParkingRadarPlane);

        textFieldGetPlace = new JTextField();
        textFieldGetPlace.setBounds(830, 299, 84, 26);
        frame.getContentPane().add(textFieldGetPlace);
        textFieldGetPlace.setColumns(10);

        JButton btnTakePlane = new JButton("Забрать");
        btnTakePlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textFieldGetPlace.getText() != "")
                {
                    BasePlane plane = (BasePlane) parking.takePLane(Integer.parseInt(textFieldGetPlace.getText()));
                    if (plane != null)
                    {
                        EventQueue.invokeLater(() -> {
                            FormPlane formPlane;
                            try {
                                formPlane = new FormPlane();
                                formPlane.frame.setVisible(true);
                                frame.setVisible(false);
                            } catch (Exception exp) {
                                exp.printStackTrace();
                                return;
                            }
                            formPlane.setPlane(plane);
                        }); }
                    else JOptionPane.showMessageDialog( frame, "Парковочное место пусто", "Сообщение", JOptionPane.INFORMATION_MESSAGE );
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
}