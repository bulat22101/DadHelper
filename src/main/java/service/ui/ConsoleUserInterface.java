package service.ui;

import connector.user.ConsoleUserConnector;
import entity.ColumnsConfig;
import entity.SearchTask;
import entity.TableConfig;
import entity.TableType;
import service.search.SearchProcessor;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleUserInterface implements UserInterface {
    private final ConsoleUserConnector consoleUserConnector;
    private final SearchProcessor searchProcessor;

    public ConsoleUserInterface(ConsoleUserConnector consoleUserConnector, SearchProcessor searchProcessor) {
        this.consoleUserConnector = consoleUserConnector;
        this.searchProcessor = searchProcessor;
    }

    @Override
    public void launch() {
        do {
            SearchTask searchTask = getSearchTask();
            String reportFileName = searchProcessor.proceedSearching(searchTask);
            consoleUserConnector.showMessage("Your report in file: " + reportFileName);
        } while (checkContinue());
    }

    private SearchTask getSearchTask() {
        return new SearchTask(getTotalTableConfig(), getWorkTablesConfigs());
    }

    private TableConfig getTotalTableConfig() {
        consoleUserConnector.showMessage("####TOTAL TABLE####");
        return getTableConfig();
    }

    private List<TableConfig> getWorkTablesConfigs() {
        consoleUserConnector.showMessage("####WORK TABLES####");
        int tablesNumber = consoleUserConnector.readInt("Enter number of tables: ");
        return IntStream.range(0, tablesNumber)
                .boxed()
                .map(i -> getTableConfig())
                .collect(Collectors.toList());
    }

    private TableConfig getTableConfig() {
        String tablePath = getTablePath();
        TableType tableType = TableType.getTableType(tablePath);
        String sheetName = tableType == TableType.XLSX ? getSheetName() : null;
        String delimiter = tableType == TableType.CSV ? getDelimiter() : null;
        ColumnsConfig columnsConfig = getColumnsConfig();
        return new TableConfig(tableType, columnsConfig, new File(tablePath).toPath(), sheetName, delimiter, "[\\s\"']");
    }

    private String getTablePath() {
        return consoleUserConnector.readString("Enter path to file: ");
    }

    private String getSheetName() {
        return consoleUserConnector.readString("Enter sheet name: ");
    }

    private ColumnsConfig getColumnsConfig() {
        int nameColumnNumber = consoleUserConnector.readInt("Enter name column number(starting from 0): ");
        int amountColumnNumber = consoleUserConnector.readInt("Enter amount column number(starting from 0): ");
        int measureColumnNumber = consoleUserConnector.readInt("Enter measure column number(starting from 0): ");
        return new ColumnsConfig(nameColumnNumber, amountColumnNumber, measureColumnNumber);
    }

    private boolean checkContinue() {
        return "YES".equals(consoleUserConnector.readString("Type \"YES\" to make one more search.").toUpperCase());
    }

    private String getDelimiter() {
        return consoleUserConnector.readString("Enter CSV delimiter:");
    }
}
