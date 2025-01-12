package oleborn.todospringtest.controllers.jwt;

import jakarta.annotation.Resource;
import oleborn.todospringtest.model.jwt_authentication.AppUser;
import oleborn.todospringtest.model.jwt_authentication.AppUserDto;
import oleborn.todospringtest.model.jwt_authentication.util.AuthDto;
import oleborn.todospringtest.repository.AppUserRepository;
import oleborn.todospringtest.services.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Resource
    private AppUserService appUserService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody AuthDto authDto) {
        System.out.println(authDto);
        appUserService.registerAppUser(authDto);
        return ResponseEntity.ok("User registered successfully!");
    }
}