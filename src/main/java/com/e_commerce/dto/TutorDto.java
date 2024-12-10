package com.e_commerce.dto;

import lombok.Data;

@Data
public class TutorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String mobile;
    private String dob;
    private String gender;
    private String location;
    private String profession;

}