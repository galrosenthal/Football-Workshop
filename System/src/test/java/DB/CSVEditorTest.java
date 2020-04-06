package DB;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


class CSVEditorTest {

    @Test
    void readTableFromCSV() {
        Table table = CSVEditor.readTableFromCSV("System/resources/Tables/SystemUsers.csv");
        System.out.println(table);
    }

    @Test
    void writeTableToCSV() {
        Table table = new Table();
        ArrayList<String> titles = new ArrayList<>();
        titles.add("col1");
        titles.add("col2");
        table.setTitles(titles);
        table = TableTest.initTestTable(table);
        assertTrue(table.size() == 10);

        CSVEditor.writeTableToCSV(table, "System/resources/Tables/testTable.csv");

        File file = new File("System/resources/Tables/testTable.csv");
        assertTrue(file.exists());
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            assertTrue(fileAttributes.isRegularFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
    }
}