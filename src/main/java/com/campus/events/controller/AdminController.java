package com.campus.events.controller;

import com.campus.events.model.Event;
import com.campus.events.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "admin-dashboard";
    }

    @GetMapping("/events/new")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin-event-form";
    }

    @PostMapping("/events")
    public String createOrUpdateEvent(@Valid @ModelAttribute("event") Event event, 
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "admin-event-form";
        }
        eventService.saveEvent(event);
        return "redirect:/admin/dashboard?success";
    }
    
    @GetMapping("/events/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "admin-event-form";
    }

    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/dashboard?deleted";
    }
}
