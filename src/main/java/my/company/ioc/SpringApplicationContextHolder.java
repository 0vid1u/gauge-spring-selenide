package my.company.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplicationContextHolder {

    private final ApplicationContext context;

    public SpringApplicationContextHolder() {
        this.context = new AnnotationConfigApplicationContext(SpringApplicationConfiguration.class);
    }

    public ApplicationContext getContext() {
        return context;
    }


    public static SpringApplicationContextHolder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SpringApplicationContextHolder INSTANCE = new SpringApplicationContextHolder();
    }
}