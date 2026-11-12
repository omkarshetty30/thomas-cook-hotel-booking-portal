package in.thomascook.booking.service;

import in.thomascook.booking.dto.BookingRequest;
import in.thomascook.booking.dto.BookingResponse;
import in.thomascook.booking.model.*;
import in.thomascook.booking.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.UUID;

@Service
public class BookingService {
    private final HotelRepository hotels;
    private final RoomRepository rooms;
    private final BookingRepository bookings;

    public BookingService(HotelRepository hotels, RoomRepository rooms, BookingRepository bookings) {
        this.hotels = hotels; this.rooms = rooms; this.bookings = bookings;
    }

    @Transactional
    public BookingResponse create(BookingRequest request) {
        if (!request.checkOut().isAfter(request.checkIn()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-out must be after check-in.");
        if (request.checkIn().isBefore(java.time.LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-in cannot be in the past.");

        Hotel hotel = hotels.findById(request.hotelId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found."));
        Room room = rooms.findById(request.roomId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room type not found."));
        if (!room.getHotel().getId().equals(hotel.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected room does not belong to this hotel.");

        long overlapping = bookings.countOverlapping(
            room.getId(), "CONFIRMED", request.checkOut(), request.checkIn());
        if (overlapping >= room.getInventory())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This room type is sold out for the selected dates.");

        int nights = Math.toIntExact(ChronoUnit.DAYS.between(request.checkIn(), request.checkOut()));
        BigDecimal total = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
        String reference = "TC" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase(Locale.ROOT);
        Booking booking = new Booking(reference, hotel, room, request.guestName().trim(),
            request.email().trim().toLowerCase(Locale.ROOT), request.checkIn(), request.checkOut(), nights, total);
        return BookingResponse.from(bookings.save(booking));
    }

    @Transactional(readOnly = true)
    public BookingResponse find(String reference, String email) {
        return BookingResponse.from(bookings.findByBookingReferenceAndEmailIgnoreCase(reference, email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found. Check the reference and email.")));
    }

    @Transactional
    public BookingResponse cancel(String reference, String email) {
        Booking booking = bookings.findByBookingReferenceAndEmailIgnoreCase(reference, email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found. Check the reference and email."));
        if ("CANCELLED".equals(booking.getStatus()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This booking is already cancelled.");
        booking.cancel();
        return BookingResponse.from(booking);
    }
}
