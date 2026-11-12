package in.thomascook.booking.controller;

import in.thomascook.booking.dto.*;
import in.thomascook.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }

    @PostMapping
    public BookingResponse create(@Valid @RequestBody BookingRequest request) {
        return service.create(request);
    }

    @GetMapping("/{reference}")
    public BookingResponse find(@PathVariable String reference, @RequestParam String email) {
        return service.find(reference, email);
    }

    @DeleteMapping("/{reference}")
    public BookingResponse cancel(@PathVariable String reference, @RequestParam String email) {
        return service.cancel(reference, email);
    }
}
