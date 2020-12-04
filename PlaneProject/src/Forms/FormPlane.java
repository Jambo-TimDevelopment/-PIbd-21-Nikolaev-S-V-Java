package Forms;

import Plane.BasePlane;
import Plane.Direction;
import Plane.RadarPlane;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;

public class FormPlane {

    public JFrame frame;
    private BasePlane plane;
    private PlanePanel planePanel;
    private Frame parentFrame;

    /**
     * Create the application.
     */
    public FormPlane(Frame parentFrame) {
        initialize();
        this.parentFrame = parentFrame;
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
                plane.MoveTransport(Direction.Up);
                planePanel.repaint();
            }
        });
        ImageIcon iconUp = new ImageIcon("C:\\Users\\senya\\Google Диск\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Up.png");
        MoveUpBut.setIcon(iconUp);
        MoveUpBut.setBounds(627, 404, 38, 23);
        frame.getContentPane().add(MoveUpBut);

        JButton MoveDownBut = new JButton("");
        MoveDownBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                plane.MoveTransport(Direction.Down);
                planePanel.repaint();
            }
        });
        ImageIcon iconDown = new ImageIcon("C:\\Users\\senya\\Google Диск\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Down.png");
        MoveDownBut.setIcon(iconDown);
        MoveDownBut.setBounds(627, 429, 38, 23);
        frame.getContentPane().add(MoveDownBut);

        JButton MoveLeftBut = new JButton("");
        MoveLeftBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                plane.MoveTransport(Direction.Left);
                planePanel.repaint();
            }
        });
        ImageIcon iconLeft = new ImageIcon("C:\\Users\\senya\\Google Диск\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Left.png");
        MoveLeftBut.setIcon(iconLeft);
        MoveLeftBut.setBounds(588, 429, 38, 23);
        frame.getContentPane().add(MoveLeftBut);

        JButton MoveRightBut = new JButton("");
        MoveRightBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                plane.MoveTransport(Direction.Right);
                planePanel.repaint();
            }
        });
        ImageIcon iconRight = new ImageIcon("C:\\Users\\senya\\Google Диск\\University\\ТП\\PIbd-21-Nikolaev-S-Java\\PlaneProject\\images\\Right.png");
        MoveRightBut.setIcon(iconRight);
        MoveRightBut.setBounds(666, 429, 38, 23);
        frame.getContentPane().add(MoveRightBut);

        planePanel = new PlanePanel(plane);
        planePanel.setBounds(40, 40, 660, 363);
        frame.getContentPane().add(planePanel);

        JButton btnBack;
        btnBack = new JButton( "На парковку!" );
        btnBack.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parentFrame.setVisible( true );
                frame.setVisible( false );
            }
        } );

        btnBack.setBounds( 400, 430, 156, 35 );
        frame.getContentPane().add( btnBack );
    }

    public void setPlane(BasePlane plane){
        this.plane = plane;
        Random rnd = new Random();
        plane.SetPosition( rnd.nextInt( 200 ), rnd.nextInt( 200 ),
                planePanel.getWidth(), planePanel.getHeight() );
        planePanel.setPlane( plane );
        planePanel.repaint();
    }
}
