package connector.writer;

import entity.Report;

public interface ReportWriter {
    String createAndSaveReport(Report report);
}
