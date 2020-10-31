import Plane.BasePlane;
import Plane.Direction;
import Plane.RadarPlane;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;

public class FormPlane {

    private JFrame frame;
    private BasePlane basePlane;
    PlanePanel panel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FormPlane window = new FormPlane();
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
    public FormPlane() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 750, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton MoveUpBut = new JButton("");
        MoveUpBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                basePlane.MoveTransport(Direction.Up);
                panel.repaint();
            }
        });
        ImageIcon iconUp = new ImageIcon("C:\\Users\\senya\\OneDrive\\Рабочий стол\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Up.png");
        MoveUpBut.setIcon(iconUp);
        MoveUpBut.setBounds(627, 404, 38, 23);
        frame.getContentPane().add(MoveUpBut);

        JButton MoveDownBut = new JButton("");
        MoveDownBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                basePlane.MoveTransport(Direction.Down);
                panel.repaint();
            }
        });
        ImageIcon iconDown = new ImageIcon("C:\\Users\\senya\\OneDrive\\Рабочий стол\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Down.png");
        MoveDownBut.setIcon(iconDown);
        MoveDownBut.setBounds(627, 429, 38, 23);
        frame.getContentPane().add(MoveDownBut);

        JButton MoveLeftBut = new JButton("");
        MoveLeftBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                basePlane.MoveTransport(Direction.Left);
                panel.repaint();
            }
        });
        ImageIcon iconLeft = new ImageIcon("C:\\Users\\senya\\OneDrive\\Рабочий стол\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Left.png");
        MoveLeftBut.setIcon(iconLeft);
        MoveLeftBut.setBounds(588, 429, 38, 23);
        frame.getContentPane().add(MoveLeftBut);

        JButton MoveRightBut = new JButton("");
        MoveRightBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                basePlane.MoveTransport(Direction.Right);
                panel.repaint();
            }
        });
        ImageIcon iconRight = new ImageIcon("C:\\Users\\senya\\OneDrive\\Рабочий стол\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Right.png");
        MoveRightBut.setIcon(iconRight);
        MoveRightBut.setBounds(666, 429, 38, 23);
        frame.getContentPane().add(MoveRightBut);

        panel = new PlanePanel(basePlane);
        panel.setBounds(40, 40, 660, 363);
        frame.getContentPane().add(panel);

        JButton CreatePlaneBut = new JButton("Create plane");
        CreatePlaneBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Random rnd = new Random();
                Color mainColor =  Color.GRAY;
                Color dopColor = new Color(0,0,0);
                boolean antenna = true;
                basePlane = new BasePlane(
                        rnd.nextInt() % 200 + 100,
                        (float)(rnd.nextInt() % 1000 + 1000),
                        mainColor
                        );
                basePlane.SetPosition(rnd.nextInt() % 90 + 100, rnd.nextInt() % 90 + 100, panel.getWidth(), panel.getHeight());
                panel.setPlane(basePlane);
                panel.repaint();
            }
        });
        CreatePlaneBut.setBounds(40, 10, 150, 23);
        frame.getContentPane().add(CreatePlaneBut);

        JButton CreateRadarPlaneBut = new JButton("Create radar plane");
        CreateRadarPlaneBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Random rnd = new Random();
                Color mainColor =  Color.GRAY;
                Color dopColor = new Color(0,0,0);
                int countRadar = rnd.nextInt() % 3 + 1;
                boolean antenna = true;
                basePlane = new RadarPlane(
                        rnd.nextInt() % 200 + 100,
                        (float)(rnd.nextInt() % 1000 + 1000),
                        mainColor,
                        dopColor,
                        countRadar,
                        antenna);
                basePlane.SetPosition(rnd.nextInt() % 90 + 100, rnd.nextInt() % 90 + 100, panel.getWidth(), panel.getHeight());
                panel.setPlane(basePlane);
                panel.repaint();
            }
        });
        CreateRadarPlaneBut.setBounds(200, 10, 150, 23);
        frame.getContentPane().add(CreateRadarPlaneBut);
    }
}
