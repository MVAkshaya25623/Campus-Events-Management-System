package com.campus.events;

import com.campus.events.dto.UserRegistrationDto;
import com.campus.events.model.Event;
import com.campus.events.service.EventService;
import com.campus.events.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class EventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(UserService userService, EventService eventService) {
		return args -> {
			try {
				// Create Admin
				UserRegistrationDto admin = new UserRegistrationDto();
				admin.setUsername("admin");
				admin.setPassword("admin123");
				admin.setEmail("admin@campus.edu");
				userService.registerAdmin(admin);

				// Create Student
				UserRegistrationDto student = new UserRegistrationDto();
				student.setUsername("student1");
				student.setPassword("student123");
				student.setEmail("student1@campus.edu");
				student.setDepartment("Computer Science");
				userService.registerStudent(student);

				// Create Sample Events
				Event e1 = new Event();
				e1.setTitle("Intro to AI Workshop");
				e1.setDescription("Learn the basics of Artificial Intelligence and Machine Learning.\nThis is a hands-on workshop, so please bring your laptops.");
				e1.setDate(LocalDateTime.now().plusDays(5).withHour(10).withMinute(0));
				e1.setLocation("Room 401, Tech Building");
				e1.setDepartment("Computer Science");
				e1.setType("Workshop");
				e1.setCapacity(50);
				eventService.saveEvent(e1);

				Event e2 = new Event();
				e2.setTitle("Annual Business Seminar");
				e2.setDescription("Join industry leaders as they discuss the future of global markets.");
				e2.setDate(LocalDateTime.now().plusDays(10).withHour(14).withMinute(30));
				e2.setLocation("Main Auditorium");
				e2.setDepartment("Business");
				e2.setType("Seminar");
				e2.setCapacity(200);
				eventService.saveEvent(e2);

				Event e3 = new Event();
				e3.setTitle("Spring Hackathon 2026");
				e3.setDescription("A 48-hour coding marathon! Build amazing projects and win prizes.");
				e3.setDate(LocalDateTime.now().plusDays(15).withHour(18).withMinute(0));
				e3.setLocation("Innovation Lab");
				e3.setDepartment("Engineering");
				e3.setType("Hackathon");
				e3.setCapacity(100);
				eventService.saveEvent(e3);

			} catch (Exception e) {
				System.out.println("Data already initialized or error: " + e.getMessage());
			}
		};
	}
}
