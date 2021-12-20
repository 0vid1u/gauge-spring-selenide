package my.company.ioc.aspects.managers;

import com.thoughtworks.gauge.Table;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TableEnvironmentFilterManager {

    private static final String ENV_COLUMN_NAME = "ENV";
    private static final int SECOND_COLUMN = 1;
    private static final String ENVIRONMENT_VARIABLE = "gauge_environment";
    private static final int ENVIRONMENT_COLUMN = 0;

    public Table filterByEnvironment(Table table) {
        List<String> initialColumnNames = table.getColumnNames();

        if (hasEnvironmentColumn(initialColumnNames)) {
            return createTableFilteredByEnvironment(table);
        }

        return table;
    }

    private Table createTableFilteredByEnvironment(Table table) {
        List<String> initialColumnNames = table.getColumnNames();
        String env = System.getenv(ENVIRONMENT_VARIABLE);

        Table finalTable = new Table(initialColumnNames.subList(SECOND_COLUMN, initialColumnNames.size()));

        table.getTableRows()
                .stream()
                .filter(r -> r.getCellValues().get(ENVIRONMENT_COLUMN).equalsIgnoreCase(env))
                .forEachOrdered(r -> finalTable.addRow(r.getCellValues().subList(SECOND_COLUMN, r.getCellValues().size())));

        return finalTable;
    }

    private boolean hasEnvironmentColumn(List<String> initialColumnNames) {
        return initialColumnNames.get(0).equalsIgnoreCase(ENV_COLUMN_NAME);
    }
}