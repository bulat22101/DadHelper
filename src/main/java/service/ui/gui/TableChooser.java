package service.ui.gui;

import entity.ColumnsConfig;
import entity.TableConfig;
import entity.TableType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class TableChooser extends JFrame {
    private final String tableRequesterKey;
    private final TableRequester tableRequester;
    private final TableChooserPanel panel;
    private Path filePath;

    private TableChooser(String tableRequesterKey, TableRequester tableRequester, TableChooserPanel panel, Path filePath) {
        this.tableRequesterKey = tableRequesterKey;
        this.tableRequester = tableRequester;
        this.panel = panel;
        this.filePath = filePath;
        panel.getChooseFileJButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TableChooser.this.panel.clearError();
                getFile();
            }
        });
        this.panel.getOkJButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendTableConfigAndExit();
            }
        });
        getContentPane().add(panel);
        setTitle("Таблица");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public TableChooser(String tableRequesterKey, TableRequester tableRequester) {
        this(tableRequesterKey, tableRequester, new TableChooserPanel(), null);
    }

    public TableChooser(String tableRequesterKey, TableRequester tableRequester, TableConfig defaultConfig) {
        this(tableRequesterKey, tableRequester, new TableChooserPanel(defaultConfig), defaultConfig.getTablePath());
    }

    private void sendTableConfigAndExit() {
        panel.clearError();
        if (filePath == null) {
            panel.showErrorMessage("Выбери файл!");
            return;
        }
        TableType tableType;
        try {
            tableType = TableType.getTableType(filePath.getFileName().toString());
        } catch (IllegalArgumentException e) {
            panel.showErrorMessage("Поддерживается только csv и xlsx.");
            return;
        }
        String sheetName = panel.getSheetName();
        String delimiter = panel.getDelimiter();
        if (tableType == TableType.CSV && delimiter.trim().isEmpty()) {
            panel.showErrorMessage("Выберите разделитель!");
            return;
        }
        if (tableType == TableType.XLSX && sheetName.trim().isEmpty()) {
            panel.showErrorMessage("Введите имя листа!");
            return;
        }
        int nameColumnNumber, measureColumnNumber, amountColumnNumber;
        try {
            nameColumnNumber = Integer.parseInt(panel.getNameColumnNumber()) - 1;
            measureColumnNumber = Integer.parseInt(panel.getMeasureColumnNumber()) - 1;
            amountColumnNumber = Integer.parseInt(panel.getAmountColumnNumber()) - 1;
        } catch (Exception e) {
            panel.showErrorMessage("Введите корректные номера столбцов!");
            return;
        }
        ColumnsConfig columnsConfig = new ColumnsConfig(nameColumnNumber, amountColumnNumber, measureColumnNumber);
        TableConfig tableConfig = new TableConfig(tableType, columnsConfig, filePath, sheetName, delimiter, "[\\s\"']");
        tableRequester.tableGotten(tableRequesterKey, tableConfig);
        setVisible(false);
        dispose();
    }

    private void getFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Таблицы", "csv", "xlsx"));
        if (fileChooser.showDialog(panel, "Выбрать") == JFileChooser.APPROVE_OPTION) {
            this.filePath = fileChooser.getSelectedFile().toPath();
            this.panel.getChooseFileJButton().setText(this.filePath.getFileName().toString());
        }
    }
}
