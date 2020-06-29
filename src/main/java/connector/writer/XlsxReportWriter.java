package connector.writer;

import entity.AmountCheckReportRecord;
import entity.MaterialRecord;
import entity.Report;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class XlsxReportWriter implements ReportWriter {
    private final static List<String> MATERIAL_RECORD_HEADERS = Arrays.asList(
            "Нормированное наименование",
            "Оригинальное наименование",
            "Ед. изм.",
            "Количество",
            "Файл",
            "Лист",
            "Номер строки"
    );
    private final static List<String> AMOUNT_CHECK_REPORT_RECORD_HEADERS = Arrays.asList(
            "Нормированное наименование",
            "Ед. изм.",
            "Кол. из ОДМ",
            "Кол. из КС-ок"
    );

    @Override
    public String createAndSaveReport(Report report) {
        String fileName = generateFilename();
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                Workbook workbook = new XSSFWorkbook()
        ) {
            writeAmountCheckReportRecords(workbook.createSheet("Проверка"), report.getAmountCheckRecords());
            writeMaterialRecords(workbook.createSheet("Записи из ОДМ"), report.getTotalMaterialRexords());
            writeMaterialRecords(workbook.createSheet("Записи из КС-ок"), report.getWorkMaterialRecords());
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    private void writeMaterialRecords(Sheet sheet, List<MaterialRecord> materialRecords) {
        writeHeaders(sheet.createRow(0), MATERIAL_RECORD_HEADERS);
        IntStream.rangeClosed(1, materialRecords.size())
                .forEach(rowN -> writeMaterialRecord(sheet.createRow(rowN), materialRecords.get(rowN - 1)));
    }

    private void writeAmountCheckReportRecords(Sheet sheet, List<AmountCheckReportRecord> records) {
        writeHeaders(sheet.createRow(0), AMOUNT_CHECK_REPORT_RECORD_HEADERS);
        IntStream.rangeClosed(1, records.size())
                .forEach(rowN -> writeAmountCheckReportRecord(sheet.createRow(rowN), records.get(rowN - 1)));
    }

    private void writeHeaders(Row headersRow, List<String> headers) {
        IntStream.range(0, headers.size())
                .forEach(i -> {
                    Cell headerCell = headersRow.createCell(i, CellType.STRING);
                    headerCell.setCellValue(headers.get(i));
                });
    }

    private void writeAmountCheckReportRecord(Row row, AmountCheckReportRecord record) {
        row.createCell(0, CellType.STRING).setCellValue(record.getFixedName());
        row.createCell(1, CellType.STRING).setCellValue(record.getMeasure());
        row.createCell(2, CellType.NUMERIC).setCellValue(record.getGivenAmount());
        row.createCell(3, CellType.NUMERIC).setCellValue(record.getRealAmount());
    }

    private void writeMaterialRecord(Row row, MaterialRecord record) {
        row.createCell(0, CellType.STRING).setCellValue(record.getFixedName());
        row.createCell(1, CellType.STRING).setCellValue(record.getName());
        row.createCell(2, CellType.STRING).setCellValue(record.getMeasure());
        row.createCell(3, CellType.NUMERIC).setCellValue(record.getAmount());
        row.createCell(4, CellType.STRING).setCellValue(record.getTableConfig().getTablePath().getFileName().toString());
        row.createCell(5, CellType.STRING).setCellValue(record.getTableConfig().getSheetName());
        row.createCell(6, CellType.NUMERIC).setCellValue(record.getRowNumber() + 1);
    }

    private String generateFilename() {
        return "Report_" + System.currentTimeMillis() + ".xlsx";
    }
}
