package oleborn.todospringtest.model.jwt_authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    String username;
    String password;
    List<Role> roles;
}
