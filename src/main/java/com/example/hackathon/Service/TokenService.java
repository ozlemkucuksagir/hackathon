
package com.example.hackathon.Service;


import com.example.hackathon.Security.Request.JwtRequestModel;
import com.example.hackathon.Security.Response.JwtResponseModel;
import com.example.hackathon.Security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Service


//@Qualifier("inMemoryUserDetailsManager")

public class TokenService {


    private Set<String> invalidatedTokens = new HashSet<>();
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    public ResponseEntity<?> createToken(JwtRequestModel
                                                 request) throws Exception {

        final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);

        return ResponseEntity.ok(new JwtResponseModel(jwtToken));
    }

    public boolean invalidateToken(String token) {
        // Eğer token daha önceden zaten geçersiz kılınmışsa tekrar geçersiz kılmıyoruz.
        if (invalidatedTokens.contains(token)) {
            return false;
        }

        // Tokenı geçersiz kılma işlemini gerçekleştiriyoruz.
        invalidatedTokens.add(token);
        return true;
    }

    public boolean isInvalidateToken(String token) {
        // Eğer token daha önceden zaten geçersiz kılınmışsa tekrar geçersiz kılmıyoruz.
        if (invalidatedTokens.contains("Bearer "+token)) {
            return false;
        }

        // Tokenı geçersiz kılma işlemini gerçekleştiriyoruz.
        else {
            return true;
        }
    }
}