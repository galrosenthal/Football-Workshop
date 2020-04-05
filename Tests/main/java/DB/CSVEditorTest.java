package main.java.DB;

import DB.CSVEditor;
import org.junit.jupiter.api.Test;





class CSVEditorTest {

    @Test
    void readListFromCSV() {
        main.java.DB.Table tadble = CSVEditor.readListFromCSV("System/src/main/java/DB/SystemUsers.csv");
    }

    @Test
    void writeListToCSV() {
    }
}