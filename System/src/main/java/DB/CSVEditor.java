package DB;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public class CSVEditor {

    private static final String cvsSplitBy = ",";

    public static Table readTableFromCSV(String path) {

        Table table = new Table();

        BufferedReader bufferedReader = null;
        String line = "";
        try {

            bufferedReader = new BufferedReader(new FileReader(path));
            line = bufferedReader.readLine();
            List<String> titles = Arrays.asList(line.split(cvsSplitBy));
            table.setTitles(titles);
            while ((line = bufferedReader.readLine()) != null) {
                List<String> record = Arrays.asList(line.split(cvsSplitBy));
                table.addRecord(record);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return table;
    }

    public static boolean writeTableToCSV(Table table, String path) {
        if (table == null || table.size() == 0 || path == null) {
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();

        List<String> titles = table.getTitles();
        String titlesStr = titles.get(0);
        for (int i = 1; i < titles.size(); i++) {
            titlesStr = titlesStr + "," + titles.get(i);
        }
        stringBuilder.append(titlesStr + "\n");

        for (List<String> record : table.getTable()) {
            String recordLine = record.get(0);
            for (int i = 1; i < record.size(); i++) {
                recordLine = recordLine + "," + record.get(i);
            }
            stringBuilder.append(recordLine + "\n");
        }

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}



