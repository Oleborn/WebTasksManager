package oleborn.todospringtest.repository;

import oleborn.todospringtest.model.jwt_authentication.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional <AppUser> findByUsername(String username);
}
