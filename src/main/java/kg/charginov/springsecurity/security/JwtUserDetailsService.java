package kg.charginov.springsecurity.security;

import kg.charginov.springsecurity.model.entity.User;
import kg.charginov.springsecurity.security.jwt.JwtUser;
import kg.charginov.springsecurity.security.jwt.JwtUserFactory;
import kg.charginov.springsecurity.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUserDetailsService implements UserDetailsService {

    final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User with username '" + username + "' not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("Method 'loadUserByUsername' - user with username: {} successfully loaded",username);

        return jwtUser;
    }
}
