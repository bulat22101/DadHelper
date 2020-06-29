package entity;

import java.nio.file.Path;

public class TableConfig {
    private final TableType tableType;
    private final ColumnsConfig columnsConfig;
    private final Path tablePath;
    //only for XLSX
    private final String sheetName;
    //only for CSV
    private final String delimiter;
    private final String patternToRemove;

    public TableConfig(TableType tableType, ColumnsConfig columnsConfig, Path tablePath, String sheetName, String delimiter, String patternToRemove) {
        this.tableType = tableType;
        this.columnsConfig = columnsConfig;
        this.tablePath = tablePath;
        this.sheetName = sheetName;
        this.delimiter = delimiter;
        this.patternToRemove = patternToRemove;
    }

    public ColumnsConfig getColumnsConfig() {
        return columnsConfig;
    }

    public Path getTablePath() {
        return tablePath;
    }

    public String getPatternToRemove() {
        return patternToRemove;
    }

    public TableType getTableType() {
        return tableType;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    @Override
    public String toString() {
        return "TableConfig{" +
                "tableType=" + tableType +
                ", columnsConfig=" + columnsConfig +
                ", tablePath=" + tablePath +
                ", sheetName='" + sheetName + '\'' +
                ", delimiter='" + delimiter + '\'' +
                ", patternToRemove='" + patternToRemove + '\'' +
                '}';
    }
}
