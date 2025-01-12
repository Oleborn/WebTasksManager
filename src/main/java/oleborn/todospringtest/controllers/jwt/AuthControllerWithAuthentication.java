package oleborn.todospringtest.controllers.jwt;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import oleborn.todospringtest.model.jwt_authentication.util.AuthDto;
import oleborn.todospringtest.model.jwt_authentication.util.JwtUtil;
import oleborn.todospringtest.security.jwt_authentication.UserPrincipal;
import oleborn.todospringtest.services.UserPrincipalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthControllerWithAuthentication {

    @Resource
    private UserPrincipalService userDetailsService;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * AuthenticationManager — это интерфейс в Spring Security, который отвечает за процесс аутентификации.
     * Его основная задача — проверить, что предоставленные учетные данные (например, имя пользователя и пароль)
     * корректны.
     * <p>
     * Когда вы вызываете authenticationManager.authenticate(), вы передаете объект
     * Authentication (например, UsernamePasswordAuthenticationToken).
     * <p>
     * ProviderManager передает этот объект всем зарегистрированным провайдерам (AuthenticationProvider), пока один из них не сможет выполнить аутентификацию.
     * <p>
     * Если провайдер (например, DaoAuthenticationProvider) успешно аутентифицирует пользователя, он возвращает полностью аутентифицированный объект Authentication.
     * Если ни один провайдер не может аутентифицировать пользователя, выбрасывается исключение AuthenticationException.
     * <p>
     * DaoAuthenticationProvider — это провайдер, который использует UserDetailsService для загрузки пользователя и PasswordEncoder для проверки пароля.
     * <p>
     * DaoAuthenticationProvider вызывает UserDetailsService.loadUserByUsername(username), чтобы загрузить пользователя из базы данных.
     * <p>
     * Он сравнивает введенный пароль с паролем, хранящимся в базе данных, используя PasswordEncoder.
     * <p>
     * Если пароль верный, создается полностью аутентифицированный объект Authentication.
     */
    @Resource
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthDto authDto)
    {
                    /*
        Если ваше приложение использует только один способ аутентификации,
        вы можете обойтись без AuthenticationManager и реализовать аутентификацию вручную
        т.к AuthenticationManager может показаться избыточным
         */
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword())
            );
        return ResponseEntity.ok(jwtUtil.generateToken(userDetailsService.loadUserByUsername(authDto.getUsername())));
    }
}