package DB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Table table;

    @BeforeEach
    void setUp() {
        table = new Table();
        ArrayList<String> titles = new ArrayList<>();
        titles.add("col1");
        titles.add("col2");
        table.setTitles(titles);
    }

    @Test
    void addRecord() {
        assertTrue(table.size() == 0);
        table = initTestTable(table);
        assertTrue(table.size() == 10);
        ArrayList<String> record = new ArrayList<>();
        record.add("rec1-10");
        record.add("rec2-10");
        table.addRecord(record);
        assertTrue(table.size() == 11);
        assertTrue(table.getRecord(10).get(0).equals("rec1-10"));
    }

    protected static Table initTestTable(Table table) {
        for (int i = 0; i < 10; i++) {
            ArrayList<String> record = new ArrayList<>();
            record.add("rec1-"+i);
            record.add("rec2-"+i);
            table.addRecord(record);
        }
        return table;
    }

    @Test
    void size() {
        assertTrue(table.size() == 0);
    }

    @Test
    void getRecordValue() {
        assertTrue(table.size() == 0);
        table = initTestTable(table);
        assertTrue((table.getRecordValue(0,"col1")).equals("rec1-0"));
    }

    @Test
    void getRecord() {
        assertTrue(table.size() == 0);
        table = initTestTable(table);
        assertTrue(table.size() == 10);

        List<String> record = table.getRecord(new String[]{"col1"},new String[]{"rec1-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col2"},new String[]{"rec2-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col1","col2"},new String[]{"rec1-0","rec2-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col2","col1"},new String[]{"rec2-0","rec1-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col2","col1"},new String[]{"rec2-1","rec1-1"});
        assertTrue(record.get(1).equals("rec2-1"));
        record = table.getRecord(new String[]{"col1"},new String[]{"WRONG"});
        assertNull(record);
    }

}