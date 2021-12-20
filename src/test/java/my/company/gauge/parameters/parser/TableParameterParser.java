package my.company.gauge.parameters.parser;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.execution.parameters.parsers.base.CustomParameterParser;
import gauge.messages.Spec;
import my.company.gauge.parameters.evaluator.ParameterEvaluator;

import java.util.ArrayList;
import java.util.List;

public class TableParameterParser extends CustomParameterParser<Table> {

    private final ParameterEvaluator parameterEvaluator = new ParameterEvaluator();

    @Override
    protected Table customParse(Class<?> parameterType, Spec.Parameter parameter) {
        Table actualTable = tableFromProto(parameter.getTable());
        List<String> headers = actualTable.getColumnNames();
        List<TableRow> tableRows = actualTable.getTableRows();

        Table resultTable = new Table(headers);

        tableRows.forEach(row -> {
            List<String> evaluatedRow = row.getCellValues().stream()
                    .map(parameterEvaluator::evaluate)
                    .toList();

            resultTable.addRow(evaluatedRow);
        });

        return resultTable;
    }

    @Override
    public boolean canParse(Class<?> parameterType, Spec.Parameter parameter) {
        return parameterType.equals(Table.class);
    }

    private static Table tableFromProto(Spec.ProtoTable protoTable) {
        Spec.ProtoTableRow headerRow = protoTable.getHeaders();
        List<String> headers = getTableRowFor(headerRow);
        Table table = new Table(headers);

        for (int i = 0; i < protoTable.getRowsCount(); i++) {
            Spec.ProtoTableRow protoRow = protoTable.getRows(i);
            table.addRow(getTableRowFor(protoRow));
        }
        return table;
    }
    private static List<String> getTableRowFor(Spec.ProtoTableRow tableRow) {
        List<String> row = new ArrayList<>();
        for (int i = 0; i < tableRow.getCellsCount(); i++) {
            row.add(tableRow.getCells(i));
        }
        return row;
    }
}
