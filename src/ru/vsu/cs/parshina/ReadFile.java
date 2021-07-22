package ru.vsu.cs.parshina;
import ru.vsu.cs.parshina.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class ReadFile {


    public static List<Apartment> readListFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        List<Apartment> list = new ArrayList<>();
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() == 0) {
                    continue;
                }
                String[] parts = line.split(" ");
                if (parts[0].equals("//")) {
                    continue;
                }
                list.add(new Apartment((parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
            }
            return list;
        } catch (NumberFormatException e) {
            SwingUtils.showErrorMessageBox("ErROR", "Error", e);
        }
        return list;
    }

    public static List<Apartment> readInputFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        List<Apartment> list = new LinkedList<>();
        Apartment apartmentMin = null;
        Apartment apartmentMax = null;
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() == 0) {
                    continue;
                }
                //if (Character.isDigit(line.charAt(0))) {
                String[] parts = line.split(":");
                if (parts[0].equals("Мин.комнат")) {
                    //assert false;
                    apartmentMin.setRooms(Integer.parseInt(parts[1]));
                }
                if (parts[0].equals("Макс.комнат")) {
                    apartmentMax.setRooms(Integer.parseInt(parts[1]));
                }
                if (parts[0].equals("Мин.площадь")) {
                    apartmentMin.setS_general(Integer.parseInt(parts[1]));
                }
                if (parts[0].equals("Макс.площадь")) {
                    apartmentMax.setS_general(Integer.parseInt(parts[1]));
                }
                if (parts[0].equals("Мин.площадь кухни")) {
                    apartmentMin.setS_kitchen(Integer.parseInt(parts[1]));
                }
                if (parts[0].equals("Макс.площадь кухни")) {
                    apartmentMax.setS_kitchen(Integer.parseInt(parts[1]));
                }
                if (parts[0].equals("Мин.стоимость")) {
                    apartmentMin.setPrice(Integer.parseInt(parts[1]));
                }
                if (parts[0].equals("Макс.стоимость")) {
                    apartmentMax.setPrice(Integer.parseInt(parts[1]));
                }
                // }
            }
        } catch (NumberFormatException e) {
            SwingUtils.showErrorMessageBox("ErROR", "Error", e);
        }
        return list;
    }

    public static List<ApartmentFilter> readListFiltersFromJtable(JTable table) { //прочитать из таблицы, записать в лист
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // получаем таблицу
        List<ApartmentFilter> list = new ArrayList<>();
        try {
            for (int i = 1; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (getTrueOrFalse(tableModel, i, j)) {
                        if (i == 1) {
                            if (j == 0) {
                                list.add(Filters.roomsGreaterThan(getIntFromTableModelCell(tableModel, 1, 0)));
                            }
                            if (j == 1) {
                                list.add(Filters.S_generalGreaterThan(getIntFromTableModelCell(tableModel, 1, 1)));
                            }
                            if (j == 2) {
                                list.add(Filters.S_kitchenGreaterThan(getIntFromTableModelCell(tableModel, 1, 2)));
                            }
                            if (j == 3) {
                                list.add(Filters.priceGreaterThan(getIntFromTableModelCell(tableModel, 1, 3)));
                            }
                        }
                        if (i == 2) {
                            if (j == 0) {
                                list.add(Filters.roomsLessThan(getIntFromTableModelCell(tableModel, 2, 0)));
                            }
                            if (j == 1) {
                                list.add(Filters.S_generalLessThan(getIntFromTableModelCell(tableModel, 2, 1)));
                            }
                            if (j == 2) {
                                list.add(Filters.S_kitchenLessThan(getIntFromTableModelCell(tableModel, 2, 2)));
                            }
                            if (j == 3) {
                                list.add(Filters.priceLessThan(getIntFromTableModelCell(tableModel, 2, 3)));
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            return null;
            //SwingUtils.showErrorMessageBox("ErROR", "Error", e);
        }

        return list;
    }

    public static boolean getTrueOrFalse(DefaultTableModel tableModel, int i, int j) {
        return (tableModel.getValueAt(i, j)) != null || (tableModel.getValueAt(i, j)) != "";
    }

    public static int getIntFromTableModelCell(DefaultTableModel tableModel, int i, int j) {
        return Integer.parseInt(String.valueOf(tableModel.getValueAt(i, j)));
    }

    public static List<Apartment> readListFromJtable(JTable table) { //прочитать из таблицы, записать в лист
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // получаем таблицу
        List<Apartment> list = new ArrayList<>();
        try {
            for (int i = 1; i < tableModel.getRowCount(); i++) {
                list.add(new Apartment(String.valueOf(tableModel.getValueAt(i, 0)), getIntFromTableModelCell(tableModel, i, 1), getIntFromTableModelCell(tableModel, i, 2), getIntFromTableModelCell(tableModel, i, 3), getIntFromTableModelCell(tableModel, i, 4)));
            }
            return list;
        } catch (NumberFormatException e) {
            return null;
            //SwingUtils.showErrorMessageBox("ErROR", "Error", e);
        }
    }

    public static void writeJtableSorry(JTable table) { //если не нашлось квартиры по заданным условиям
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnCount(5);
        tableModel.setRowCount(2);
        tableModel.setValueAt("Район", 0, 0);
        tableModel.setValueAt("Кол-во комнат", 0, 1);
        tableModel.setValueAt("Площадь(м²)", 0, 2);
        tableModel.setValueAt("Площадь кухни(м²)", 0, 3);
        tableModel.setValueAt("Стоимость(руб.)", 0, 4);
        for (int i = 0; i < 5; i++) {
            tableModel.setValueAt("-", 1, i);
        }
    }

    public static void writeListOfListIntoJtable(JTable table, List<Apartment> list) { //прочитать из листа, записать в табличку
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnCount(5);
        tableModel.setRowCount(list.size() + 1);
        int i = 1;
        tableModel.setValueAt("Район", 0, 0);
        tableModel.setValueAt("Кол-во комнат", 0, 1);
        tableModel.setValueAt("Площадь(м²)", 0, 2);
        tableModel.setValueAt("Площадь кухни(м²)", 0, 3);
        tableModel.setValueAt("Стоимость(руб.)", 0, 4);
        for (Apartment apartment : list) {
            tableModel.setValueAt(apartment.getDistrict(), i, 0);
            tableModel.setValueAt(apartment.getRooms(), i, 1);
            tableModel.setValueAt(apartment.getS_general(), i, 2);
            tableModel.setValueAt(apartment.getS_kitchen(), i, 3);
            tableModel.setValueAt(apartment.getPrice(), i, 4);
            i++;
        }
    }

    public static void writeFiltersListOfListIntoJtable(JTable table, List<Apartment> list) { //прочитать из листа, записать в табличку
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnCount(5);
        int i = 1;
        for (Apartment apartment : list) {
            tableModel.setValueAt(apartment.getRooms(), i, 0);
            tableModel.setValueAt(apartment.getS_general(), i, 1);
            tableModel.setValueAt(apartment.getS_kitchen(), i, 2);
            tableModel.setValueAt(apartment.getPrice(), i, 3);
            i++;
        }
    }



    public static void writeListFilters(JTable table) { //написать название стобцов
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnCount(5);
        tableModel.setRowCount(3);
        tableModel.setValueAt("Кол-во комнат", 0, 0);
        tableModel.setValueAt("Площадь(м²)", 0, 1);
        tableModel.setValueAt("Площадь кухни(м²)", 0, 2);
        tableModel.setValueAt("Стоимость(руб.)", 0, 3);
        tableModel.setValueAt("Минимум", 1, 4);
        tableModel.setValueAt("Максимум", 2, 4);
    }

    public static void writeListToFile(String file, List<Apartment> list) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        int i = 1;
        if (list.size() == 0) {
            fileWriter.append("К несчастью, по вашим параметрам не найдено ни одной квартики!");
            fileWriter.close();
        } else {
            fileWriter.append("Результат:" + "\n");
            for (Apartment apartment : list) {
                fileWriter.append(String.valueOf(i)).append(". ");
                fileWriter.append("Район:").append(apartment.getDistrict()).append(", ").append("Комнат:").append(String.valueOf(apartment.getRooms())).append(", ").append("Общая площадь(м²):").append(String.valueOf(apartment.getS_general())).append(", ").append("Площадь кухни(м²):").append(String.valueOf(apartment.getS_kitchen())).append(", ").append("Стоимость(руб.):").append(String.valueOf(apartment.getPrice())).append(".");
                fileWriter.append("\n");
                i++;
            }
            fileWriter.close();
        }
    }



}

