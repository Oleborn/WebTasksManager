package oleborn.todospringtest.security.form_authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
Аутентификация через форму — это один из самых распространенных способов аутентификации в веб-приложениях.
Пользователь вводит свои учетные данные (логин и пароль) в HTML-форму, которая отправляется на сервер для проверки.
Если данные верны, пользователь получает доступ к защищенным ресурсам.

Аутентификация через форму в Spring Security (и в большинстве веб-приложений) действует до тех пор,
пока сессия пользователя активна или пока пользователь явно не выйдет из системы.
 */

/**
 * Конфигурационный класс для настройки безопасности приложения.
 * Включает настройку цепочки фильтров безопасности, аутентификации и авторизации.
 */
//@Configuration // Указывает, что класс является конфигурационным и содержит бины Spring
//@EnableWebSecurity // Включает поддержку безопасности для веб-приложения
public class SecurityConfig {

    /**
     * Настраивает цепочку фильтров безопасности.
     *
     * @param http Объект для настройки безопасности HTTP-запросов.
     * @return SecurityFilterChain, который определяет правила безопасности.
     * @throws Exception Если произошла ошибка при настройке.
     */
    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Настройка авторизации запросов
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/").permitAll() // Разрешить доступ к "/tasks" без аутентификации
                        .anyRequest().authenticated() // Все запросы требуют аутентификации
                )
                // Настройка формы входа
                .formLogin(form -> form
                        .loginPage("/login") // Указать страницу входа
                        .permitAll() // Разрешить доступ к странице входа всем пользователям
                )
                // Настройка выхода из системы
                .logout(LogoutConfigurer::permitAll); // Разрешить выход из системы всем пользователям

        return http.build(); // Сборка и возврат настроенной цепочки фильтров
    }

    /**
     * Создает сервис для работы с пользователями.
     * В данном случае используется in-memory хранилище пользователей.
     *
     * @return UserDetailsService, который управляет пользователями.
     */
    //@Bean
    //Создание пользователя можно вынести в отдельный класс и отдельную логику с созданием сущности и тд
    public UserDetailsService userDetailsService() {
        // Создание пользователя с именем "user", паролем "password" и ролью "USER"
        UserDetails user = User.builder()
                .username("user") // Имя пользователя
                .password(passwordEncoder().encode("password")) // Кодирование пароля
                .roles("USER") // Роль пользователя
                .build();

        // Возврат менеджера пользователей, который хранит пользователя в памяти
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Создает кодировщик паролей.
     * Используется для кодирования паролей пользователей.
     *
     * @return PasswordEncoder, который использует алгоритм BCrypt.
     */
    //@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Использование BCrypt для кодирования паролей
    }
}