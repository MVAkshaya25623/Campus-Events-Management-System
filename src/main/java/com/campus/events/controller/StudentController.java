package com.campus.events.controller;

import com.campus.events.model.User;
import com.campus.events.service.RegistrationService;
import com.campus.events.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private RegistrationService registrationService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow();
        
        model.addAttribute("registrations", registrationService.getUserRegistrations(user.getId()));
        return "student-dashboard";
    }

    @PostMapping("/events/{id}/register")
    public String registerForEvent(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName()).orElseThrow();
            registrationService.registerUserForEvent(user, id);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully registered for event!");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/events/" + id;
    }

    @PostMapping("/registrations/{id}/cancel")
    public String cancelRegistration(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName()).orElseThrow();
            registrationService.cancelRegistration(id, user.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Registration cancelled.");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/student/dashboard";
    }
}
