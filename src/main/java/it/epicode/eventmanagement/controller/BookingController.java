package it.epicode.eventmanagement.controller;

import it.epicode.eventmanagement.entities.Booking;
import it.epicode.eventmanagement.entities.Event;
import it.epicode.eventmanagement.entities.User;
import it.epicode.eventmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookEvent(@RequestParam User user, @RequestParam Event event) {
        Booking booking = bookingService.bookEvent(user, event);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable User user) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(user));
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Prenotazione annullata con successo.");
    }
}
