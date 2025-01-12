package oleborn.todospringtest.controllers.jwt;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import oleborn.todospringtest.model.jwt_authentication.util.JwtUtil;
import oleborn.todospringtest.security.jwt_authentication.UserPrincipal;
import oleborn.todospringtest.services.UserPrincipalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов, связанных с аутентификацией.
 * Этот контроллер предоставляет эндпоинт для аутентификации пользователей
 * и генерации JWT-токенов.
 */
@RestController
@RequestMapping("/auth")
public class AuthControllerWithCookies {

    @Resource // Внедрение зависимости сервиса для работы с пользовательскими данными
    private UserPrincipalService userDetailsService;

    @Resource // Внедрение зависимости утилиты для работы с JWT-токенами
    private JwtUtil jwtUtil;

    @Resource // Внедрение зависимости AuthenticationManager для аутентификации пользователей
    private AuthenticationManager authenticationManager;

    /**
     * Эндпоинт для аутентификации пользователя и генерации JWT-токена.
     * После успешной аутентификации токен сохраняется в cookies.
     *
     * @param response HTTP-ответ для установки cookies
     * @return ResponseEntity с сообщением об успешной аутентификации
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestParam String username, // Получаем username из параметров запроса
                                                            @RequestParam String password, // Получаем password из параметров запроса
                                                            HttpServletResponse response) {
        try {

            // Аутентификация пользователя
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Загрузка данных пользователя
            UserPrincipal userDetails = userDetailsService.loadUserByUsername(username);

            // Генерация JWT-токена
            String token = jwtUtil.generateToken(userDetails);

            // Устанавливаем токен в cookies
            Cookie cookie = new Cookie("jwtToken", token);
            cookie.setHttpOnly(true); // Защита от XSS
            cookie.setSecure(true); // Только для HTTPS
            cookie.setPath("/"); // Доступно для всех путей
            response.addCookie(cookie);

            response.sendRedirect("/tasks");

            // Возвращаем успешный ответ
            return ResponseEntity.ok("Authentication successful! Token has been set in cookies.");
        } catch (Exception e) {
            // В случае ошибки аутентификации возвращаем ошибку
            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage());
        }
    }
}