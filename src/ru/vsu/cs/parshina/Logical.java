package ru.vsu.cs.parshina;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Logical {

    public static List<Apartment> Operation(List<Apartment> list, List<ApartmentFilter> filterList) {
        List<Apartment> result = new ArrayList<>();
        boolean flag = false;
        for (Apartment apartment: list){
            for (ApartmentFilter filter: filterList) {
                if (filter.runFilter(apartment)) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) { result.add(apartment);}
        }
        return result;
    }
    public static List<Integer> readListFromFile(String path) throws FileNotFoundException, NullPointerException, InputMismatchException {
        try (Scanner scanner = new Scanner(new File(path))) {
            List<Integer> list = new ArrayList<>();
            scanner.useDelimiter("(\\s|[,;])+");
            while (scanner.hasNext()) {
                list.add(scanner.nextInt());
            }
            return list;
        }
    }

    public static void writeListToFile(String path, List<Integer> outPut) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(path)) {
            for (Integer a : outPut) {
                pw.print(a + " ");
            }
        }
    }
    /*public static List<Integer> intArrayToList(int[] arr) {
        return Arrays.stream(arr).boxed().collect(Collectors.toList());
    }

    public static int[] intListToArray(List<Integer> list) {

        return list.stream().mapToInt(i -> i).toArray();
    }*/

}
