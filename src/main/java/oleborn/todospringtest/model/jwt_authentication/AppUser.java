package oleborn.todospringtest.model.jwt_authentication;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    List<Role> roles;

}