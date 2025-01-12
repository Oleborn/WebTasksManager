package oleborn.todospringtest.security.jwt_authentication;

import lombok.*;
import oleborn.todospringtest.model.jwt_authentication.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, представляющий пользователя в системе.
 * Реализует интерфейс UserDetails, который используется Spring Security
 * для получения информации о пользователе и его правах доступа.
 */
@Getter // Lombok: автоматически генерирует геттеры для всех полей (@Data тут избыточна и не нужна)
@Builder // Lombok: предоставляет builder для удобного создания объектов
@AllArgsConstructor // Lombok: генерирует конструктор со всеми аргументами
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Lombok: генерирует приватный конструктор без аргументов
public class UserPrincipal implements UserDetails {

    private String username; // Имя пользователя
    private String password; // Пароль пользователя
    private List<Role> roles; // Список ролей пользователя

    /**
     * Возвращает список прав доступа (ролей) пользователя.
     * Каждая роль преобразуется в объект SimpleGrantedAuthority.
     *
     * @return Коллекция объектов GrantedAuthority, представляющих роли пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString())) // Преобразуем роль в GrantedAuthority
                .collect(Collectors.toList()); // Собираем роли в список
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return Пароль пользователя.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return Имя пользователя.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Проверяет, не истек ли срок действия учетной записи.
     *
     * @return true, если срок действия учетной записи не истек, иначе false.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Учетная запись никогда не истекает
    }

    /**
     * Проверяет, не заблокирована ли учетная запись.
     *
     * @return true, если учетная запись не заблокирована, иначе false.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Учетная запись никогда не блокируется
    }

    /**
     * Проверяет, не истек ли срок действия учетных данных (пароля).
     *
     * @return true, если срок действия учетных данных не истек, иначе false.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Учетные данные никогда не истекают
    }

    /**
     * Проверяет, активна ли учетная запись.
     *
     * @return true, если учетная запись активна, иначе false.
     */
    @Override
    public boolean isEnabled() {
        return true; // Учетная запись всегда активна
    }
}