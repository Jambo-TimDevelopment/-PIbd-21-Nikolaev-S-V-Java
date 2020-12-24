package Forms;

import FileFilter.OpenFileFilter;
import Parking.ParkingCollection;
import Parking.Parking;
import Plane.APlane;
import Plane.BasePlane;
import Plane.IPlane;
import Radar.IRadar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private final DefaultListModel<String> listParkingModel = new DefaultListModel<>();
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

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        frame.setJMenuBar(menuBar);

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
            listParkingModel.addElement(key);
        }
        jListOfParking = new JList<>(listParkingModel);
        jListOfParking.setLayoutOrientation(JList.VERTICAL);
        jListOfParking.setBounds(width + 20, 90, 130, 80);
        jListOfParking.addListSelectionListener(e -> {
            if (jListOfParking.getSelectedIndex() > -1) {
                panel.setParking(collectionParking.get(listParkingModel.get(jListOfParking.getSelectedIndex())));
                panel.repaint();
            }
        });
        frame.add(jListOfParking);

        btnTakeLastPlane = new JButton("Получить последний самолет");
        btnTakeLastPlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (deletePlane.size() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Нет улетевших самолетов", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    BasePlane lastPlane = deletePlane.pop();
                    EventQueue.invokeLater(() -> {
                                FormPlane formPlane;
                                try {
                                    formPlane = new FormPlane(frame);
                                    formPlane.frame.setVisible(true);
                                    frame.setVisible(false);
                                } catch (Exception exp) {
                                    exp.printStackTrace();
                                    return;
                                }
                                formPlane.setPlane(lastPlane);
                            }
                    );
                    deletePlane.remove(lastPlane);
                }
                panel.repaint();
            }
        });
        btnTakeLastPlane.setBounds(735, 381, 215, 29);
        frame.getContentPane().add(btnTakeLastPlane);


        JButton deleteStationButton = new JButton("Удалить парковку");
        deleteStationButton.addActionListener(e -> {
            if (jListOfParking.getSelectedIndex() > -1) {
                if (JOptionPane.showConfirmDialog(frame, "Удалить парковку " + listParkingModel.get(jListOfParking.getSelectedIndex()) + "?", "Удаление", JOptionPane.OK_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {
                    collectionParking.DelParking(listParkingModel.get(jListOfParking.getSelectedIndex()));
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
                    if (collectionParking.get(listParkingModel.get(jListOfParking.getSelectedIndex())).addPlane(aPlane)) {
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Самолет не удалось поставить");
                    }
                }
            }
        });
        btnParkingBasePlane.setBounds(720, 216, 250, 30);
        frame.getContentPane().add(btnParkingBasePlane);

        textFieldGetPlace = new JTextField();
        textFieldGetPlace.setBounds(830, 299, 84, 26);
        frame.getContentPane().add(textFieldGetPlace);
        textFieldGetPlace.setColumns(10);

        JButton btnTakePlane = new JButton("Забрать");
        btnTakePlane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textFieldGetPlace.getText() != "") {
                    BasePlane plane = (BasePlane) collectionParking.
                            get(listParkingModel.get(jListOfParking.getSelectedIndex())).
                            get(Integer.parseInt(textFieldGetPlace.getText()));

                    collectionParking.get(listParkingModel.get(jListOfParking.getSelectedIndex())).
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
        listParkingModel.clear();
        for (int i = 0; i < collectionParking.keys().length; i++) {
            listParkingModel.addElement(collectionParking.keys()[i]);
        }

        if (listParkingModel.size() > 0 && (index == -1 || index >= listParkingModel.size())) {
            jListOfParking.setSelectedIndex(0);
        } else if (listParkingModel.size() > 0 && index > -1 && index < listParkingModel.size()) {
            jListOfParking.setSelectedIndex(index);
        }
    }


    private void addPlane(APlane plane) {
        if (plane != null && jListOfParking.getSelectedIndex() > -1) {
            if (collectionParking.get(listParkingModel.get(jListOfParking.getSelectedIndex())).addPlane(plane)) {
                panel.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Машину не удалось поставить");
            }
        }
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("Файл");
        JMenuItem save = new JMenuItem("Сохранить один аэропорт");
        file.add(save);
        JMenuItem saveAll = new JMenuItem("Сохранить всё");
        file.add(saveAll);
        JMenuItem load = new JMenuItem("Загрузить один аэропорт");
        file.add(load);
        JMenuItem loadAll = new JMenuItem("Загрузить всё");
        file.add(loadAll);

        saveAll.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.SAVE);
            if (filename == null) {
                return;
            }
            if (collectionParking.SaveAllData(filename)) {
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "При сохранении произошла ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        save.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.SAVE);
            if (filename == null) {
                return;
            }
            if (jListOfParking.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(frame, "Укажите аэропорт, ", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (collectionParking.SaveData(filename, listParkingModel.get(jListOfParking.getSelectedIndex()))) {
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "При сохранении произошла ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        loadAll.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.LOAD);
            if (filename == null) {
                return;
            }
            if (collectionParking.LoadAllData(filename)) {
                JOptionPane.showMessageDialog(frame, "Загрузилось успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
                ReloadParking();
                if (listParkingModel.size() > 0) {
                    panel.setParking(collectionParking.get(listParkingModel.get(0)));
                    panel.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "При загрузке произошла ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        load.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.LOAD);
            if (filename == null) {
                return;
            }
            if (collectionParking.LoadData(filename)) {
                JOptionPane.showMessageDialog(frame, "Загрузка прошла успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
                listParkingModel.clear();
                for (String key : collectionParking.keys()) {
                    listParkingModel.addElement(key);
                }
                if (listParkingModel.size() > 0) {
                    panel.setParking(collectionParking.get(listParkingModel.get(0)));
                    panel.repaint();
                }
                panel.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "При загрузке произошла ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return file;
    }

    private String fileDialogSetup(int type) {
        File filename;
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new OpenFileFilter(".txt"));
        int returnVal;
        if (type == 0) {
            returnVal = chooser.showOpenDialog(frame);
        } else {
            returnVal = chooser.showSaveDialog(frame);
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile();
            return filename.getAbsolutePath();
        }
        return null;
    }
}

