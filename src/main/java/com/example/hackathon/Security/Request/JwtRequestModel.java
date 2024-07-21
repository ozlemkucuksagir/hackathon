package com.example.hackathon.Security.Request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.io.Serializable;
@Data

public class JwtRequestModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2636936156391265891L;
    private String username;
    private String password;
    public JwtRequestModel() {
    }
    public JwtRequestModel(String username, String password) {
        super();
        this.username = username; this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}