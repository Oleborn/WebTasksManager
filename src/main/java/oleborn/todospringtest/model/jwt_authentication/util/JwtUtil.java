package oleborn.todospringtest.model.jwt_authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import oleborn.todospringtest.security.jwt_authentication.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Утилита для работы с JWT-токенами.
 * Позволяет генерировать, проверять и извлекать данные из JWT-токенов.
 */
@Component // Указывает, что этот класс является компонентом Spring и будет управляться Spring-контейнером
public class JwtUtil {

    // Секретный ключ для подписи JWT (внедряется из конфигурации)
    @Value("your-256-bit-secret-32-characters-long")
    private String secret;

    // Время жизни токена (10 дней, внедряется из конфигурации)
    @Value("864000000")
    private long expirationTime;

    // Ключ для подписи JWT
    private SecretKey getSigningKey() {
        // Проверка длины ключа
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 256 bits (32 characters) long");
        }
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Генерация JWT-токена на основе UserDetails.
     *
     * @param userPrincipal Данные пользователя
     * @return Сгенерированный JWT-токен
     */
    public String generateToken(UserPrincipal userPrincipal) {
        // Создаем claims (данные, которые будут включены в токен)
        Map<String, Object> claims = new HashMap<>();

        // Добавляем имя пользователя
        claims.put("username", userPrincipal.getUsername());

        // Добавляем роли пользователя
        claims.put("authorities", userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        // Генерируем токен с claims и временем истечения
        return createToken(claims, userPrincipal.getUsername());
    }

    /**
     * Создание JWT-токена.
     *
     * @param claims Дополнительные данные (например, роли)
     * @param subject Имя пользователя
     * @return Сгенерированный JWT-токен
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Дополнительные данные (если нужны)
                .setSubject(subject) // Имя пользователя
                .setIssuedAt(new Date(System.currentTimeMillis())) // Время создания
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Время истечения
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Подпись с использованием секретного ключа
                .compact(); // Сборка токена в строку
    }

    /**
     * Извлечение имени пользователя из токена.
     *
     * @param token JWT-токен
     * @return Имя пользователя
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлечение времени истечения токена.
     *
     * @param token JWT-токен
     * @return Время истечения токена
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение конкретного поля из токена.
     *
     * @param token JWT-токен
     * @param claimsResolver Функция для извлечения данных из токена
     * @return Запрошенные данные
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Извлечение всех данных из токена.
     *
     * @param token JWT-токен
     * @return Все данные из токена
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Указываем ключ для проверки подписи
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // Логируем ошибку и возвращаем null или выбрасываем исключение
            throw new RuntimeException("Не удалось извлечь данные из токена: " + e.getMessage());
        }
    }

    /**
     * Проверка, истек ли срок действия токена.
     *
     * @param token JWT-токен
     * @return true, если токен истек, иначе false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Проверка валидности токена.
     *
     * @param token JWT-токен
     * @param userDetails Данные пользователя
     * @return true, если токен валиден, иначе false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            // Логируем ошибку и возвращаем false
            return false;
        }
    }
}