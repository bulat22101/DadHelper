package entity;

import java.util.List;

public class Report {
    private final List<AmountCheckReportRecord> amountCheckRecords;
    private final List<MaterialRecord> totalMaterialRecords;
    private final List<MaterialRecord> workMaterialRecords;
    private final List<SimpleAmountCheckReportRecord> simpleAmountCheckRecords;

    public Report(List<AmountCheckReportRecord> amountCheckRecords, List<MaterialRecord> totalMaterialRecords, List<MaterialRecord> workMaterialRecords, List<SimpleAmountCheckReportRecord> simpleAmountCheckRecords) {
        this.amountCheckRecords = amountCheckRecords;
        this.totalMaterialRecords = totalMaterialRecords;
        this.workMaterialRecords = workMaterialRecords;
        this.simpleAmountCheckRecords = simpleAmountCheckRecords;
    }

    public List<AmountCheckReportRecord> getAmountCheckRecords() {
        return amountCheckRecords;
    }

    public List<MaterialRecord> getTotalMaterialRecords() {
        return totalMaterialRecords;
    }

    public List<MaterialRecord> getWorkMaterialRecords() {
        return workMaterialRecords;
    }

    public List<SimpleAmountCheckReportRecord> getSimpleAmountCheckRecords() {
        return simpleAmountCheckRecords;
    }
}
