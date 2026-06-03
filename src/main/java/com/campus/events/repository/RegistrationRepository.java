package com.campus.events.repository;

import com.campus.events.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserId(Long userId);
    List<Registration> findByEventId(Long eventId);
    boolean existsByUserIdAndEventIdAndStatus(Long userId, Long eventId, String status);
    long countByEventIdAndStatus(Long eventId, String status);
}
