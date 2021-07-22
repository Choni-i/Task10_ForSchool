package ru.vsu.cs.parshina;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ru.vsu.cs.parshina.util.*;

public class MainForm extends JFrame {

    private JPanel MainPanel;
    private JTable InputTable;

    private JButton GetFromFileButton;
    private JPanel DataButtonPanel;
    private JButton GetRandomNumbersButton;
    private JButton ExecuteButton;
    private JLabel Output;
    private JPanel ExecutePanel;
    private JScrollPane InputPanel;
    private JPanel SaveButtonPanel;
    private JButton SaveIntoFileButton;
    private JTable OutputTable;
    private JScrollPane OutputPanel;
    private JScrollPane InputDataPanel;
    private JTable DataTable;
    private JButton DataButton;


    private JFileChooser fileChooserOpen;  // выбор директории
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;  // выбор меню, но он удален


    public MainForm() throws FileNotFoundException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 600);
        this.setTitle("Демо-версия циан");
        this.setLocationRelativeTo(null);
        // this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.pack();
        JTableUtils.initJTableForArray(InputTable, 110, true, false, false, false);
        JTableUtils.initJTableForArray(DataTable, 110, true, false, false, false);
        JTableUtils.initJTableForArray(OutputTable, 110, true, false, false, false);

        JTableUtils.resizeJTable(InputTable, 3, 5, 25, 175);
        JTableUtils.resizeJTable(DataTable, 2, 5, 25, 175);
        JTableUtils.resizeJTable(OutputTable, 2, 5, 25, 175);

        ReadFile.writeListFilters(InputTable);
        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);
        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");


        DataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReadFile readfile = new ReadFile();
                try {
                    if (fileChooserOpen.showOpenDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        List<Apartment> data = readfile.readListFromFile(fileChooserOpen.getSelectedFile().getPath());
                        JTableUtils.resizeJTable(DataTable, data.size() + 1, 5, -1, 175);
                        ReadFile.writeListOfListIntoJtable(DataTable, data);

                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });


        GetFromFileButton.addActionListener(new ActionListener() {  // прочитать из файла и записать в таблицу
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReadFile readfile = new ReadFile();
                try {
                    if (fileChooserOpen.showOpenDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        Logical logical = new Logical();
                        List<Apartment> filterList = ReadFile.readListFromFile(fileChooserOpen.getSelectedFile().getPath());
                        ReadFile.writeFiltersListOfListIntoJtable(DataTable, filterList);}
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Выберите корректный файл.", "Некорректные данные", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        ExecuteButton.addActionListener(new ActionListener() {  // Прочитать из таблицы, посчитать, записать в таблицу
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReadFile readfile = new ReadFile();
                try {
                    List<Apartment> data = ReadFile.readListFromJtable(DataTable);
                    Logical logical = new Logical();
                    //ReadFile.writeListFilters(InputTable);

                    List<ApartmentFilter> filterList = ReadFile.readListFiltersFromJtable(InputTable);
                    List<Apartment> result = Logical.Operation(data, filterList);
                    if (result.size() == 0) {
                        ReadFile.writeJtableSorry(OutputTable);
                    } else {
                        JTableUtils.resizeJTable(OutputTable, result.size() + 1, 5, -1, 175);
                        ReadFile.writeListOfListIntoJtable(OutputTable, result);

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Введите число", "Некорректные данные", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        SaveIntoFileButton.addActionListener(new ActionListener() { // прочитать из таблицы, записать в файл
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReadFile readfile = new ReadFile();
                try {
                    List<ApartmentFilter> list = ReadFile.readListFiltersFromJtable(InputTable);
                    List<Apartment> data = ReadFile.readListFromJtable(DataTable);
                    if (fileChooserSave.showSaveDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        Logical logical = new Logical();
                        ReadFile.writeListToFile(file, Logical.Operation(data, list));
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }

    private void createUIComponents() {


    }

}
