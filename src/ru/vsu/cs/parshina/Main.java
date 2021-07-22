package ru.vsu.cs.parshina;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;
import ru.vsu.cs.parshina.util.SwingUtils;

public class Main {


    public static void winMain() throws Exception {
        Locale.setDefault(Locale.ROOT);
        //SwingUtils.setLookAndFeelByName("Windows");
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        // Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainForm().setVisible(true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] Args) throws Exception {
        CmdParams params = CmdParams.parseArgs(Args);
        ReadFile readfile = new ReadFile();
        if (Args.length == 0 || params.window) {
            winMain();
            return;
        }
        if (params.error) {
            System.out.print("Error in Input/Output stream");
        }
        if (params.help) {
            System.out.println("Usage:");
            System.out.println("Fast execute <input-file> <output-file>");
            System.out.println();
            System.out.println("--data <data-file>// add Data_flats file");
            System.out.println("-d <data-file>");
            System.out.println();
            System.out.println("--input <input-file>// add Input file");
            System.out.println("-i <input-file>");
            System.out.println();
            System.out.println("--output <output-file> // add Output file");
            System.out.println("-o <output-file>");
            System.out.println();
            System.out.println("--window // show window");
            System.out.println("-w");
            System.out.println();
            System.out.println("If there are not any input-file and output-file");
            System.out.println("It will returns error in Error Stream");
            System.out.println();
        }
        if (params.inputFile == null && params.outputFile == null && params.dataFile == null) {
            winMain();
        } else {

            List<Apartment> list = ReadFile.readInputFromFile(params.inputFile);
            List<Apartment> data = ReadFile.readListFromFile(params.dataFile);
            if (list.size() == 0) {
                System.err.println("Can't read");
                System.exit(-2);
            }
            Logical logical = new Logical();
            //Logical.Operation(data, list);
            //ReadFile.writeListToFile(params.outputFile, Logical.Operation(data, list));
        }


    }
}


