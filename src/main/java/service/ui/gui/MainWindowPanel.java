package service.ui.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindowPanel extends CustomFontJPanel {
    private static final String DEFAULT_WORK_TABLES_COMBOBOX_ITEM = "ОТЧЕТЫ КС";
    private final JLabel totalTableJLabel;
    private final JComboBox<String> workTablesJComboBox;
    private final JButton addTotalJButton;
    private final JButton addWorkJButton;
    private final JButton deleteWorkJButton;
    private final JButton editWorkJButton;
    private final JButton createReportJButton;
    private final JLabel errorsJLabel;

    public MainWindowPanel() {
        this.totalTableJLabel = new JLabel("ФАЙЛ ОДМ НЕ ВЫБРАН");
        this.workTablesJComboBox = new JComboBox<>();
        this.workTablesJComboBox.addItem(DEFAULT_WORK_TABLES_COMBOBOX_ITEM);
        this.addTotalJButton = new JButton("ВЫБРАТЬ");
        this.addWorkJButton = new JButton("ДОБАВИТЬ");
        this.deleteWorkJButton = new JButton("УДАЛИТЬ");
        this.editWorkJButton = new JButton("ИЗМЕНИТЬ");
        this.createReportJButton = new JButton("ПОЛУЧИТЬ ОТЧЕТ");
        this.errorsJLabel = new JLabel();
        setLayout(new GridLayout(4, 2));
        add(totalTableJLabel);
        add(addTotalJButton);
        add(workTablesJComboBox);
        add(addWorkJButton);
        add(editWorkJButton);
        add(deleteWorkJButton);
        add(errorsJLabel);
        add(createReportJButton);
    }

    public void showErrorMessage(String message) {
        errorsJLabel.setText("<html><font color='red'>" + message + "</font></html>");
    }

    public void clearError() {
        errorsJLabel.setText("");
    }

    public JButton getAddTotalJButton() {
        return addTotalJButton;
    }

    public JButton getAddWorkJButton() {
        return addWorkJButton;
    }

    public JButton getCreateReportJButton() {
        return createReportJButton;
    }

    public JButton getDeleteWorkJButton() {
        return deleteWorkJButton;
    }

    public JButton getEditWorkJButton() {
        return editWorkJButton;
    }

    public JComboBox<String> getWorkTablesJComboBox() {
        return workTablesJComboBox;
    }

    public void changeTotalTableName(String totalTableName) {
        totalTableJLabel.setText(totalTableName);
    }

    public void addWorkTableName(String workTableName) {
        workTablesJComboBox.addItem(workTableName);
    }

}
