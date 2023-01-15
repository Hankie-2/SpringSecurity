package kg.charginov.springsecurity.service.impl;

import kg.charginov.springsecurity.model.entity.Role;
import kg.charginov.springsecurity.model.entity.User;
import kg.charginov.springsecurity.model.enums.Status;
import kg.charginov.springsecurity.repository.RoleRepository;
import kg.charginov.springsecurity.repository.UserRepository;
import kg.charginov.springsecurity.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

     final UserRepository userRepository;

     final RoleRepository roleRepository;

     final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {

        Role role =  roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("Method 'register' - user: {} successfully registered",registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("Method 'getAll' - {} users found",result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("Method 'findByUsername' - user: {} found by username: {}",result,username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if(result == null){
            log.warn("Method 'findById' - no user found by ID: {}",id);
            return null;
        }

        log.info("Method 'findById' - user: {} found by ID: {}",result,id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("Method 'delete' - user with ID: {} successfully deleted",id);
    }
}
