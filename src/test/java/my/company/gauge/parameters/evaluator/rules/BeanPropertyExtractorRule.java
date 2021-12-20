package my.company.gauge.parameters.evaluator.rules;

import my.company.datastore.Brainiac;
import my.company.datastore.Memory;
import my.company.datastore.internal.DataStoreHolder;
import my.company.gauge.exceptions.NullBeanPropertyException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

public class BeanPropertyExtractorRule implements RuleStrategy {
    private static final Map<String, Memory> RULE_BEAN_ALIASES = Map.of("$user_profile", Memory.BUFFER); //to be replaced with appropriated keys
    private final Brainiac brainiac = new DataStoreHolder();

    @Override
    public String evaluate(String rule) {
        var bean = brainiac.recall(RULE_BEAN_ALIASES.get(extractRuleName(rule)));
        String propertyName = extractRuleMetadata(rule);

        return getPropertyFrom(bean, propertyName);
    }

    private String getPropertyFrom(Object bean, String propertyName) {
        try {
            return Optional.ofNullable(PropertyUtils.getProperty(bean, propertyName))
                    .orElseThrow(() -> {
                        throw new NullBeanPropertyException("The [" + bean + "] object property [" + propertyName + "] value can't be null.");
                    })
                    .toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(format("Such property [%s] does not exist in model %s", propertyName, bean), e);
        }
    }
}
