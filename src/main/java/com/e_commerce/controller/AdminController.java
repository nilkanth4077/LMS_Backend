package com.e_commerce.controller;

import com.e_commerce.dto.TutorDto;
import com.e_commerce.entity.Course;
import com.e_commerce.entity.User;
import com.e_commerce.service.CourseService;
import com.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Admin!";
    }

    @PostMapping("/course/add")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PostMapping("/add_tutor")
    public ResponseEntity<String> addTutor(@RequestBody TutorDto tutorDto) {
        User tutor = new User();
        tutor.setFirstName(tutorDto.getFirstName());
        tutor.setLastName(tutorDto.getLastName());
        tutor.setPassword(passwordEncoder.encode(tutorDto.getPassword()));
        tutor.setEmail(tutorDto.getEmail());
        tutor.setRole("TUTOR");
        tutor.setMobile(tutorDto.getMobile());
        tutor.setDob(tutorDto.getDob());
        tutor.setGender(tutorDto.getGender());
        tutor.setLocation(tutorDto.getLocation());
        tutor.setProfession(tutorDto.getProfession());

        userService.addTutor(tutor);
        return ResponseEntity.ok("Tutor added successfully!");
    }
}
