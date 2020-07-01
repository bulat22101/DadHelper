package service.ui.gui;

import entity.ColumnsConfig;
import entity.TableConfig;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Optional;

public class TableChooserPanel extends CustomFontJPanel {
    private final JLabel chooseFileJLabel;
    private final JButton chooseFileJButton;
    private final JLabel enterSheetJLabel;
    private final JTextField enterSheetJTextField;
    private final JLabel enterDelimiterJLabel;
    private final JTextField enterDelimiterJTextField;
    private final JLabel enterNameColumnNumberJLabel;
    private final JTextField enterNameColumnNumberJTextField;
    private final JLabel enterMeasureColumnNumberJLabel;
    private final JTextField enterMeasureColumnNumberJTextField;
    private final JLabel enterAmountColumnNumberJLabel;
    private final JTextField enterAmountColumnNumberJTextField;
    private final JLabel errorsJLabel;
    private final JButton okJButton;

    public TableChooserPanel() {
        this.chooseFileJLabel = new JLabel("Выберите файл:");
        this.chooseFileJButton = new JButton("Выбрать");
        this.enterSheetJLabel = new JLabel("Введите имя листа(только для .XLSX):");
        this.enterSheetJTextField = new JTextField();
        this.enterDelimiterJLabel = new JLabel("Выберите разделитель(только для .CSV):");
        this.enterDelimiterJTextField = new JTextField(";");
        this.enterNameColumnNumberJLabel = new JLabel("Введите номер столбца с наименованием:");
        this.enterNameColumnNumberJTextField = new JTextField();
        this.enterMeasureColumnNumberJLabel = new JLabel("Введите номер столбца с ед. изм.:");
        this.enterMeasureColumnNumberJTextField = new JTextField();
        this.enterAmountColumnNumberJLabel = new JLabel("Введите номер столбца с количеством:");
        this.enterAmountColumnNumberJTextField = new JTextField();
        this.errorsJLabel = new JLabel();
        this.okJButton = new JButton("OK");
        setLayout(new GridLayout(7, 2));
        add(chooseFileJLabel);
        add(chooseFileJButton);
        add(enterSheetJLabel);
        add(enterSheetJTextField);
        add(enterDelimiterJLabel);
        add(enterDelimiterJTextField);
        add(enterNameColumnNumberJLabel);
        add(enterNameColumnNumberJTextField);
        add(enterMeasureColumnNumberJLabel);
        add(enterMeasureColumnNumberJTextField);
        add(enterAmountColumnNumberJLabel);
        add(enterAmountColumnNumberJTextField);
        add(errorsJLabel);
        add(okJButton);
    }

    public TableChooserPanel(TableConfig defaultValues) {
        this();
        Optional<TableConfig> tableConfig = Optional.of(defaultValues);
        tableConfig
                .map(TableConfig::getTablePath)
                .map(Path::getFileName)
                .map(Path::toString)
                .ifPresent(chooseFileJButton::setText);
        tableConfig
                .map(TableConfig::getSheetName)
                .ifPresent(enterSheetJTextField::setText);
        tableConfig
                .map(TableConfig::getDelimiter)
                .ifPresent(enterDelimiterJTextField::setText);
        Optional<ColumnsConfig> columnsConfig = tableConfig.map(TableConfig::getColumnsConfig);
        columnsConfig
                .map(ColumnsConfig::getNameColumnNumber)
                .map(v -> v + 1)
                .map(String::valueOf)
                .ifPresent(enterNameColumnNumberJTextField::setText);
        columnsConfig
                .map(ColumnsConfig::getMeasureColumnNumber)
                .map(v -> v + 1)
                .map(String::valueOf)
                .ifPresent(enterMeasureColumnNumberJTextField::setText);
        columnsConfig
                .map(ColumnsConfig::getAmountColumnNumber)
                .map(v -> v + 1)
                .map(String::valueOf)
                .ifPresent(enterAmountColumnNumberJTextField::setText);
    }

    public void showErrorMessage(String message) {
        errorsJLabel.setText("<html><font color='red'>" + message + "</font></html>");
    }

    public void clearError() {
        errorsJLabel.setText("");
    }

    public JButton getChooseFileJButton() {
        return chooseFileJButton;
    }

    public JButton getOkJButton() {
        return okJButton;
    }

    public String getSheetName() {
        return enterSheetJTextField.getText();
    }

    public String getDelimiter() {
        return enterDelimiterJTextField.getText();
    }

    public String getNameColumnNumber() {
        return enterNameColumnNumberJTextField.getText();
    }

    public String getAmountColumnNumber() {
        return enterAmountColumnNumberJTextField.getText();
    }

    public String getMeasureColumnNumber() {
        return enterMeasureColumnNumberJTextField.getText();
    }
}
