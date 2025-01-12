package oleborn.todospringtest.services;

import jakarta.annotation.Resource;
import oleborn.todospringtest.model.jwt_authentication.AppUser;
import oleborn.todospringtest.repository.AppUserRepository;
import oleborn.todospringtest.security.jwt_authentication.UserPrincipal;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

    @Resource
    private AppUserRepository appUserRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserPrincipal.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRoles())
            .build();
    }
}