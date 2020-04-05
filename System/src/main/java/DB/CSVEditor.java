package DB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class CSVEditor {

    private static final String cvsSplitBy = ",";

    public static Table readListFromCSV(String path){

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

    public static boolean writeListToCSV(Table table, String path){
        //TODO
        return true;
    }

}



