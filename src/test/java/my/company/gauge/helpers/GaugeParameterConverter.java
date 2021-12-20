package my.company.gauge.helpers;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import my.company.gauge.exceptions.GaugeTableParsingException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class GaugeParameterConverter {

    private GaugeParameterConverter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static List<String> asList(Table table) {
        if (table.getTableRows().isEmpty() && table.getColumnNames().size() > 1) {
            throw new GaugeTableParsingException("Gauge table must have only one column and can't be empty.");
        }
        return table.getColumnValues(0);
    }

    public static LinkedHashMap<String, String> asLinkedMap(Table table) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        List<TableRow> rows = table.getTableRows();

        for (TableRow row : rows) {
            map.put(row.getCell("Key"), row.getCell("Value"));
        }
        return map;
    }

    public static Map<String, String> asMap(Table table) {
        Map<String, String> map = new HashMap<>();
        List<TableRow> rows = table.getTableRows();

        for (TableRow row : rows) {
            map.put(row.getCell("Key"), row.getCell("Value"));
        }
        return map;
    }

    public static List<Map<String, String>> asMaps(Table table) {
        List<TableRow> rows = table.getTableRows();
        List<String> columnNames = table.getColumnNames();
        List<Map<String, String>> result = new ArrayList<>();

        for (TableRow row : rows) {
            Map<String, String> item = IntStream.range(0, row.size())
                    .boxed()
                    .collect(Collectors.toMap(
                            table::getColumnName,
                            i -> row.getCell(columnNames.get(i)),
                            (a, b) -> b)
                    );
            result.add(item);
        }
        return result;
    }
}
