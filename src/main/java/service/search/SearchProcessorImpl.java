package service.search;

import connector.writer.ReportWriter;
import entity.*;
import exception.DadHelperException;
import service.TableGrabberResolver;

import java.util.*;
import java.util.stream.Collectors;

public class SearchProcessorImpl implements SearchProcessor {
    private final TableGrabberResolver tableGrabberResolver;
    private final ReportWriter reportWriter;

    public SearchProcessorImpl(TableGrabberResolver tableGrabberResolver, ReportWriter reportWriter) {
        this.tableGrabberResolver = tableGrabberResolver;
        this.reportWriter = reportWriter;
    }

    @Override
    public String proceedSearching(SearchTask searchTask) throws DadHelperException {
        List<MaterialRecord> totalMaterialRecords = getMaterialRecords(searchTask.getTotalTableConfig());
        List<MaterialRecord> workMaterialRecords = new ArrayList<>();
        for (TableConfig tableConfig : searchTask.getWorkTablesConfigs()) {
            List<MaterialRecord> materialRecords = getMaterialRecords(tableConfig);
            workMaterialRecords.addAll(materialRecords);
        }
        Report report = generateReport(totalMaterialRecords, workMaterialRecords);
        return reportWriter.createAndSaveReport(report);
    }

    private Report generateReport(
            List<MaterialRecord> totalMaterialRecords,
            List<MaterialRecord> workMaterialRecords
    ) {
        List<AmountCheckReportRecord> amountCheckReportRecords = generateAmountCheckReportRecords(
                totalMaterialRecords,
                workMaterialRecords
        );
        totalMaterialRecords.sort(Comparator.comparing(MaterialRecord::getFixedName));
        workMaterialRecords.sort(Comparator.comparing(MaterialRecord::getFixedName));
        amountCheckReportRecords.sort(
                Comparator.comparing(AmountCheckReportRecord::getFixedName)
                        .thenComparing(AmountCheckReportRecord::getMeasure)
        );
        List<SimpleAmountCheckReportRecord> simpleAmountCheckReportRecords =
                getSimpleAmountCheckReportRecords(totalMaterialRecords, workMaterialRecords);
        return new Report(amountCheckReportRecords, totalMaterialRecords, workMaterialRecords, simpleAmountCheckReportRecords);
    }

    private List<AmountCheckReportRecord> generateAmountCheckReportRecords(
            List<MaterialRecord> totalMaterialRecords,
            List<MaterialRecord> workMaterialRecords
    ) {
        List<AmountCheckReportRecord> result = new ArrayList<>();
        Map<String, Map<String, Double>> totalMaterialsAmountByNameAndMeasure =
                getMaterialsAmountByNameAndMeasure(totalMaterialRecords);
        Map<String, Map<String, Double>> workMaterialsAmountByNameAndMeasure =
                getMaterialsAmountByNameAndMeasure(workMaterialRecords);
        Set<String> allMaterialsNames = intersection(
                totalMaterialsAmountByNameAndMeasure.keySet(),
                workMaterialsAmountByNameAndMeasure.keySet()
        );
        for (String name : allMaterialsNames) {
            Set<String> allMeasures = intersection(
                    totalMaterialsAmountByNameAndMeasure.getOrDefault(name, Collections.emptyMap()).keySet(),
                    workMaterialsAmountByNameAndMeasure.getOrDefault(name, Collections.emptyMap()).keySet()
            );
            for (String measure : allMeasures) {
                double totalAmount = totalMaterialsAmountByNameAndMeasure
                        .getOrDefault(name, Collections.emptyMap())
                        .getOrDefault(measure, 0d);
                double workAmount = workMaterialsAmountByNameAndMeasure
                        .getOrDefault(name, Collections.emptyMap())
                        .getOrDefault(measure, 0d);
                AmountCheckReportRecord record = new AmountCheckReportRecord(name, measure, totalAmount, workAmount);
                result.add(record);
            }
        }
        return result;
    }

    private <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>();
        result.addAll(set1);
        result.addAll(set2);
        return result;
    }

    private Map<String, Map<String, Double>> getMaterialsAmountByNameAndMeasure(List<MaterialRecord> materialRecords) {
        return materialRecords.stream()
                .collect(Collectors.groupingBy(
                        MaterialRecord::getFixedName,
                        Collectors.groupingBy(
                                MaterialRecord::getMeasure,
                                Collectors.summingDouble(MaterialRecord::getAmount)
                        )
                ));
    }

    private List<MaterialRecord> getMaterialRecords(TableConfig tableConfig) throws DadHelperException {
        return tableGrabberResolver.getTableGrabber(tableConfig.getTableType())
                .getMaterialRecords(tableConfig);
    }

    private List<SimpleAmountCheckReportRecord> getSimpleAmountCheckReportRecords(
            List<MaterialRecord> totalMaterialRecords,
            List<MaterialRecord> workMaterialRecords
    ) {
        Map<String, List<MaterialRecord>> workMaterialRecordsByFixedName = workMaterialRecords.stream()
                .collect(Collectors.groupingBy(MaterialRecord::getFixedName, Collectors.toList()));
        return totalMaterialRecords.stream()
                .sorted(Comparator.comparing(MaterialRecord::getRowNumber))
                .map(totalMaterialRecord -> {
                    int totalReportRowNumber = totalMaterialRecord.getRowNumber();
                    String name = totalMaterialRecord.getName();
                    double totalReportAmount = totalMaterialRecord.getAmount();
                    double workReportAmount = workMaterialRecordsByFixedName
                            .getOrDefault(totalMaterialRecord.getFixedName(), Collections.emptyList())
                            .stream()
                            .mapToDouble(MaterialRecord::getAmount)
                            .sum();
                    return new SimpleAmountCheckReportRecord(
                            totalReportRowNumber,
                            name,
                            totalReportAmount,
                            workReportAmount
                    );
                })
                .collect(Collectors.toList());
    }
}
