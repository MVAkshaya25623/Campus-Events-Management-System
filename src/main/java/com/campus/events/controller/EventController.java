package com.campus.events.controller;

import com.campus.events.model.Event;
import com.campus.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping({"/", "/events"})
    public String listEvents(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "6") int size,
                             @RequestParam(required = false) String keyword,
                             @RequestParam(required = false) String department,
                             @RequestParam(required = false) String type) {
        
        Page<Event> eventPage = eventService.searchEvents(keyword, department, type, PageRequest.of(page, size));
        
        model.addAttribute("events", eventPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("department", department);
        model.addAttribute("type", type);
        
        return "events";
    }

    @GetMapping("/events/{id}")
    public String viewEventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "event-details";
    }
}
