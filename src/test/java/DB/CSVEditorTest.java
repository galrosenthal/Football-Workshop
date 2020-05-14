package DB;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class CSVEditorTest {

    @Test
    public void readTableFromCSV() {
        Table table = CSVEditor.readTableFromCSV("src/main/resources/Tables/SystemUsers.csv");
        System.out.println(table);
    }

    @Test
    public void writeTableToCSV() {
        Table table = new Table();
        ArrayList<String> titles = new ArrayList<>();
        titles.add("col1");
        titles.add("col2");
        table.setTitles(titles);
        table = TableTest.initTestTable(table);
        assertTrue(table.size() == 10);

        CSVEditor.writeTableToCSV(table, "src/main/resources/Tables/testTable.csv");

        File file = new File("src/main/resources/Tables/testTable.csv");
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