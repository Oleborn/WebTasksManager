package oleborn.todospringtest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для измерения времени выполнения метода.
 * <p>
 * Методы, помеченные этой аннотацией, будут перехвачены аспектом,
 * который измеряет и логирует время их выполнения.
 * <p>
 * Используйте эту аннотацию, чтобы отслеживать производительность
 * ключевых операций в приложении.
 *
 * <h2>Пример использования:</h2>
 * <pre>{@code
 * @MeasureExecutionTime
 * public void someMethod() {
 *     // Ваш код
 * }
 * }</pre>
 *
 * <h2>Работа с AOP:</h2>
 * Для корректной работы, необходимо реализовать аспект, который
 * будет обрабатывать эту аннотацию.
 *
 * <h3>Пример аспекта:</h3>
 * <pre>{@code
 * @Aspect
 * @Component
 * public class ExecutionTimeAspect {
 *
 *     @Around("@annotation(oleborn.todospringtest.annotation.MeasureExecutionTime)")
 *     public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
 *         long start = System.currentTimeMillis();
 *         Object result = joinPoint.proceed();
 *         long end = System.currentTimeMillis();
 *         System.out.println("Время выполнения метода " + joinPoint.getSignature().getName() + ": " + (end - start) + " мс");
 *         return result;
 *     }
 * }
 * }</pre>
 *
 * @Target(ElementType.METHOD) Указывает, что аннотация применяется только к методам.
 * @Retention(RetentionPolicy.RUNTIME) Указывает, что аннотация доступна во время выполнения через рефлексию.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MeasureExecutionTime {
}

