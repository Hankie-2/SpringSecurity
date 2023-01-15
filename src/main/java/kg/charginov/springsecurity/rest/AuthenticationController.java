package kg.charginov.springsecurity.rest;

import kg.charginov.springsecurity.model.dto.AuthenticationRequest;
import kg.charginov.springsecurity.model.dto.AuthenticationResponse;
import kg.charginov.springsecurity.model.entity.User;
import kg.charginov.springsecurity.security.jwt.JwtTokenProvider;
import kg.charginov.springsecurity.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

     AuthenticationManager authenticationManager;

     JwtTokenProvider jwtTokenProvider;

     UserService userService;

     @PostMapping("/login")
     public ResponseEntity login(@RequestBody AuthenticationRequest request){
         try {
            String username = request.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,request.getPassword()));
             User user = userService.findByUsername(username);
             if(user == null){
                 throw new UsernameNotFoundException("User with username " + username + " not found!");
             }

             String token = jwtTokenProvider.createToken(username,user.getRoles());

             AuthenticationResponse response = new AuthenticationResponse(username,token);
             return ResponseEntity.ok(response);

         }catch (AuthenticationException e){
             throw new BadCredentialsException("Invalid username or password");
         }
     }

}
