package com.campus.events.repository;

import com.campus.events.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE " +
           "(:keyword IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:department IS NULL OR e.department = :department) AND " +
           "(:type IS NULL OR e.type = :type)")
    Page<Event> searchAndFilterEvents(@Param("keyword") String keyword, 
                                      @Param("department") String department, 
                                      @Param("type") String type, 
                                      Pageable pageable);
}
