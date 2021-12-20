package my.company.gauge.parameters.converter;

import com.thoughtworks.gauge.execution.parameters.parsers.base.CustomParameterParser;
import gauge.messages.Spec;

import java.util.Arrays;
import java.util.List;

public class StringToListConverter extends CustomParameterParser<List<String>> {

    @Override
    protected List<String> customParse(Class<?> parameterType, Spec.Parameter parameter) {
        return Arrays.stream(parameter.getValue().split(",")).map(String::trim).toList();
    }

    @Override
    public boolean canParse(Class<?> parameterType, Spec.Parameter parameter) {
        return parameterType.equals(List.class);
    }
}
