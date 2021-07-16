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
         //   if (Character.isDigit(line.charAt(0))) {
                String[] parts = line.split(":");
                list.add(new Apartment((parts[1]), Integer.parseInt(parts[3]), Integer.parseInt(parts[5]), Integer.parseInt(parts[7]), Integer.parseInt(parts[9])));
           // }
        }
        return list;
        } catch (NumberFormatException e) {
            SwingUtils.showErrorMessageBox("ErROR", "Error", e);
        }
        return list;
    }

    public static List<ApartmentFilter> readInputFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        List<ApartmentFilter> list = new LinkedList<>();
        try {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() == 0) {
                continue;
            }
            //if (Character.isDigit(line.charAt(0))) {
                String[] parts = line.split(":");
                if (parts[0].equals("Мин.комнат")) {
                    list.add(Filters.roomsGreaterThan(Integer.parseInt(parts[1])));
                }
                if (parts[0].equals("Макс.комнат")) {
                    list.add(Filters.roomsLessThan(Integer.parseInt(parts[1])));
                }
                if (parts[0].equals("Мин.площадь")) {
                    list.add(Filters.S_generalGreaterThan(Integer.parseInt(parts[1])));
                }
                if (parts[0].equals("Макс.площадь")) {
                    list.add(Filters.S_generalLessThan(Integer.parseInt(parts[1])));
                }
                if (parts[0].equals("Мин.площадь кухни")) {
                    list.add(Filters.S_kitchenGreaterThan(Integer.parseInt(parts[1])));
                }
                if (parts[0].equals("Макс.площадь кухни")) {
                    list.add(Filters.S_kitchenLessThan(Integer.parseInt(parts[1])));
                }
                if (parts[0].equals("Мин.стоимость")) {
                    list.add(Filters.priceGreaterThan((Integer.parseInt(parts[1]))));
                }
                if (parts[0].equals("Макс.стоимость")) {
                    list.add(Filters.priceLessThan(Integer.parseInt(parts[1])));
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
                for(int i =0; i<2; i++){
                    for(int j =0; j<4; j++) {
                        if (getTrueOrFalse(tableModel, i, j)){
                            if (i==0){
                                if (j==0){
                                    list.add(Filters.roomsGreaterThan(getIntFromTableModelCell(tableModel, 0, 0)));
                                }
                                if (j==1){
                                    list.add(Filters.S_generalGreaterThan(getIntFromTableModelCell(tableModel, 0, 1)));
                                }
                                if (j==2){
                                    list.add(Filters.S_kitchenGreaterThan(getIntFromTableModelCell(tableModel, 0, 2)));
                                }
                                if (j==3){
                                    list.add(Filters.priceGreaterThan(getIntFromTableModelCell(tableModel, 0, 3)));
                                }
                            }
                            if (i==1){
                                if (j==0){
                                    list.add(Filters.roomsLessThan(getIntFromTableModelCell(tableModel, 1, 0)));
                                }
                                if (j==1){
                                    list.add(Filters.S_generalLessThan(getIntFromTableModelCell(tableModel, 1, 1)));
                                }
                                if (j==2){
                                    list.add(Filters.S_kitchenLessThan(getIntFromTableModelCell(tableModel, 1, 2)));
                                }
                                if (j==3){
                                    list.add(Filters.priceLessThan(getIntFromTableModelCell(tableModel, 1, 3)));
                                }
                            }
                        }
                    }}
                } catch (NumberFormatException e) {
                return null;
                //SwingUtils.showErrorMessageBox("ErROR", "Error", e);
            }

        return list;
    }
    public static boolean getTrueOrFalse(DefaultTableModel tableModel,int i, int j){
        return (tableModel.getValueAt(i, j)) != null;
    }
    public static int getIntFromTableModelCell(DefaultTableModel tableModel,int i, int j){
        return Integer.parseInt(String.valueOf(tableModel.getValueAt(i, j)));
    }

    public static List<Apartment> readListFromJtable(JTable table) { //прочитать из таблицы, записать в лист
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // получаем таблицу
        List<Apartment> list = new ArrayList<>();
        try {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                list.add(new Apartment(String.valueOf(tableModel.getValueAt(i, 0)), getIntFromTableModelCell(tableModel, i, 1), getIntFromTableModelCell(tableModel, i, 2),getIntFromTableModelCell(tableModel, i, 3), getIntFromTableModelCell(tableModel, i, 4)));
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
        tableModel.setRowCount(1);
            tableModel.setValueAt("S", 0, 0);
            tableModel.setValueAt("O", 0, 1);
            tableModel.setValueAt("R", 0, 2);
            tableModel.setValueAt("R", 0, 3);
            tableModel.setValueAt("Y", 0, 4);

    }

     public static void writeListOfListIntoJtable(JTable table, List<Apartment> list) { //прочитать из листа, записать в табличку
         DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
         tableModel.setColumnCount(5);
         tableModel.setRowCount(list.size());
         int i = 0;
         for(Apartment apartment: list){
                tableModel.setValueAt(apartment.getDistrict(), i, 0);
                tableModel.setValueAt(apartment.getRooms(), i, 1);
                tableModel.setValueAt(apartment.getS_general(), i, 2);
                tableModel.setValueAt(apartment.getS_kitchen(), i, 3);
                tableModel.setValueAt(apartment.getPrice(), i, 4);
                i++;
        }
    }

    public static void writeListFiltersOfListIntoJtable(JTable table, List<ApartmentFilter> list) { //прочитать из листа, записать в табличку
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnCount(4);
        tableModel.setRowCount(list.size()*2);
        int j = 0;
        for(ApartmentFilter apartment: list){
            tableModel.setValueAt(apartment, j, 0);
            tableModel.setValueAt(apartment, j, 1);
            j++;
        }
    }
    public static void writeListToFile(String file, List<Apartment> list) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        int i = 1;
        if (list.size() == 0){
            fileWriter.append("К несчастью, по вашим параметрам не найдено ни одной квартики!");
            fileWriter.close();
        } else {
            fileWriter.append("Результат:" + "\n");
            for (Apartment apartment : list) {
                fileWriter.append(i + ". ");
                fileWriter.append("Район:" + apartment.getDistrict() + " " + "Комнат:" + apartment.getRooms() + " " + "Общая площадь(м^2):" + apartment.getS_general() + " " + "Площадь кухни(м^2)" + apartment.getS_kitchen() + " " + "Стоимость(руб.):" + apartment.getPrice());
                fileWriter.append("\n");
                i++;
            }
            fileWriter.close();
        }
    }

   /* public static void printList(List<ApartmentFilter> list){
        for(int i = 0; i< list.size(); i++){
            System.out.println(list.get(i)+ "\n");
        }
    }
    public static void main(String[] args){
        try {
            List<Apartment> list = new ArrayList<>();
            list.add(new Apartment("Ляля", 12, 88, 90, 909090));
            // }
            writeListToFile("proba", list);

        } catch (Exception e) {
            System.err.println("Ошибка загруки студентов!");
            return;
        }

    }*/


}

