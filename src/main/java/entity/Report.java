package entity;

import java.util.List;

public class Report {
    private final List<AmountCheckReportRecord> amountCheckRecords;
    private final List<MaterialRecord> totalMaterialRexords;
    private final List<MaterialRecord> workMaterialRecords;

    public Report(List<AmountCheckReportRecord> amountCheckRecords, List<MaterialRecord> totalMaterialRexords, List<MaterialRecord> workMaterialRecords) {
        this.amountCheckRecords = amountCheckRecords;
        this.totalMaterialRexords = totalMaterialRexords;
        this.workMaterialRecords = workMaterialRecords;
    }

    public List<AmountCheckReportRecord> getAmountCheckRecords() {
        return amountCheckRecords;
    }

    public List<MaterialRecord> getTotalMaterialRexords() {
        return totalMaterialRexords;
    }

    public List<MaterialRecord> getWorkMaterialRecords() {
        return workMaterialRecords;
    }
}
