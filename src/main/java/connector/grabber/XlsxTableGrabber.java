package connector.grabber;

import entity.ColumnsConfig;
import entity.MaterialRecord;
import entity.TableConfig;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XlsxTableGrabber implements TableGrabber {
    @Override
    public List<MaterialRecord> getMaterialRecords(TableConfig tableConfig) {
        List<MaterialRecord> materialRecords = new ArrayList<>();
        try (
                FileInputStream fileInputStream = new FileInputStream(tableConfig.getTablePath().toFile());
                XSSFWorkbook excelBook = new XSSFWorkbook(fileInputStream)
        ) {
            XSSFSheet sheet = excelBook.getSheet(tableConfig.getSheetName());
            ColumnsConfig columnsConfig = tableConfig.getColumnsConfig();
            int nameColumnNumber = columnsConfig.getNameColumnNumber();
            int amountColumnNumber = columnsConfig.getAmountColumnNumber();
            int measureColumnNumber = columnsConfig.getMeasureColumnNumber();
            String patternToRemove = tableConfig.getPatternToRemove();
            sheet.rowIterator().forEachRemaining(row -> {
                Cell nameCell = row.getCell(nameColumnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Cell amountCell = row.getCell(amountColumnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Cell measureCell = row.getCell(measureColumnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Optional<String> rawName = getString(nameCell);
                double amount = getNumeric(amountCell).orElse(0d);
                String measure = getString(measureCell).map(str -> fixString(str, patternToRemove)).orElse("UNKNOWN");
                rawName.ifPresent(name -> {
                    String fixedName = fixString(name, tableConfig.getPatternToRemove());
                    materialRecords.add(new MaterialRecord(fixedName, name, amount, measure, tableConfig, row.getRowNum()));
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return materialRecords;
    }

    private Optional<String> getString(Cell cell) {
        return Optional.ofNullable(cell)
                .filter(c -> c.getCellType().equals(CellType.STRING))
                .map(Cell::getStringCellValue);
    }

    private Optional<Double> getNumeric(Cell cell) {
        return Optional.ofNullable(cell)
                .filter(c -> c.getCellType().equals(CellType.NUMERIC))
                .map(Cell::getNumericCellValue);
    }
}
