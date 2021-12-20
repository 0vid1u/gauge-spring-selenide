package my.company.utils;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TablePrinter {

    private final List<List<String>> table;
    private int[] columnMaxSize = null;

    public TablePrinter() {
        table = new ArrayList<>();
    }

    public TablePrinter(List<String> headers) {
        this.table = new ArrayList<>();
        this.table.add(headers);
    }

    public TablePrinter(List<String> headers, List<List<String>> rows) {
        this.table = new ArrayList<>();
        this.table.add(headers);
        this.table.addAll(rows);
    }

    public TablePrinter(Table gaugeTable) {
        this.table = new ArrayList<>();
        this.table.add(gaugeTable.getColumnNames());
        this.table.addAll(
                gaugeTable.getTableRows().stream()
                        .map(TableRow::getCellValues)
                        .toList()
        );
    }

    public void setHeader(List<String> header) {
        this.table.add(0, header);
    }

    public void setRows(List<List<String>> rows) {
        this.table.addAll(rows);
    }

    public void addRow(List<String> fields) {
        this.table.add(fields);
    }

    private String getChars(int spaceCount, String s) {
        return String.valueOf(s).repeat(Math.max(0, spaceCount));
    }

    private void initColumnMaxSizes() {
        columnMaxSize = new int[(table.get(0)).size()];
        Arrays.fill(columnMaxSize, 0);
        for (List<String> row : table) {
            for (int j = 0; j < row.size(); j++) {
                int fieldSize = row.get(j).length();
                if (fieldSize > columnMaxSize[j]) {
                    columnMaxSize[j] = fieldSize;
                }
            }
        }
    }

    public String toString() {
        initColumnMaxSizes();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < table.size(); i++) {
            List<String> row = table.get(i);
            if (i == 1) {
                for (int j = 0; j < row.size(); j++) {
                    stringBuilder.append(getChars(columnMaxSize[j] + 1, "-")).append(" ");
                }
                stringBuilder.append(System.lineSeparator());
            }
            for (int j = 0; j < row.size(); j++) {
                String addTmp = row.get(j);
                stringBuilder.append(addTmp).append(getChars(columnMaxSize[j] + 2 - addTmp.length(), " "));
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}