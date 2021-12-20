package my.company.ioc.aspects;

import com.thoughtworks.gauge.Table;
import lombok.AllArgsConstructor;
import my.company.ioc.aspects.managers.TableEnvironmentFilterManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.UndeclaredThrowableException;

@Aspect
@Component
@AllArgsConstructor
public class StepsAspect {

    private final TableEnvironmentFilterManager tableEnvironmentFilterManager;

    @Around("@annotation(com.thoughtworks.gauge.Step)")
    public Object interceptStep(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        args = processArguments(args);

        try {
            return joinPoint.proceed(args);
        } catch (UndeclaredThrowableException e) {
            if (e.getCause() != null) {
                RuntimeException r = new RuntimeException(e.getCause());
                r.setStackTrace(e.getCause().getStackTrace());
                throw r;
            }
            throw e;
        }

    }

    private Object[] processArguments(Object[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                Object a = args[i];
                if (a instanceof Table table) {
                    args[i] = tableEnvironmentFilterManager.filterByEnvironment(table);
                }
            }
        }

        return args;
    }
}