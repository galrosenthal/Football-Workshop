package DB;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import javafx.scene.control.Tab;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


//import static org.junit.jupiter.api.Assertions.*;

public class TableTest {
    private Table table;

    @Before
    public void setUp() {
        table = new Table();
        ArrayList<String> titles = new ArrayList<>();
        titles.add("col1");
        titles.add("col2");
        table.setTitles(titles);
    }

    protected static Table initTestTable(Table table) {
        for (int i = 0; i < 10; i++) {
            ArrayList<String> record = new ArrayList<>();
            record.add("rec1-" + i);
            record.add("rec2-" + i);
            table.addRecord(record);
        }
        return table;
    }

    @Test
    public void addRecordUTest() {
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

    @Test
    public void getTitlesUTest() {
        assertTrue(this.table.getTitles().get(0).equals("col1"));
    }

    @Test
    public void setTableUTest() {
        initTestTable(this.table);

        Table testTable = new Table();
        ArrayList<String> titles = new ArrayList<>();
        titles.add("testCol1");
        titles.add("testCol2");
        testTable.setTitles(titles);

        //testTable's table is empty
        assertFalse(testTable.getTable().equals(this.table.getTable()));
        this.table.setTable(testTable.getTable());
        //both tables are empty
        assertTrue(testTable.getTable().equals(this.table.getTable()));
    }

    @Test
    public void sizeUTest() {
        assertTrue(table.size() == 0);
    }

    @Test
    public void getRecordValueUTestUTest() {
        assertTrue(table.size() == 0);
        table = initTestTable(table);
        assertTrue((table.getRecordValue(0, "col1")).equals("rec1-0"));
    }

    @Test
    public void getRecordValue2UTestUTest() {
        table = initTestTable(table);
        //The given recordIndex is bigger then table.size()
        assertTrue((table.getRecordValue(10, "col1")) == null);
        //The given column title doesn't exists
        assertTrue((table.getRecordValue(9, "not a title")) == null);
    }

    @Test
    public void getRecordUTest() {
        assertTrue(table.size() == 0);
        table = initTestTable(table);
        assertTrue(table.size() == 10);

        List<String> record = table.getRecord(new String[]{"col1"}, new String[]{"rec1-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col2"}, new String[]{"rec2-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col1", "col2"}, new String[]{"rec1-0", "rec2-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col2", "col1"}, new String[]{"rec2-0", "rec1-0"});
        assertTrue(record.get(1).equals("rec2-0"));
        record = table.getRecord(new String[]{"col2", "col1"}, new String[]{"rec2-1", "rec1-1"});
        assertTrue(record.get(1).equals("rec2-1"));
        record = table.getRecord(new String[]{"col1"}, new String[]{"WRONG"});
        assertNull(record);
    }

    @Test
    public void getRecordsUTest() {
        table = initTestTable(table);
        Table subTable = table.getRecords(new String[]{"col1"}, new String[]{"rec1-0"});
        //subTable should have a single entry
        assertTrue(subTable.size() == 1);
        assertTrue(subTable.getTitles().equals(table.getTitles()));
        assertTrue(subTable.getRecord(0).get(1).equals("rec2-0"));
        //adding a new matching record
        ArrayList<String> record = new ArrayList<>();
        record.add("rec1-0");
        record.add("newRec-col2");
        table.addRecord(record);
        //subTable should have two entries
        subTable = table.getRecords(new String[]{"col1"}, new String[]{"rec1-0"});
        assertTrue(subTable.size() == 2);
        assertTrue(subTable.getRecord(0).get(1).equals("rec2-0"));
        assertTrue(subTable.getRecord(1).get(1).equals("newRec-col2"));

    }

    @Test
    public void getRecords2UTest() {
        table = initTestTable(table);
        Table subTable = table.getRecords(new String[]{"not a col"}, new String[]{"rec1-0"});
        //subTable should have a single entry
        assertTrue(subTable == null);
    }

    @Test
    public void getRecordValue3UTest() {
        table = initTestTable(table);
        String recordValue = table.getRecordValue(new String[]{"not a title"}, new String[]{"rec1-0"}, "col2");
        assertTrue(recordValue == null);
        recordValue = table.getRecordValue(new String[]{"col1"}, new String[]{"not a value"}, "col2");
        assertTrue(recordValue == null);
        recordValue = table.getRecordValue(new String[]{"col1"}, new String[]{"rec1-0"}, "not a title");
        assertTrue(recordValue == null);

    }

    @Test
    public void getRecordValue4UTest() {
        table = initTestTable(table);
        String recordValue = table.getRecordValue(new String[]{"col1"}, new String[]{"rec1-0"}, "col2");
        assertTrue(recordValue.equals("rec2-0"));
    }

    @Test
    public void addValueToRecordUTest() {
        assertEquals(0, table.size());
        table = initTestTable(table);
        assertEquals(10, table.size());

        assertFalse(table.addValueToRecordInSpecificCol(0, "COL1", "12345"));

        assertTrue(table.addValueToRecordInSpecificCol(0, "col1", "12345"));
        assertEquals("rec1-0;12345", table.getTable().get(0).get(0));

    }

    @Test
    public void RemoveValueFromRecordUTest() {
        assertEquals(0, table.size());
        table = initTestTable(table);
        assertEquals(10, table.size());

        assertTrue(table.addValueToRecordInSpecificCol(0, "col1", "12345"));
        assertEquals("rec1-0;12345", table.getTable().get(0).get(0));

        assertFalse(table.removeValueFromRecordInSpecificCol(0, "COL1", "12345"));
        assertTrue(table.removeValueFromRecordInSpecificCol(0, "col1", "12345"));
        assertEquals("rec1-0", table.getTable().get(0).get(0));

        assertTrue(table.addValueToRecordInSpecificCol(0, "col1", "123"));
        assertTrue(table.addValueToRecordInSpecificCol(0, "col1", "456"));
        assertTrue(table.removeValueFromRecordInSpecificCol(0, "col1", "123"));
        assertEquals("rec1-0;456", table.getTable().get(0).get(0));

    }

    @Test
    public void toStringUTest() {
        assertFalse(table.toString().isEmpty());
        System.out.println(table);

        table = initTestTable(table);
        assertTrue(table.toString().contains("|"));
    }
}