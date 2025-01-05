package oleborn.todospringtest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;

/**
 * Аспект для логирования вызовов методов и исключений.
 * <p>
 * Логирует выполнение методов и обработку исключений в классе {@code TaskService}.
 * <p>
 * <h2>Описание аспектов:</h2>
 * <ul>
 *     <li>{@link Before} — Логирует начало работы метода.</li>
 *     <li>{@link AfterThrowing} — Логирует исключения, выброшенные методом.</li>
 * </ul>
 */
@Component
@Aspect
@Slf4j
public class LoggingAspect {

    /**
     * Логирование начала работы метода.
     *
     * @param joinPoint информация о вызванном методе
     */
    @Before("execution(* oleborn.todospringtest.controllers.services.TaskService.*(..))")
    public void logMethodExecution(JoinPoint joinPoint) {
        log.info("Запущена работа метода:{}", joinPoint.getSignature().getName());
        System.out.println("Запущена работа метода:" + joinPoint.getSignature().getName());
    }

    /**
     * Логирование исключений, выброшенных методом.
     *
     * @param exception выброшенное исключение
     * @param joinPoint информация о методе, вызвавшем исключение
     */
    @AfterThrowing(value = "execution(* oleborn.todospringtest.controllers.services.TaskService.*(..))",
            throwing = "exception", argNames = "exception,joinPoint")
    public void logException(Exception exception, JoinPoint joinPoint) {
        System.out.println("Метод " + joinPoint.getSignature().getName() + " породил ошибку: " + exception.getMessage());
    }
}
