package com.campus.events.service;

import com.campus.events.model.Event;
import com.campus.events.model.Registration;
import com.campus.events.model.User;
import com.campus.events.repository.EventRepository;
import com.campus.events.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private QrCodeService qrCodeService;

    public Registration registerUserForEvent(User user, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (registrationRepository.existsByUserIdAndEventIdAndStatus(user.getId(), eventId, "REGISTERED")) {
            throw new RuntimeException("User already registered for this event.");
        }

        long currentRegistrations = registrationRepository.countByEventIdAndStatus(eventId, "REGISTERED");
        if (currentRegistrations >= event.getCapacity()) {
            throw new RuntimeException("Event is already at full capacity.");
        }

        Registration registration = new Registration(user, event, LocalDateTime.now(), "REGISTERED");
        
        // Save first to get ID
        registration = registrationRepository.save(registration);
        
        // Generate QR code and update
        String qrUrl = qrCodeService.generateQrCodeUrl(registration.getId(), user.getId(), event.getId());
        registration.setQrCodeData(qrUrl);
        registration = registrationRepository.save(registration);
        
        emailService.sendRegistrationEmail(user.getEmail(), event.getTitle());

        return registration;
    }

    public void cancelRegistration(Long registrationId, Long userId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
                
        if (!registration.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to cancel this registration.");
        }
        
        registration.setStatus("CANCELLED");
        registrationRepository.save(registration);
    }
    
    public List<Registration> getUserRegistrations(Long userId) {
        return registrationRepository.findByUserId(userId);
    }
}
