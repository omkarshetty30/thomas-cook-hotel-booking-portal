package in.thomascook.booking.dto;

import in.thomascook.booking.model.Booking;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(
    String bookingReference, String hotelName, String roomType, String guestName, String email,
    LocalDate checkIn, LocalDate checkOut, int nights, BigDecimal totalAmount, String status
) {
    public static BookingResponse from(Booking b) {
        return new BookingResponse(b.getBookingReference(), b.getHotel().getName(),
            b.getRoom().getRoomType(), b.getGuestName(), b.getEmail(), b.getCheckIn(),
            b.getCheckOut(), b.getNights(), b.getTotalAmount(), b.getStatus());
    }
}
