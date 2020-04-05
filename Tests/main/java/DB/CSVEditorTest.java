package main.java.DB;

import DB.CSVEditor;
import org.junit.jupiter.api.Test;





class CSVEditorTest {

    @Test
    void readListFromCSV() {
        main.java.DB.Table table = CSVEditor.readListFromCSV("System/src/main/java/DB/SystemUsers.csv");
        System.out.println(table);
    }

    @Test
    void writeListToCSV() {
    }
}