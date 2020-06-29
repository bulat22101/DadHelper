package connector.grabber;

import entity.ColumnsConfig;
import entity.MaterialRecord;
import entity.TableConfig;
import exception.DadHelperException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CsvTableGrabber implements TableGrabber {

    @Override
    public List<MaterialRecord> getMaterialRecords(TableConfig tableConfig) throws DadHelperException {
        List<MaterialRecord> materialRecords = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(tableConfig.getTablePath().toFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String row;
            int rowNumber = 0;
            while ((row = bufferedReader.readLine()) != null) {
                String[] rowCells = row.split(tableConfig.getDelimiter());
                ColumnsConfig columnsConfig = tableConfig.getColumnsConfig();
                int nameColumnNumber = columnsConfig.getNameColumnNumber();
                int amountColumnNumber = columnsConfig.getAmountColumnNumber();
                int measureColumnNumber = columnsConfig.getMeasureColumnNumber();
                String patternToRemove = tableConfig.getPatternToRemove();
                Optional<String> rawName = getStringFromCell(rowCells, nameColumnNumber);
                Optional<String> rawAmount = getStringFromCell(rowCells, amountColumnNumber);
                Optional<String> rawMeasure = getStringFromCell(rowCells, measureColumnNumber);
                if (rawName.isPresent()) {
                    String name = rawName.get();
                    String fixedName = fixString(name, patternToRemove);
                    double amount;
                    try {
                        amount = rawAmount.map(Double::parseDouble).orElse(0d);
                    } catch (NumberFormatException e) {
                        amount = 0d;
                    }
                    String measure = rawMeasure.map(str -> fixString(str, patternToRemove)).orElse("UNKNOWN");
                    materialRecords.add(new MaterialRecord(fixedName, name, amount, measure, tableConfig, rowNumber));
                }
                rowNumber++;
            }
        } catch (IOException e) {
            throw new DadHelperException("Error while reading file " + tableConfig.getTablePath().toAbsolutePath());
        }
        return materialRecords;
    }

    private Optional<String> getStringFromCell(String[] rowCells, int columnNumber) {
        return Optional.of(columnNumber)
                .filter(cN -> 0 <= cN && cN < rowCells.length)
                .map(cN -> rowCells[cN])
                .filter(str -> !str.trim().isEmpty());
    }
}
