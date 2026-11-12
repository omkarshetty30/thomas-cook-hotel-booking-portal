package in.thomascook.booking.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record BookingRequest(
    @NotNull Long hotelId,
    @NotNull Long roomId,
    @NotBlank @Size(max = 120) String guestName,
    @NotBlank @Email @Size(max = 180) String email,
    @NotNull LocalDate checkIn,
    @NotNull LocalDate checkOut
) {}
