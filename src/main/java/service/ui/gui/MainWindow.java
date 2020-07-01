package service.ui.gui;

import entity.SearchTask;
import entity.TableConfig;
import exception.DadHelperException;
import service.search.SearchProcessor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame implements TableRequester {
    private static final String TOTAL_TABLE = "TOTAL_TABLE";
    private static final String WORK_TABLE = "WORK_TABLE";
    private final SearchProcessor searchProcessor;
    private final MainWindowPanel panel;
    private final List<TableConfig> workTables;
    private TableConfig totalTableConfig;

    public MainWindow(SearchProcessor searchProcessor) {
        this.searchProcessor = searchProcessor;
        this.panel = new MainWindowPanel();
        this.workTables = new ArrayList<>();
        getContentPane().add(panel);
        panel.getCreateReportJButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createReport();
            }
        });
        panel.getAddTotalJButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.clearError();
                if (totalTableConfig == null) {
                    new TableChooser(TOTAL_TABLE, MainWindow.this);
                } else {
                    new TableChooser(TOTAL_TABLE, MainWindow.this, totalTableConfig);
                }
            }
        });
        panel.getAddWorkJButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.clearError();
                new TableChooser(WORK_TABLE, MainWindow.this);
            }
        });
        panel.getDeleteWorkJButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.clearError();
                removeSelectedWorkTable();
            }
        });
        panel.getEditWorkJButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.clearError();
                TableConfig selectedTableConfig = removeSelectedWorkTable();
                if (selectedTableConfig != null) {
                    new TableChooser(WORK_TABLE, MainWindow.this, selectedTableConfig);
                }
            }
        });
        setTitle("Поиск");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void tableGotten(String key, TableConfig tableConfig) {
        if (TOTAL_TABLE.equals(key)) {
            totalTableConfig = tableConfig;
            panel.changeTotalTableName(tableConfig.getTablePath().getFileName().toString());
            panel.getAddTotalJButton().setText("ИЗМЕНИТЬ");
        }
        if (WORK_TABLE.equals(key)) {
            workTables.add(tableConfig);
            panel.addWorkTableName(tableConfig.getTablePath().getFileName().toString());
        }
    }

    private TableConfig removeSelectedWorkTable() {
        int selectedIndex = panel.getWorkTablesJComboBox().getSelectedIndex();
        if (selectedIndex > 0) {
            panel.getWorkTablesJComboBox().removeItemAt(selectedIndex);
            panel.getWorkTablesJComboBox().setSelectedIndex(0);
            return workTables.remove(selectedIndex - 1);
        } else {
            return null;
        }
    }

    private void createReport() {
        panel.clearError();
        if (totalTableConfig == null) {
            panel.showErrorMessage("Файл ОДМ не выбран");
            return;
        }
        if (workTables.isEmpty()) {
            panel.showErrorMessage("Отчеты КС не выбраны");
            return;
        }
        SearchTask searchTask = new SearchTask(totalTableConfig, workTables);
        String reportFileName;
        try {
            reportFileName = searchProcessor.proceedSearching(searchTask);
            JOptionPane.showMessageDialog(this, reportFileName, "Поиск завершен", JOptionPane.INFORMATION_MESSAGE);
        } catch (DadHelperException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Не удалось завершить поиск", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getStackTrace());
        }
    }
}
