package in.thomascook.booking.controller;

import in.thomascook.booking.dto.HotelResponse;
import in.thomascook.booking.repository.HotelRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "http://localhost:5173")
public class HotelController {
    private final HotelRepository hotels;
    public HotelController(HotelRepository hotels) { this.hotels = hotels; }

    @GetMapping
    public List<HotelResponse> list(@RequestParam(required = false) String location) {
        String query = (location == null || location.isBlank()) ? null : location.trim();
        return hotels.searchWithRooms(query).stream().map(HotelResponse::from).toList();
    }

    @GetMapping("/{id}")
    public HotelResponse detail(@PathVariable Long id) {
        return hotels.findDetailsById(id).map(HotelResponse::from)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Hotel not found."));
    }
}
