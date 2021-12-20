package my.company.gauge.parameters.evaluator.rules;

public record RuleContext(RuleStrategy strategy) {

    public String executeStrategy(String rule) {
        return strategy.evaluate(rule);
    }
}
