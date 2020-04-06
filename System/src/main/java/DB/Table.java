package DB;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a DB table
 */
public class Table {
    /**
     * The columns names
     */
    private List<String> titles;
    /**
     * The data records
     * each record is an ordered list of strings
     */
    private List<List<String>> table;

    /**
     * Constructor
     */
    public Table() {
        this.titles = new ArrayList<>();
        this.table = new ArrayList<>();
    }

    /**
     * Getter for the titles list
     *
     * @return - List<String> - titles
     */
    public List<String> getTitles() {
        return titles;
    }

    /**
     * Setter for the titles list
     *
     * @param titles - List<String> - An ordered list of titles
     */
    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    /**
     * Getter for the data table
     *
     * @return - List<List<String>> - The data table
     */
    public List<List<String>> getTable() {
        return table;
    }

    /**
     * Setter for the data table
     *
     * @param table - List<List<String>> - An ordered data table
     */
    public void setTable(List<List<String>> table) {
        this.table = table;
    }

    /**
     * Receives a new record and inserts it to the data table
     *
     * @param record - List<String> - A record to be added
     */
    public void addRecord(List<String> record) {
        this.table.add(record);
    }

    /**
     * Returns the size of the data table, the number of records in the dataset
     *
     * @return - int - The size of the data table
     */
    public int size() {
        return table.size();
    }

    /**
     * Returns the record in the given index
     *
     * @param index - int - the index of the desired record
     * @return - List<String> - A record
     */
    public List<String> getRecord(int index) {
        return table.get(index);
    }

    /**
     * Receives an array of key fields and an array of their values and return the FIRST record with those values.
     * @param keyFields - String[] - an array of key fields names
     * @param values - String[] - an array of key fields values
     * @return - List<String> - a record with values matching the values given, null if no record was found
     */
    public List<String> getRecord(String[] keyFields, String[] values) {
        int[] keyFieldsIndexes = new int[keyFields.length];
        for (int i = 0; i < keyFields.length; i++) {
            for (int j = 0; j < this.titles.size(); j++) {
                if (titles.get(j).equals(keyFields[i])) {
                    keyFieldsIndexes[i] = j;
                }
            }
        }

        for (int i = 0; i < table.size(); i++) {
            List<String> record = table.get(i);
            boolean matchingValues=true;
            for (int j = 0; j < keyFieldsIndexes.length; j++) {
                if(!(record.get(keyFieldsIndexes[j]).equals(values[j]))){
                    matchingValues = false;
                }
            }
            if (matchingValues){
                return record;
            }
        }
        return null;
    }


    /**
     * Receives a record index and a column name and returns the record's value of that column.
     * The title name is used to access the record value.
     * example: table.getRecordValue(<recordIndex>,<title>)
     *
     * @param recordIndex - int - the index of the desired record
     * @param title       - column name of the desired value.
     * @return - String - record's value
     */
    public String getRecordValue(int recordIndex, String title) {
        if (recordIndex >= table.size()) {
            return null;
        }
        for (int titleIndex = 0; titleIndex < titles.size(); titleIndex++) {
            if ((titles.get(titleIndex)).equals(title)) {
                return (table.get(recordIndex)).get(titleIndex);
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Table:\n");
        for (String columnName : titles) {
            stringBuilder.append(columnName + " ");
        }
        stringBuilder.append("\n");

        int tableLength = titles.size();
        for (int recordIndex = 0; recordIndex < table.size(); recordIndex++) {
            List<String> currentRecord = table.get(recordIndex);
            for (int columnIndex = 0; columnIndex < tableLength; columnIndex++) {
                stringBuilder.append(currentRecord.get(columnIndex) + " ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
