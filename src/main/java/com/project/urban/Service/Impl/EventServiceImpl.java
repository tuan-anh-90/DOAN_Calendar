package com.project.urban.Service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.urban.Controller.CalendarController.EventCreateParams;
import com.project.urban.Controller.CalendarController.EventMoveParams;
import com.project.urban.Controller.CalendarController.SetColorParams;
import com.project.urban.Entity.Event;
import com.project.urban.Repository.EventRepository;
import com.project.urban.Service.EventService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{
	private final EventRepository eventRepository;

    @Override
    public Iterable<Event> findEventsBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findBetween(start, end);
    }

    @Override
    public Event createEvent(EventCreateParams params) {
        Event event = new Event();
        event.setStart(params.start);
        event.setEnd(params.end);
        event.setText(params.text);
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event moveEvent(EventMoveParams params) {
        Optional<Event> optionalEvent = eventRepository.findById(params.id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setStart(params.start);
            event.setEnd(params.end);
            eventRepository.save(event);
            return event;
        } else {
            throw new IllegalArgumentException("Event not found");
        }
    }

    @Override
    public Event setColor(SetColorParams params) {
        Optional<Event> optionalEvent = eventRepository.findById(params.id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setColor(params.color);
            eventRepository.save(event);
            return event;
        } else {
            throw new IllegalArgumentException("Event not found");
        }
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
