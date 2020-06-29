package entity;

public class ColumnsConfig {
    private final int nameColumnNumber;
    private final int amountColumnNumber;
    private final int measureColumnNumber;

    public ColumnsConfig(int nameColumnNumber, int amountColumnNumber, int measureColumnNumber) {
        this.nameColumnNumber = nameColumnNumber;
        this.amountColumnNumber = amountColumnNumber;
        this.measureColumnNumber = measureColumnNumber;
    }

    public int getNameColumnNumber() {
        return nameColumnNumber;
    }

    public int getAmountColumnNumber() {
        return amountColumnNumber;
    }

    public int getMeasureColumnNumber() {
        return measureColumnNumber;
    }

    @Override
    public String toString() {
        return "ColumnsConfig{" +
                "nameColumnNumber=" + nameColumnNumber +
                ", amountColumnNumber=" + amountColumnNumber +
                ", measureColumnNumber=" + measureColumnNumber +
                '}';
    }
}
