package it.epicode.eventmanagement.controller;

import it.epicode.eventmanagement.entities.Event;
import it.epicode.eventmanagement.entities.User;
import it.epicode.eventmanagement.enumerated.Role;
import it.epicode.eventmanagement.service.EventService;
import it.epicode.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService; // ✅ Aggiunto per recuperare l'utente dal database

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestParam String title,
                                         @RequestParam String description,
                                         @RequestParam String location,
                                         @RequestParam int availableSeats,
                                         @RequestParam Long organizerId) { // ✅ Passa solo l'ID dell'organizzatore

        // ✅ Recupera l'utente dal database
        User organizer = userService.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("Organizzatore non trovato con ID: " + organizerId));

        // ✅ Controlla se il ruolo dell'utente è valido
        if (organizer.getRole() != Role.USER && organizer.getRole() != Role.ORGANIZER) {
            return ResponseEntity.badRequest().body("L'utente non ha i permessi per creare un evento.");
        }

        // ✅ Crea l'evento
        Event event = eventService.createEvent(title, description, location, availableSeats, organizer);
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Optional<Event>> getEventById(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Evento eliminato con successo.");
    }
}

