package my.company.gauge.parameters.evaluator.rules;

import static java.lang.System.getenv;

public class PropertyRule implements RuleStrategy {
    @Override
    public String evaluate(String rule) {
        return getenv(extractRuleMetadata(rule));
    }
}
