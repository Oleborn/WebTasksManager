//package oleborn.todospringtest.security.jwt_authentication;
//
//import jakarta.annotation.Resource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * Конфигурационный класс для настройки безопасности приложения.
// * Включает настройку цепочки фильтров безопасности, аутентификации и авторизации.
// * Использует JWT для аутентификации и авторизации запросов.
// */
//@Configuration // Указывает, что этот класс является конфигурационным и содержит бины Spring
//@EnableWebSecurity // Включает поддержку безопасности для веб-приложения
//public class SecurityConfigWithAuthorization {
//
//    @Resource
//    private JwtRequestFilterAuthorization jwtRequestFilter; // Фильтр для обработки JWT-токенов
//
//    /**
//     * Настраивает цепочку фильтров безопасности.
//     *
//     * @param http Объект для настройки безопасности HTTP-запросов.
//     * @return SecurityFilterChain, который определяет правила безопасности.
//     * @throws Exception Если произошла ошибка при настройке.
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Отключаем CSRF-защиту, так как используется JWT
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // Настройка авторизации запросов
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/authenticate", "/register").permitAll() // Разрешаем доступ без аутентификации
//                        .requestMatchers("/admin").hasRole("ADMIN") // Доступ только для пользователей с ролью ADMIN
//                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
//                )
//
//                // Настройка управления сессиями
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Используем stateless-сессии (без состояния)
//
//                // Добавляем JwtRequestFilter перед UsernamePasswordAuthenticationFilter
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build(); // Сборка и возврат настроенной цепочки фильтров
//    }
//
//    /**
//     * Создает кодировщик паролей.
//     * Используется для кодирования паролей пользователей.
//     *
//     * @return PasswordEncoder, который использует алгоритм BCrypt.
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Использование BCrypt для кодирования паролей
//    }
//
//    /**
//     * Создает менеджер аутентификации.
//     * Используется для аутентификации пользователей.
//     *
//     * @param config Конфигурация аутентификации
//     * @return AuthenticationManager для управления аутентификацией
//     * @throws Exception Если произошла ошибка при создании менеджера аутентификации
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager(); // Получение менеджера аутентификации из конфигурации
//    }
//}