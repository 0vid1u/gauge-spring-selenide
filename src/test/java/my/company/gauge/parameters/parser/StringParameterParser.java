package my.company.gauge.parameters.parser;

import com.thoughtworks.gauge.execution.parameters.parsers.base.CustomParameterParser;
import gauge.messages.Spec;
import my.company.gauge.parameters.evaluator.ParameterEvaluator;

public class StringParameterParser extends CustomParameterParser<String> {

    private final ParameterEvaluator evaluator = new ParameterEvaluator();

    @Override
    protected String customParse(Class<?> parameterType, Spec.Parameter parameter) {
        return evaluator.evaluate(parameter.getValue());
    }

    @Override
    public boolean canParse(Class<?> parameterType, Spec.Parameter parameter) {
        return parameterType.equals(String.class);
    }
}
