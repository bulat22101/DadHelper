package entity;

public class AmountCheckReportRecord {

    private final String fixedName;
    private final String measure;
    private final double givenAmount;
    private final double realAmount;

    public AmountCheckReportRecord(String fixedName, String measure, double givenAmount, double realAmount) {
        this.fixedName = fixedName;
        this.measure = measure;
        this.givenAmount = givenAmount;
        this.realAmount = realAmount;
    }

    public String getFixedName() {
        return fixedName;
    }

    public String getMeasure() {
        return measure;
    }

    public double getGivenAmount() {
        return givenAmount;
    }

    public double getRealAmount() {
        return realAmount;
    }
}
