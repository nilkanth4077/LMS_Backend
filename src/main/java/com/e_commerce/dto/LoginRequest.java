package com.e_commerce.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
        super();
        // TODO Auto-generated constructor stub
    }


}
