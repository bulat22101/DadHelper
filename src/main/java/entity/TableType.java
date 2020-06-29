package entity;

public enum TableType {
    CSV,
    XLSX;

    public static TableType getTableType(String tablePath){
        if(tablePath.matches(".*\\.xlsx")){
            return XLSX;
        }
        if(tablePath.matches(".*\\.csv")){
            return CSV;
        }
        throw new IllegalArgumentException();
    }
}
