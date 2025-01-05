package oleborn.todospringtest.aop;

import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * Аспект для перехвата и обработки вызовов методов сервиса {@code TaskService}.
 * <p>
 * Реализует различные типы советов (advice) для демонстрации возможностей Spring AOP:
 * <ul>
 *     <li>{@link Before} — Выполняется перед вызовом метода.</li>
 *     <li>{@link After} — Выполняется после вызова метода (в любом случае).</li>
 *     <li>{@link AfterReturning} — Выполняется после успешного завершения метода.</li>
 *     <li>{@link AfterThrowing} — Выполняется при выбросе исключения методом.</li>
 *     <li>{@link Around} — Оборачивает метод, позволяя контролировать его выполнение.</li>
 * </ul>
 */
@Component
@Aspect
public class TaskServiceAspect {

    /**
     * Выполняется перед вызовом любого метода в классе {@code TaskService}.
     *
     * @param joinPoint объект {@link JoinPoint}, содержащий информацию о вызванном методе
     */
    @Before("execution(* oleborn.todospringtest.controllers.services.TaskService.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("Перед выполнением метода: " + joinPoint.getSignature().getName());
    }

    /**
     * Выполняется после вызова любого метода в классе {@code TaskService}.
     *
     * @param joinPoint объект {@link JoinPoint}, содержащий информацию о вызванном методе
     */
    @After("execution(* oleborn.todospringtest.controllers.services.TaskService.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        System.out.println("После выполнения метода: " + joinPoint.getSignature().getName());
    }

    /**
     * Выполняется после успешного завершения любого метода в классе {@code TaskService}.
     *
     * @param joinPoint объект {@link JoinPoint}, содержащий информацию о вызванном методе
     */
    @AfterReturning("execution(* oleborn.todospringtest.controllers.services.TaskService.*(..))")
    public void afterReturningMethod(JoinPoint joinPoint) {
        System.out.println("Метод успешно выполнен: " + joinPoint.getSignature().getName());
    }

    /**
     * Выполняется, если метод в классе {@code TaskService} выбросил исключение.
     *
     * @param joinPoint объект {@link JoinPoint}, содержащий информацию о вызванном методе
     */
    @AfterThrowing("execution(* oleborn.todospringtest.controllers.services.TaskService.*(..))")
    public void afterThrowingMethod(JoinPoint joinPoint) {
        System.out.println("Метод выбросил исключение: " + joinPoint.getSignature().getName());
    }

    /**
     * Оборачивает выполнение метода в классе {@code TaskService}, позволяя контролировать его выполнение.
     * <p>
     * Логирует информацию перед и после выполнения метода.
     *
     * @param joinPoint объект {@link ProceedingJoinPoint}, содержащий информацию о вызванном методе
     * @return результат выполнения метода
     * @throws Throwable если исходный метод выбросит исключение
     */
    @Around("execution(* oleborn.todospringtest.controllers.services.TaskService.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // Выводим информацию перед выполнением метода
        System.out.println("Вокруг метода - Перед выполнением: " + joinPoint.getSignature().getName());

        // Выполняем метод
        Object result = joinPoint.proceed();

        // Выводим информацию после выполнения метода
        System.out.println("Вокруг метода - После выполнения: " + joinPoint.getSignature().getName());

        return result;
    }
}