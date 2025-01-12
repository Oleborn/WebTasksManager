package oleborn.todospringtest.security.jwt_authentication;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oleborn.todospringtest.model.jwt_authentication.util.JwtUtil;
import oleborn.todospringtest.services.UserPrincipalService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр для обработки JWT-токенов в каждом HTTP-запросе.
 * Этот фильтр извлекает JWT-токен из cookies, проверяет его валидность
 * и, если токен корректен, устанавливает аутентификацию пользователя в SecurityContextHolder.
 *
 * Фильтр выполняется один раз для каждого запроса (благодаря наследованию от OncePerRequestFilter).
 */
@Component // Указывает, что этот класс является компонентом Spring и будет управляться Spring-контейнером
public class JwtRequestFilterWithCookies extends OncePerRequestFilter {

    @Resource // Внедрение зависимости сервиса для работы с пользовательскими данными
    private UserPrincipalService userDetailsService;

    @Resource // Внедрение зависимости утилиты для работы с JWT-токенами
    private JwtUtil jwtUtil;

    /**
     * Основной метод фильтра, который выполняется для каждого HTTP-запроса.
     *
     * @param request  HTTP-запрос
     * @param response HTTP-ответ
     * @param filterChain Цепочка фильтров, которая позволяет передать запрос дальше
     * @throws ServletException Если возникает ошибка при обработке запроса
     * @throws IOException Если возникает ошибка ввода-вывода
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Получаем JWT-токен из cookies
        String jwt = getTokenFromCookies(request);

        String username = null; // Имя пользователя, извлеченное из JWT-токена

        // Если токен извлечен, извлекаем имя пользователя из токена
        if (jwt != null) {
            username = jwtUtil.extractUsername(jwt);
        }

        // Если имя пользователя извлечено и аутентификация еще не установлена в SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Загружаем данные пользователя (userPrincipal) по имени пользователя
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Проверяем, что JWT-токен валиден для данного пользователя
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Создаем объект аутентификации на основе данных пользователя
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Устанавливаем дополнительные детали аутентификации (например, IP-адрес и сессию)
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Устанавливаем аутентификацию в SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Передаем запрос и ответ дальше по цепочке фильтров
        filterChain.doFilter(request, response);
    }

    /**
     * Метод для извлечения JWT-токена из cookies.
     *
     * @param request HTTP-запрос
     * @return JWT-токен, если он найден в cookies, иначе null
     */
    private String getTokenFromCookies(HttpServletRequest request) {
        // Проверяем, есть ли cookies в запросе
        if (request.getCookies() != null) {
            // Перебираем все cookies
            for (Cookie cookie : request.getCookies()) {
                // Ищем cookie с именем "jwtToken"
                if ("jwtToken".equals(cookie.getName())) {
                    // Возвращаем значение токена
                    return cookie.getValue();
                }
            }
        }
        // Если токен не найден, возвращаем null
        return null;
    }
}