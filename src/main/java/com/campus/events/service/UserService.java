package com.campus.events.service;

import com.campus.events.dto.UserRegistrationDto;
import com.campus.events.model.Role;
import com.campus.events.model.User;
import com.campus.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerStudent(UserRegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken.");
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use.");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setDepartment(registrationDto.getDepartment());
        user.setRole(Role.STUDENT);
        user.setVerified(false);
        
        // Generate 6 digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);

        User savedUser = userRepository.save(user);
        
        // Send OTP
        emailService.sendOtpEmail(savedUser.getEmail(), otp);
        
        return savedUser;
    }
    
    public User registerAdmin(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setRole(Role.ADMIN);
        user.setVerified(true);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean verifyUserOtp(String email, String otp) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.isVerified() && otp.equals(user.getOtp())) {
                user.setVerified(true);
                user.setOtp(null); // Clear OTP after success
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
