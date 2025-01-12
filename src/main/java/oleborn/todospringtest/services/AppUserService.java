package oleborn.todospringtest.services;

import jakarta.annotation.Resource;
import oleborn.todospringtest.model.jwt_authentication.AppUser;
import oleborn.todospringtest.model.jwt_authentication.AppUserDto;
import oleborn.todospringtest.model.jwt_authentication.Role;
import oleborn.todospringtest.model.jwt_authentication.util.AuthDto;
import oleborn.todospringtest.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Resource
    private AppUserRepository appUserRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    public void registerAppUser(AuthDto authDto) {
        authDto.setPassword(passwordEncoder.encode(authDto.getPassword()));
        AppUserDto appUserDto = AppUserDto.builder()
                .username(authDto.getUsername())
                .password(authDto.getPassword())
                .roles(List.of(Role.USER))
                .build();
        appUserRepository.save(new ModelMapper().map(appUserDto, AppUser.class));
    }
}
