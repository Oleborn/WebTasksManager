package oleborn.todospringtest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

/**
 * Аспект для измерения времени выполнения методов.
 * <p>
 * Этот аспект перехватывает методы, помеченные аннотацией {@code @MeasureExecutionTime},
 * и измеряет время их выполнения, выводя результат в консоль.
 * <p>
 * <h2>Основной функционал:</h2>
 * <ul>
 *     <li>Перехватывает методы с аннотацией {@link oleborn.todospringtest.annotation.MeasureExecutionTime}.</li>
 *     <li>Измеряет время выполнения метода.</li>
 *     <li>Логирует результат выполнения с указанием имени метода и времени в миллисекундах.</li>
 * </ul>
 *
 * <h2>Пример аннотированного метода:</h2>
 * <pre>{@code
 * @MeasureExecutionTime
 * public void someMethod() {
 *     // Ваш код
 * }
 * }</pre>
 */
@Component
@Aspect
@Slf4j
public class MeasureExecutionTimeAspect {

    /**
     * Измеряет время выполнения метода, помеченного аннотацией {@code @MeasureExecutionTime}.
     *
     * @param joinPoint объект {@link org.aspectj.lang.ProceedingJoinPoint}, содержащий информацию о методе
     * @return результат выполнения метода
     * @throws Throwable если исходный метод выбросит исключение
     */
    @Around("@annotation(oleborn.todospringtest.annotation.MeasureExecutionTime)")
    public Object measureTime(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();  // Выполняем метод
        long endTime = System.currentTimeMillis();
        log.info("Метод {} был выполнен за: {} мс", joinPoint.getSignature().getName(), endTime - startTime);
        return result;
    }
}
