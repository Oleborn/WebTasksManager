//package oleborn.todospringtest.security.jwt_authentication;
//
//import jakarta.annotation.Resource;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import oleborn.todospringtest.model.jwt_authentication.util.JwtUtil;
//import oleborn.todospringtest.services.UserPrincipalService;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///**
// * Фильтр для обработки JWT-токенов в каждом HTTP-запросе.
// * Этот фильтр извлекает JWT-токен из заголовка "Authorization",
// * проверяет его валидность и, если токен корректен, устанавливает
// * аутентификацию пользователя в SecurityContextHolder.
// *
// * Фильтр выполняется один раз для каждого запроса (благодаря наследованию от OncePerRequestFilter).
// */
//@Component // Указывает, что этот класс является компонентом Spring и будет управляться Spring-контейнером
//public class JwtRequestFilterAuthorization extends OncePerRequestFilter {
//
//    @Resource // Внедрение зависимости сервиса для работы с пользовательскими данными
//    private UserPrincipalService userDetailsService;
//
//    @Resource // Внедрение зависимости утилиты для работы с JWT-токенами
//    private JwtUtil jwtUtil;
//
//    /**
//     * Основной метод фильтра, который выполняется для каждого HTTP-запроса.
//     *
//     * @param request  HTTP-запрос
//     * @param response HTTP-ответ
//     * @param filterChain Цепочка фильтров, которая позволяет передать запрос дальше
//     * @throws ServletException Если возникает ошибка при обработке запроса
//     * @throws IOException Если возникает ошибка ввода-вывода
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // Получаем заголовок "Authorization" из HTTP-запроса
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null; // Имя пользователя, извлеченное из JWT-токена
//        String jwt = null; // JWT-токен, извлеченный из заголовка
//
//        // Проверяем, что заголовок "Authorization" существует и начинается с "Bearer "
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7); // Извлекаем JWT-токен (убираем "Bearer ")
//            username = jwtUtil.extractUsername(jwt); // Извлекаем имя пользователя из JWT-токена
//        }
//
//        // Если имя пользователя извлечено и аутентификация еще не установлена в SecurityContextHolder
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Загружаем данные пользователя (userPrincipal) по имени пользователя
//            UserPrincipal userPrincipal = this.userDetailsService.loadUserByUsername(username);
//
//            // Проверяем, что JWT-токен валиден для данного пользователя
//            if (jwtUtil.validateToken(jwt, userPrincipal)) {
//                // Создаем объект аутентификации на основе данных пользователя
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userPrincipal, null, userPrincipal.getAuthorities());
//
//                // Устанавливаем дополнительные детали аутентификации (например, IP-адрес и сессию)
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // Устанавливаем аутентификацию в SecurityContextHolder
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        // Передаем запрос и ответ дальше по цепочке фильтров
//        filterChain.doFilter(request, response);
//    }
//}