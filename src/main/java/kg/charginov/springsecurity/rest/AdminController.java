package kg.charginov.springsecurity.rest;

import kg.charginov.springsecurity.model.dto.AdminUserDto;
import kg.charginov.springsecurity.model.dto.UserDto;
import kg.charginov.springsecurity.model.entity.User;
import kg.charginov.springsecurity.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/admin")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminController {

    final UserService userService;

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
