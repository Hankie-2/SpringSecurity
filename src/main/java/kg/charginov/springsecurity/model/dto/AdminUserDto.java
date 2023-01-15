package kg.charginov.springsecurity.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.charginov.springsecurity.model.entity.User;
import kg.charginov.springsecurity.model.enums.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUserDto {

    Long id;
    String username;
    String firstName;
    String lastName;
    String email;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;


    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);

        return user;
    }

    public static AdminUserDto fromUser(User user){
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getUsername());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setStatus(user.getStatus().name());
        adminUserDto.setUpdatedAt(user.getUpdatedAt());
        adminUserDto.setCreatedAt(user.getCreatedAt());

        return adminUserDto;
    }

}
