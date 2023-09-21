package com.project.urban.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.project.urban.Controller.CalendarController.EventCreateParams;
import com.project.urban.Controller.CalendarController.EventDeleteParams;
import com.project.urban.Controller.CalendarController.EventMoveParams;
import com.project.urban.Controller.CalendarController.SetColorParams;
import com.project.urban.Entity.Event;

@Service
public interface EventService {
	Iterable<Event> findEventsBetween(LocalDateTime start, LocalDateTime end);
    Event createEvent(EventCreateParams params);
    Event moveEvent(EventMoveParams params);
    Event setColor(SetColorParams params);
    void deleteEvent(Long id);
}
