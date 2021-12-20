package my.company.gauge.parameters.evaluator.rules;

import my.company.datastore.Brainiac;
import my.company.datastore.Memory;
import my.company.datastore.internal.DataStoreHolder;

import java.util.Objects;

public class DataStoreRule implements RuleStrategy {
    private final Brainiac brainiac = new DataStoreHolder();

    @Override
    public String evaluate(String rule) {
        String key = extractRuleMetadata(rule);
        return Objects.toString(brainiac.recall(Memory.fromString(key)));
    }
}
