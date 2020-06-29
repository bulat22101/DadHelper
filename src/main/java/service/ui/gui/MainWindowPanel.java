package service.ui.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindowPanel extends CustomFontJPanel {
    private final JLabel totalAreaDescriptionJLabel;
    private final JLabel workAreaDescriptionJLabel;
    private final JLabel totalTableJLabel;
    private final JComboBox<String> workTablesJComboBox;
    private final JButton addTotalJButton;
    private final JButton addWorkJButton;
    private final JButton createReportJButton;
    private final JLabel errorsJLabel;

    public MainWindowPanel() {
        totalAreaDescriptionJLabel = new JLabel("ОДМ");
        workAreaDescriptionJLabel = new JLabel("ОТЧЕТЫ КС");
        totalTableJLabel = new JLabel("ФАЙЛ ОДМ НЕ ВЫБРАН");
        workTablesJComboBox = new JComboBox<>();
        addTotalJButton = new JButton("ВЫБРАТЬ");
        addWorkJButton = new JButton("ДОБАВИТЬ");
        createReportJButton = new JButton("ПОЛУЧИТЬ ОТЧЕТ");
        errorsJLabel = new JLabel();
        setLayout(new GridLayout(3, 3));
        add(totalAreaDescriptionJLabel);
        add(addTotalJButton);
        add(totalTableJLabel);
        add(workAreaDescriptionJLabel);
        add(addWorkJButton);
        add(workTablesJComboBox);
        add(createReportJButton);
        add(errorsJLabel);
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

    public void changeTotalTableName(String totalTableName) {
        totalTableJLabel.setText(totalTableName);
    }

    public void addWorkTableName(String workTableName) {
        workTablesJComboBox.addItem(workTableName);
    }

}
