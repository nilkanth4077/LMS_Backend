package com.e_commerce.service;

import com.e_commerce.repository.UserRepository;
import com.e_commerce.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.e_commerce.entity.User;
import com.e_commerce.exception.UserException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public void addTutor(User tutor) {
        tutor.setRole("TUTOR");
        userRepository.save(tutor);
    }

    public User findCustomUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id: " + userId);
    }

    public Optional<User> getProfileByToken(String token) throws UserException {
        String email = jwtUtil.extractEmail(token);

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found with email: " + email);
        }
        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        // Use an email service to send the reset link
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        System.out.println("Reset Link: " + resetLink); // Replace with actual email service
    }

    public void resetPassword(String token, String newPassword) {
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid token or user not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
