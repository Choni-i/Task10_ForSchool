package ru.vsu.cs.parshina;

public class CmdParams {  // создаем класс, который хранит параметры
    String dataFile;
    String inputFile;
    String outputFile;
    public boolean error = false; // ошибка выводится из функции parseArgs
    boolean help;
    boolean window;

    public static CmdParams parseArgs(String[] args) { // спорный момент про вывод ошибки
        CmdParams params = new CmdParams(); // создает объект, чтобы изменять параметры
        if (args.length == 0) {
            params.window = true;
            return params;
        }
        if (args[0].endsWith(".txt") && args[1].endsWith(".txt") && args[2].endsWith(".txt")) {
            params.dataFile = args[0];
            params.inputFile = args[1];
            params.outputFile = args[2];
            return params;
        }
        int ArgsLength = args.length; // запоминаем 1 раз, чтобы лишний раз не обращаться
        for (int i = 0; i < ArgsLength; i++) {   // тут много лишних проверок, на зато тут не важен порядок указания параметров
            if (args[i].equals("--window") || args[i].equals("-w")) {
                params.window = true;
                return params;
            }
            if (args[i].equals("-h") || args[i].equals("--help")) params.help = true;
            if (args[i].equals("-d") || args[i].equals("--Data_flats")) {
                if (i + 1 == ArgsLength || !args[i + 1].endsWith(".txt")) {
                    params.error = true;
                }
                params.inputFile = args[i + 1];
            }
            if (args[i].equals("-i") || args[i].equals("--input")) {
                if (i + 1 == ArgsLength || !args[i + 1].endsWith(".txt")) {
                    params.error = true;
                }
                params.inputFile = args[i + 1];
            }
            if (args[i].equals("-o") || args[i].equals("--output")) {
                if (i + 1 > ArgsLength || !args[i + 1].endsWith(".txt")) {
                    params.error = true;
                }
                params.outputFile = args[i + 1];
            }

        }
        if (params.inputFile == null || params.outputFile == null || params.dataFile == null) {
            params.error = true;
        }

        return params; // возвращаем объект с параметрами
    }

}