package in.thomascook.booking.dto;

import in.thomascook.booking.model.Hotel;
import in.thomascook.booking.model.Room;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public record HotelResponse(Long id, String name, String location, String address,
    String description, BigDecimal rating, String imageUrl, List<String> amenities,
    List<RoomResponse> rooms) {
    public static HotelResponse from(Hotel h) {
        return new HotelResponse(h.getId(), h.getName(), h.getLocation(), h.getAddress(),
            h.getDescription(), h.getRating(), h.getImageUrl(),
            Arrays.stream(h.getAmenities().split(",")).map(String::trim).toList(),
            h.getRooms().stream().map(RoomResponse::from).toList());
    }
    public record RoomResponse(Long id, String roomType, String description,
                               BigDecimal pricePerNight, Integer inventory) {
        static RoomResponse from(Room r) {
            return new RoomResponse(r.getId(), r.getRoomType(), r.getDescription(),
                r.getPricePerNight(), r.getInventory());
        }
    }
}
