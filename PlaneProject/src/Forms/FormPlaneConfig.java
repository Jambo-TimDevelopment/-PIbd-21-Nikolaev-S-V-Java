package Forms;

import Plane.*;
import Radar.IRadar;
import Radar.RadarDefault;
import Radar.RadarOval;
import Radar.RadarSquare;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

public class FormPlaneConfig {
    public JDialog frame;
    public BasePlane plane;
    private IPlaneDelegate eventAddPlane;

    FormPlaneConfig(JFrame parentFrame) {
        initialize(parentFrame);
    }

    private void initialize(JFrame parentFrame) {
        frame = new JDialog(parentFrame);
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Добавление самолета");

        JPanel groupPanelRadar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelRadar.setBorder(BorderFactory.createTitledBorder("Тип самолета"));
        groupPanelRadar.setBounds(10, 10, 170, 85);
        frame.getContentPane().add(groupPanelRadar);

        setPlaneLabels(groupPanelRadar, "Обычный самолет");
        setPlaneLabels(groupPanelRadar, "Самолет с радаром");

        JPanel groupPanelOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelOptions.setBorder(BorderFactory.createTitledBorder("Параметры"));
        groupPanelOptions.setBounds(10, 100, 170, 240);
        frame.getContentPane().add(groupPanelOptions);

        JLabel maxSpeedLabel = new JLabel("Максимальная скорость");
        maxSpeedLabel.setBounds(10, 10, 280, 50);
        groupPanelOptions.add(maxSpeedLabel);

        JSpinner maxSpeedSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 1000, 1));
        maxSpeedSpinner.setBounds(150, 70, 50, 50);
        groupPanelOptions.add(maxSpeedSpinner);

        JLabel weightLabel = new JLabel("Вес самолета");
        weightLabel.setBounds(10, 130, 280, 50);
        groupPanelOptions.add(weightLabel);

        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 1000, 1));
        weightSpinner.setBounds(150, 190, 50, 50);
        groupPanelOptions.add(weightSpinner);

        JCheckBox checkBoxRadar = new JCheckBox("Радар");
        groupPanelOptions.add(checkBoxRadar);

        JCheckBox checkBoxAntenna = new JCheckBox("Антена");
        groupPanelOptions.add(checkBoxAntenna);

        int width = 400;
        int height = 200;
        PlanePanel planePanel = new PlanePanel();
        planePanel.setBounds(180, 10, width, height);
        planePanel.setBorder(BorderFactory.createBevelBorder(1));
        planePanel.setTransferHandler(new TransferHandler("text"));
        planePanel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                String data;

                try {
                    if (t.getTransferData(DataFlavor.stringFlavor) instanceof IRadar) {
                        if (plane != null && plane.getClass() == RadarPlane.class) {
                            ((RadarPlane) plane).setRadar((IRadar) t.getTransferData(DataFlavor.stringFlavor));
                            planePanel.repaint();
                        }
                        return true;
                    } else {
                        data = (String) t.getTransferData(DataFlavor.stringFlavor);
                    }
                } catch (Exception e) {
                    return false;
                }
                switch (data) {
                    case "Обычный самолет":
                        plane = new BasePlane((int) maxSpeedSpinner.getValue(), (int) weightSpinner.getValue(), Color.WHITE);
                        plane.SetPosition(30, 30, 150, 60);
                        planePanel.setPlane( plane);
                        break;
                    case "Самолет с радаром"://///////// исправить конструктор!!!!!
                        Random rnd = new Random();
                        int countRadar = rnd.nextInt() % 3 + 1;
                        plane = new RadarPlane(
                                (int) maxSpeedSpinner.getValue(),
                                (int) weightSpinner.getValue(),
                                Color.WHITE,
                                Color.BLUE,
                                checkBoxRadar.isSelected(),
                                3,
                                countRadar,
                                checkBoxAntenna.isSelected());
                        plane.SetPosition(30, 30, 320, 60);
                        planePanel.setPlane(plane);
                        break;
                    default:
                        return false;
                }
                planePanel.repaint();
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.isDataFlavorSupported(DataFlavor.stringFlavor) || info.getTransferable().
                            getTransferData(DataFlavor.stringFlavor) instanceof IRadar;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        frame.getContentPane().add(planePanel);

        JPanel groupPanelDoors = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelDoors.setBorder(BorderFactory.createTitledBorder("Тип радара"));
        groupPanelDoors.setBounds(planePanel.getX() + planePanel.getWidth() + 10, 10, 170, 150);
        frame.getContentPane().add(groupPanelDoors);

        setRadarLabels(groupPanelDoors, "Круглый радар", 1);
        setRadarLabels(groupPanelDoors, "Крвадратный радар", 2);
        setRadarLabels(groupPanelDoors, "Овальный радар", 3);

        JPanel groupPanelColors = new JPanel(new GridLayout(5, 2, 0, 2));
        groupPanelColors.setBorder(BorderFactory.createTitledBorder("Цвета"));
        groupPanelColors.setBounds(180, height + 10, 400, 200);
        frame.getContentPane().add(groupPanelColors);

        JLabel mainColorLabel = new JLabel("Основной цвет");
        mainColorLabel.setBorder(BorderFactory.createBevelBorder(0));
        mainColorLabel.setBounds(10, 10, 280, 50);
        mainColorLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                Color data;
                try {
                    data = (Color) t.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    return false;
                }
                if (plane != null) {
                    plane.MainColor = data;
                    planePanel.repaint();
                }
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.getTransferable().getTransferData(DataFlavor.stringFlavor).getClass() == Color.class;
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }
            }
        });
        groupPanelColors.add(mainColorLabel);

        JLabel dopColorLabel = new JLabel("Дополнительный цвет");
        dopColorLabel.setBorder(BorderFactory.createBevelBorder(0));
        dopColorLabel.setBounds(10, 70, 280, 50);
        dopColorLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                Color data;
                try {
                    data = (Color) t.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    return false;
                }
                if (plane != null) {
                    if (plane.getClass() == RadarPlane.class) {
                        ((RadarPlane) plane).setDopColor(data);
                        planePanel.repaint();
                    }
                }
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.getTransferable().getTransferData(DataFlavor.stringFlavor).getClass() == Color.class;
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }
            }
        });
        groupPanelColors.add(dopColorLabel);

        setColors(Color.BLUE, groupPanelColors);
        setColors(Color.GREEN, groupPanelColors);
        setColors(Color.RED, groupPanelColors);
        setColors(Color.YELLOW, groupPanelColors);
        setColors(Color.PINK, groupPanelColors);
        setColors(Color.ORANGE, groupPanelColors);
        setColors(Color.CYAN, groupPanelColors);
        setColors(Color.WHITE, groupPanelColors);

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {
            if (plane == null) {
                JOptionPane.showMessageDialog(frame, "Сначала создайте самолет!", "Добавление сомолета", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (eventAddPlane != null) {
                eventAddPlane.PlaneDelegate(plane);
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        addButton.setBounds(groupPanelOptions.getX(), groupPanelOptions.getY() + groupPanelOptions.getHeight() + 10, 100, 50);
        frame.add(addButton);

        JButton cancelButton = new JButton("Отменить");
        cancelButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        cancelButton.setBounds(groupPanelOptions.getX(), addButton.getY() + addButton.getHeight() + 10, 100, 50);
        frame.add(cancelButton);
    }

    private void setRadarLabels(JPanel parent, String name, int type) {
        IRadar radar;
        switch (type) {
            case 2:
                radar = new RadarSquare(type);
                break;
            case 3:
                radar = new RadarOval(type);
                break;
            default:
                radar = new RadarDefault(type);
        }
        JLabel DNDLabel = new JLabel(name);
        DNDLabel.setBorder(BorderFactory.createBevelBorder(0));
        DNDLabel.setBounds(10, 70, 280, 50);
        DNDLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) {
                        return radar;
                    }
                };
            }
        });
        DNDLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        parent.add(DNDLabel);
    }

    void setColors(Color color, JPanel groupPanelColors) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) {
                        return panel.getBackground();
                    }
                };
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        panel.setBounds(10, 10, 100, 100);
        groupPanelColors.add(panel);
    }

    void setPlaneLabels(JPanel parent, String name) {
        JLabel DNDLabel = new JLabel(name);
        DNDLabel.setBorder(BorderFactory.createBevelBorder(0));
        DNDLabel.setBounds(10, 70, 280, 50);
        DNDLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new StringSelection(((JLabel) c).getText());
            }
        });
        DNDLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        parent.add(DNDLabel);
    }

    public void addEvent(IPlaneDelegate ev) {
        eventAddPlane = ev;
    }

    public void mouseAction(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e)) {
            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }
}
