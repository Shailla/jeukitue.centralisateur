package jkt.centralisateur.storage.transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
public class JktTransactionManager extends StaticMethodMatcherPointcutAdvisor implements MethodInterceptor {

    public JktTransactionManager() {
        setAdvice(this);
    }
    
    public Object invoke(final MethodInvocation method) throws Throwable {        
        return method.proceed();
    }

    public boolean matches(final Method method, final Class<?> targetClass) {
        final Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        Collection<Annotation> annotations = Arrays.asList(specificMethod.getDeclaredAnnotations());
        
        boolean result = false;

        if(annotations != null) {
            for (final Annotation annotation : annotations) {
                if (Transactional.class.isAssignableFrom(annotation.getClass())) {
                    result = true;
                    break;
                }
            }
        }
        
        return result;
    }
}
