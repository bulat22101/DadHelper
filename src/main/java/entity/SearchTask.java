package entity;

import java.util.List;

public class SearchTask {
    private final TableConfig totalTableConfig;
    private final List<TableConfig> workTablesConfigs;

    public SearchTask(TableConfig totalTableConfig, List<TableConfig> workTablesConfigs) {
        this.totalTableConfig = totalTableConfig;
        this.workTablesConfigs = workTablesConfigs;
    }

    public TableConfig getTotalTableConfig() {
        return totalTableConfig;
    }

    public List<TableConfig> getWorkTablesConfigs() {
        return workTablesConfigs;
    }

    @Override
    public String toString() {
        return "SearchTask{" +
                "totalTableConfig=" + totalTableConfig +
                ", workTablesConfigs=" + workTablesConfigs +
                '}';
    }
}
