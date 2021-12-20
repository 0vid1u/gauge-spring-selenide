package my.company.gauge.parameters.evaluator.rules;

import my.company.gauge.exceptions.InvalidRuleFormatException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;


public interface RuleStrategy {

    String evaluate(String rule);

    default String extractRuleName(String rule) {
        return validate(rule).getKey();
    }

    default String extractRuleMetadata(String rule) {
        return validate(rule).getValue();
    }

    private Pair<String, String> validate(String rule) {
        List<String> ruleParts = Pattern.compile("->")
                .splitAsStream(rule)
                .map(String::trim)
                .toList();

        if (ruleParts.size() != 2 || isNullOrEmpty(ruleParts.get(0)) || isNullOrEmpty(ruleParts.get(1))) {
            throw new InvalidRuleFormatException("Invalid rule format. Expected format: RULE_NAME -> rule metadata");
        }

        return Pair.of(ruleParts.get(0), ruleParts.get(1));
    }
}
