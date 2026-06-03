package com.campus.events.service;

import com.campus.events.model.Event;
import com.campus.events.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Page<Event> searchEvents(String keyword, String department, String type, Pageable pageable) {
        // Convert empty strings to null for the query
        String kw = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String dept = (department != null && !department.trim().isEmpty()) ? department : null;
        String t = (type != null && !type.trim().isEmpty()) ? type : null;
        return eventRepository.searchAndFilterEvents(kw, dept, t, pageable);
    }
}
