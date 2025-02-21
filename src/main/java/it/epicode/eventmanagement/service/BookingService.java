package it.epicode.eventmanagement.service;

import it.epicode.eventmanagement.entities.Booking;
import it.epicode.eventmanagement.entities.Event;
import it.epicode.eventmanagement.entities.User;
import it.epicode.eventmanagement.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking bookEvent(User user, Event event) {
        if (event.getAvailableSeats() <= 0) {
            throw new RuntimeException("Non ci sono più posti disponibili per questo evento");
        }
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUserId(user.getId());
    }

    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}

