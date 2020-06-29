package entity;

public class MaterialRecord {
    private final String fixedName;
    private final String name;
    private final double amount;
    private final String measure;
    private final TableConfig tableConfig;
    private final int rowNumber;

    public MaterialRecord(
            String fixedName, String name, double amount, String measure, TableConfig tableConfig, int rowNumber
    ) {
        this.fixedName = fixedName;
        this.name = name;
        this.amount = amount;
        this.measure = measure;
        this.tableConfig = tableConfig;
        this.rowNumber = rowNumber;
    }

    public String getFixedName() {
        return fixedName;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}
