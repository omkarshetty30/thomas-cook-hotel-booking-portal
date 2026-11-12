package in.thomascook.booking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "booking_reference", nullable = false, unique = true)
    private String bookingReference;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "guest_name")
    private String guestName;
    private String email;
    @Column(name = "check_in")
    private LocalDate checkIn;
    @Column(name = "check_out")
    private LocalDate checkOut;
    private Integer nights;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    private String status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Booking() {}

    public Booking(String bookingReference, Hotel hotel, Room room, String guestName, String email,
                   LocalDate checkIn, LocalDate checkOut, int nights, BigDecimal totalAmount) {
        this.bookingReference = bookingReference;
        this.hotel = hotel; this.room = room; this.guestName = guestName; this.email = email;
        this.checkIn = checkIn; this.checkOut = checkOut; this.nights = nights;
        this.totalAmount = totalAmount; this.status = "CONFIRMED"; this.createdAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public String getBookingReference() { return bookingReference; }
    public Hotel getHotel() { return hotel; }
    public Room getRoom() { return room; }
    public String getGuestName() { return guestName; }
    public String getEmail() { return email; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public Integer getNights() { return nights; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void cancel() { this.status = "CANCELLED"; }
}
