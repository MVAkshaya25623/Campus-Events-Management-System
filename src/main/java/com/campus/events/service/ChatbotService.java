package com.campus.events.service;

import com.campus.events.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    @Autowired
    private EventService eventService;

    public String getResponse(String userMessage) {
        String msg = userMessage.toLowerCase();
        
        if (msg.contains("hi") || msg.contains("hello")) {
            return "Hello! I am the Smart Campus Assistant. How can I help you today?";
        } else if (msg.contains("register") || msg.contains("how to join")) {
            return "To register for an event, go to the 'Events' page, find an event you like, and click 'Register'. Make sure you are logged in!";
        } else if (msg.contains("location") || msg.contains("where")) {
            List<Event> events = eventService.getAllEvents();
            if (events.isEmpty()) {
                return "There are no upcoming events at the moment, so I don't have any location details.";
            }
            String locations = events.stream()
                .map(e -> "'" + e.getTitle() + "' is located at " + e.getLocation())
                .collect(Collectors.joining(". "));
            return "Here are the locations for our upcoming events: " + locations + ".";
        } else if (msg.contains("upcoming") || msg.contains("what's next") || msg.contains("when") || msg.contains("time")) {
            List<Event> events = eventService.getAllEvents();
            if (events.isEmpty()) {
                return "There are no upcoming events right now. Please check back later!";
            }
            String titles = events.stream().map(Event::getTitle).collect(Collectors.joining(", "));
            return "We have some exciting events coming up: " + titles + ". You can view all the details by navigating to the Events section!";
        } else if (msg.contains("cancel")) {
            return "You can cancel your registration from your Student Dashboard. Find the event and click 'Cancel Registration'.";
        } else if (msg.contains("qr") || msg.contains("ticket")) {
            return "After you register, a QR code is generated. You can find it on your Student Dashboard. Show this to the admin during the event.";
        } else {
            return "I'm sorry, I didn't quite understand that. You can ask me about registering, event locations, upcoming events, or how to cancel.";
        }
    }
}
