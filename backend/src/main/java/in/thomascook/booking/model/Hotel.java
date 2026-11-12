package in.thomascook.booking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String address;
    @Column(columnDefinition = "TEXT")
    private String description;
    private BigDecimal rating;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(length = 700)
    private String amenities;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    protected Hotel() {}
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }
    public BigDecimal getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public String getAmenities() { return amenities; }
    public List<Room> getRooms() { return rooms; }
}
