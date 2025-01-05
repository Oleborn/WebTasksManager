package oleborn.todospringtest.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Конфигурационный класс для включения поддержки Spring AOP.
 * <p>
 * Этот класс активирует механизм AOP в приложении, используя аннотацию
 * {@code @EnableAspectJAutoProxy}. Она автоматически создаёт прокси для
 * компонентов, в которых используется аспектно-ориентированное программирование (AOP).
 * <p>
 * <h2>Описание аннотаций:</h2>
 * <ul>
 *     <li>{@link Configuration} — указывает, что данный класс является конфигурационным
 *     и может содержать определения бинов для контекста Spring.</li>
 *     <li>{@link EnableAspectJAutoProxy} — включает автоматическое создание
 *     прокси-объектов для классов, где применяются аспекты.</li>
 * </ul>
 *
 * <h2>Пример использования:</h2>
 * <p>Для применения AOP в вашем проекте, создайте аспект и зарегистрируйте его в контексте Spring:</p>
 *
 * <pre>{@code
 * @Aspect
 * @Component
 * public class LoggingAspect {
 *     @Before("execution(* oleborn.todospringtest.controllers.services.*.*(..))")
 *     public void logBefore(JoinPoint joinPoint) {
 *         System.out.println("Вызов метода: " + joinPoint.getSignature().getName());
 *     }
 * }
 * }</pre>
 *
 * <h2>Результат:</h2>
 * После конфигурации и применения аспектов, Spring будет автоматически оборачивать
 * указанные бины в прокси и перехватывать вызовы методов в соответствии с настройками
 * ваших аспектов.
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
}

