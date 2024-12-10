package com.e_commerce.dto;

import com.e_commerce.entity.User;
import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private String role;
    private User user;
}
