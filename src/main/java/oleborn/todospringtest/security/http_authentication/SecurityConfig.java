package oleborn.todospringtest.security.http_authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
HTTP Basic Authentication — это простой способ аутентификации,
при котором клиент отправляет логин и пароль в заголовке HTTP-запроса.
Данные кодируются в формате Base64, но не шифруются, поэтому этот метод рекомендуется использовать
только в сочетании с HTTPS для защиты данных.

Аутентификация через HTTP Basic Authentication длится только в течение одного запроса.
Это означает, что клиент (например, браузер или другое приложение) должен отправлять логин и пароль
в заголовке Authorization при каждом запросе к защищенному ресурсу.

Почему так происходит?

В HTTP Basic Authentication нет состояния (stateless). Сервер не хранит информацию о том,
что пользователь уже аутентифицирован.
Каждый запрос должен содержать заголовок Authorization с данными для аутентификации.
Если клиент (например, браузер) не отправляет этот заголовок, сервер возвращает ответ 401 Unauthorized,
 и клиент должен снова запросить учетные данные у пользователя.

Почему HTTP Basic Authentication небезопасен?

 1. Данные передаются в открытом виде
Логин и пароль кодируются в формате Base64, но не шифруются.
Base64 — это не шифрование, а просто способ представления данных в текстовом виде.

Если злоумышленник перехватит запрос (например, через атаку "Man-in-the-Middle"),
он легко сможет декодировать Base64 и получить логин и пароль.

2. Отсутствие защиты без HTTPS
Без использования HTTPS (SSL/TLS) данные передаются по сети в открытом виде.
Это делает HTTP Basic Authentication крайне уязвимым для перехвата.

С HTTPS данные шифруются, что делает перехват сложнее, но Base64 все равно не является надежным способом защиты.

3. Пароль передается при каждом запросе
Поскольку логин и пароль передаются при каждом запросе, это увеличивает риск утечки данных.
Если злоумышленник получит доступ к одному запросу, он сможет использовать учетные данные для доступа к системе.

4. Нет механизма выхода (Logout)
В HTTP Basic Authentication нет стандартного способа "выйти" из системы.
Клиент может просто перестать отправлять заголовок Authorization, но это не гарантирует,
что данные не будут сохранены (например, в кеше браузера).

5. Уязвимость к атакам повторного использования (Replay Attacks)
Если злоумышленник перехватит запрос с заголовком Authorization, он может повторно отправить его на сервер,
и сервер примет его как легитимный.
 */

/**
 * Конфигурационный класс для настройки безопасности приложения.
 * Включает настройку цепочки фильтров безопасности, аутентификации и авторизации.
 * Использует HTTP Basic Authentication для аутентификации пользователей.
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
                        .requestMatchers("/public").permitAll() // Разрешить доступ к "/public" без аутентификации
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                // Включение HTTP Basic Authentication
                .httpBasic(httpBasic -> {}); // Использование HTTP Basic Authentication для аутентификации

        return http.build(); // Сборка и возврат настроенной цепочки фильтров
    }

    /**
     * Создает сервис для работы с пользователями.
     * В данном случае используется in-memory хранилище пользователей.
     *
     * @return UserDetailsService, который управляет пользователями.
     */
    //@Bean
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