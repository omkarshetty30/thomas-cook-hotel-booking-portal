package in.thomascook.booking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Column(name = "room_type")
    private String roomType;
    private String description;
    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;
    private Integer inventory;

    protected Room() {}
    public Long getId() { return id; }
    public Hotel getHotel() { return hotel; }
    public String getRoomType() { return roomType; }
    public String getDescription() { return description; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public Integer getInventory() { return inventory; }
}
