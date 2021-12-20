package my.company.gauge.parameters.evaluator.rules;

public class NoRule implements RuleStrategy {
    @Override
    public String evaluate(String rule) {
        return rule;
    }
}
