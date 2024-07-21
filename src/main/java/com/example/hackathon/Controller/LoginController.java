package com.example.hackathon.Controller;


import com.example.hackathon.Security.Request.JwtRequestModel;
import com.example.hackathon.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.example.hackathon.Service.TokenService;


@RestController
//@Qualifier("inMemoryUserDetailsManager")
@RequestMapping("/auth")

public class LoginController {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestModel request) throws Exception {
        try {

            String username = request.getUsername();
            String password = request.getPassword();

            // Kullanıcı adı ve şifre boş kontrolü
            if (username == null || password == null ||  password.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password cannot be empty!");
            }
            authenticationManager.authenticate(
                    new
                            UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login information.");

        }


        return tokenService.createToken(request);

    }

    @GetMapping("/logout/{token}")
    public ResponseEntity<?> logout(@PathVariable String token) {
        try {

            boolean success = tokenService.invalidateToken("Bearer " + token);
            if (success) {
                return ResponseEntity.ok("Logout successful");
            } else {
                return ResponseEntity.badRequest().body("Logout failed. Invalid token.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during logout");
        }
    }

   /*
  @GetMapping("/logout")
   public ResponseEntity<?> logout(@RequestBody JwtResponseModel response) {
       try {
           boolean success = tokenService.isInvalidateToken(response.getToken());
           if (success) {
               return ResponseEntity.ok("Logout successful");

           } else {
               return ResponseEntity.badRequest().body("Logout failed. Invalid token.");
           }
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during logout");
       }
   }*/
}