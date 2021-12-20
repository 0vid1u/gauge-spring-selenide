package my.company.gauge.parameters.evaluator;

import my.company.gauge.parameters.evaluator.rules.*;

import static io.vavr.API.*;
import static io.vavr.Predicates.anyOf;
import static my.company.utils.StringUtils.startsWith;

public class ParameterEvaluator {

    private static final String PROPERTY = "$property";         //$property -> application_username
    private static final String DATASTORE = "$datastore";       //$datastore -> USER_PROFILE
    private static final String RANDOM = "$rnd";                //$rnd{alphanumeric(10)} or $rnd{alphabetic(10)} or $rnd{numeric(5,100)}
    private static final String DATE = "$date";                 //$date -> TOMORROW {yyyy-MM-dd}
    private static final String USER_PROFILE = "$user_profile"; //$user_profile -> password

    public String evaluate(String parameter) {
        return Match(parameter).of(
                Case($(startsWith(PROPERTY)), () -> new RuleContext(new PropertyRule())),
                Case($(startsWith(RANDOM)), rule -> new RuleContext(new RandomRule())),
                Case($(startsWith(DATASTORE)), rule -> new RuleContext(new DataStoreRule())),
                Case($(startsWith(DATE)), rule -> new RuleContext(new DateRule())),
                Case($(anyOf(startsWith(USER_PROFILE))), () -> new RuleContext(new BeanPropertyExtractorRule())),
                Case($(), () -> new RuleContext(new NoRule()))
        ).executeStrategy(parameter);
    }
}
