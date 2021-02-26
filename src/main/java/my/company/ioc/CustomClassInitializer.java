package my.company.ioc;

import com.thoughtworks.gauge.ClassInitializer;
import org.springframework.context.ApplicationContext;

public class CustomClassInitializer implements ClassInitializer {

    private final ApplicationContext context;

    public CustomClassInitializer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object initialize(Class<?> classToInitialize) throws Exception {
        Object res;

        try {
            res = context.getBean(classToInitialize);
        } catch (Exception e) {
            return Class.forName(classToInitialize.getName()).getDeclaredConstructor().newInstance();
        }

        return res;
    }
}