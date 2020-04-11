package DB;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a DB table
 */
public class Table {
    // Just setting the Table columns names so we will never miss a letter
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String NAME = "name";
    public final static String ROLE = "role";



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
     * Adds a value to a specific record in a specific column
     * @param recIndex the index of the record to change
     * @param colTitle  the title of the column to change
     * @param valueToAdd the value to add
     * @return true if the insertion succeeded
     */
    public boolean addValueToRecordInSpecificCol(int recIndex, String colTitle, String valueToAdd)
    {
        int titleIndex = titles.indexOf(colTitle);
        if(titleIndex < 0)
        {
            return false;
        }
        List<String> recordDetails = table.get(recIndex);
        String record = recordDetails.get(titleIndex);

        recordDetails.remove(titleIndex);
        table.get(recIndex).add(titleIndex,record+";"+valueToAdd);


        return true;

    }

    /**
     * Removes a value from a specific record in a specific column
     * @param recIndex the index of the record to change
     * @param colTitle  the title of the column to change
     * @param valueToRemove the value to add
     * @return true if the removal succeeded
     */
    public boolean removeValueFromRecordInSpecificCol(int recIndex, String colTitle, String valueToRemove)
    {
        int titleIndex = titles.indexOf(colTitle);
        if(titleIndex < 0)
        {
            return false;
        }
        List<String> recordDetails = table.get(recIndex);
        String record = recordDetails.get(titleIndex);

        String[] splittedRecord = record.split(";");
        StringBuilder newValue = new StringBuilder();

        boolean isValueAdded = false;
        for(String value : splittedRecord)
        {
            if(!value.equalsIgnoreCase(valueToRemove))
            {
                if(newValue.length() != 0)
                {
                    newValue.append(";");
                }
                newValue.append(value);
            }

        }

        recordDetails.remove(titleIndex);
        table.get(recIndex).add(titleIndex,newValue.toString());


        return true;

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

    public Table getRecords(String[] keyFields, String[] values) {
        Table subTable = new Table();
        subTable.setTitles(this.getTitles());


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
                subTable.addRecord(record);
            }
        }
        return subTable;
    }

    public boolean updateRecord(int recordIndex,String colTitle,String valueToAdd){
        int indexTitle = titles.indexOf(colTitle);
        String record =table.get(recordIndex).get(indexTitle);
        record += +';'+ valueToAdd;
        //table.get(recordIndex).get(indexTitle) = record;
        return  false;
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
            stringBuilder.append(columnName + "|");
        }
        stringBuilder.append("\n");

        int tableLength = titles.size();
        for (int recordIndex = 0; recordIndex < table.size(); recordIndex++) {
            List<String> currentRecord = table.get(recordIndex);
            for (int columnIndex = 0; columnIndex < tableLength; columnIndex++) {
                stringBuilder.append(currentRecord.get(columnIndex) + "|");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
