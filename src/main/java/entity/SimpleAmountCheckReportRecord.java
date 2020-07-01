package entity;

public class SimpleAmountCheckReportRecord {
    private final int totalReportRowNumber;
    private final String name;
    private final double totalReportAmount;
    private final double workReportAmount;

    public SimpleAmountCheckReportRecord(int totalReportRowNumber, String name, double totalReportAmount, double workReportAmount) {
        this.totalReportRowNumber = totalReportRowNumber;
        this.name = name;
        this.totalReportAmount = totalReportAmount;
        this.workReportAmount = workReportAmount;
    }

    public int getTotalReportRowNumber() {
        return totalReportRowNumber;
    }

    public String getName() {
        return name;
    }

    public double getTotalReportAmount() {
        return totalReportAmount;
    }

    public double getWorkReportAmount() {
        return workReportAmount;
    }
}
