package com.campus.events.controller;

import com.campus.events.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotRestController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/query")
    public Map<String, String> processQuery(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String response = chatbotService.getResponse(message);
        
        Map<String, String> result = new HashMap<>();
        result.put("response", response);
        return result;
    }
}
