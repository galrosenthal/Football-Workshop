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
        initTable();
        assertTrue(table.size() == 10);
        assertTrue(table.getRecord(0).get(0).equals("rec1-0"));
    }

    private void initTable() {
        for (int i = 0; i < 10; i++) {
            ArrayList<String> record = new ArrayList<>();
            record.add("rec1-"+i);
            record.add("rec2-"+i);
            table.addRecord(record);
        }
    }

    @Test
    void size() {
        assertTrue(table.size() == 0);
    }

    @Test
    void getRecordValue() {
        assertTrue(table.size() == 0);
        initTable();
        assertTrue((table.getRecordValue(0,"col1")).equals("rec1-0"));
    }
}