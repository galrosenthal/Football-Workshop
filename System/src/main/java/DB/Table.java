package main.java.DB;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> titles;
    private List<List<String>> table;

    public Table() {
        this.titles = new ArrayList<>();
        this.table = new ArrayList<>();
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<List<String>> getTable() {
        return table;
    }

    public void setTable(List<List<String>> table) {
        this.table = table;
    }

    public void addRecord(List<String> record){
        this.table.add(record);
    }

    public int size(){
        return table.size();
    }

    public List<String> getRecord(int index){
        return table.get(index);
    }


    //TODO: The titles should be used to access the record value. exp.: table.getRecordValue(<recordIndex>,<title>)
    public String getRecordValue(int recordIndex, String title){
        if(recordIndex >= table.size()){
            return null;
        }
        for (int titleIndex = 0; titleIndex < titles.size(); titleIndex++) {
            if((titles.get(titleIndex)).equals(title)){
                return (table.get(recordIndex)).get(titleIndex);
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Table:\n");
        for (String columnName:titles) {
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
