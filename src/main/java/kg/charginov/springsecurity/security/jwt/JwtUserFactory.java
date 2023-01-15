package kg.charginov.springsecurity.security.jwt;

import kg.charginov.springsecurity.model.entity.Role;
import kg.charginov.springsecurity.model.entity.User;
import kg.charginov.springsecurity.model.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(
              user.getId(),
              user.getUsername(),
              user.getFirstName(),
              user.getLastName(),
            user.getPassword(),
              user.getEmail(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdatedAt(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<? extends GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
