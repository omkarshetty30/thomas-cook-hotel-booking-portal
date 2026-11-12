package in.thomascook.booking.repository;
import in.thomascook.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select count(b) from Booking b where b.room.id = :roomId and b.status = :status and b.checkIn < :checkOut and b.checkOut > :checkIn")
    long countOverlapping(@Param("roomId") Long roomId, @Param("status") String status,
                          @Param("checkOut") LocalDate checkOut, @Param("checkIn") LocalDate checkIn);
    Optional<Booking> findByBookingReferenceAndEmailIgnoreCase(String reference, String email);
    List<Booking> findByRoomIdAndStatusAndCheckInLessThanAndCheckOutGreaterThan(
        Long roomId, String status, LocalDate checkOut, LocalDate checkIn);
}
