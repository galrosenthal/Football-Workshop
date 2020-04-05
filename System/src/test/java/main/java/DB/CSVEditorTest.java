package main.java.DB;

import DB.CSVEditor;
import DB.Table;
import org.junit.jupiter.api.Test;





class CSVEditorTest {

    @Test
    void readListFromCSV() {
        Table table = CSVEditor.readListFromCSV("System/src/main/java/DB/SystemUsers.csv");
        System.out.println(table);
    }

    @Test
    void writeListToCSV() {
    }
}