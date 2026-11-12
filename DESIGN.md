# Design Notes – Hotel Booking Portal

## Architecture
The solution uses a small three-layer architecture:

1. **React UI** – collects search criteria, displays hotels/rooms, and submits booking and lookup requests.
2. **Spring Boot REST API** – validates requests, applies booking rules, calculates totals, and exposes JSON endpoints.
3. **MySQL** – persists hotels, room inventory, and bookings.

## Main data model
- `hotels`: property profile and location.
- `rooms`: room type, nightly price, and inventory count for a hotel.
- `bookings`: guest details, stay dates, booking reference, amount, and status.

A booking references one room type and reserves one unit from that room type's inventory for the selected date range.

## Booking flow
1. Guest searches by destination and dates.
2. UI requests hotel data from `GET /api/hotels`.
3. Guest selects a hotel and room type.
4. UI submits guest details and stay dates to `POST /api/bookings`.
5. Backend validates dates, verifies the room belongs to the hotel, checks overlapping confirmed bookings against inventory, calculates the number of nights and total, and creates a unique reference.
6. Guest can retrieve or cancel the booking with reference + email.

## Availability rule
Two date ranges overlap when:
`existing.checkIn < requested.checkOut AND existing.checkOut > requested.checkIn`

For each room type, the count of overlapping `CONFIRMED` bookings must be less than the room type's inventory.

## Security and validation
- Booking lookup and cancellation require reference and email.
- The server calculates price; no client-supplied total is trusted.
- DTO validation rejects invalid input.
- Database credentials are supplied through environment variables, not committed to source control.

## Assumptions
See `README.md`. The sample dataset is fictional and is included only for demonstration.

## Future improvements
Add pessimistic locking or another inventory reservation strategy for concurrent booking requests, authentication, admin screens, payment processing, email notifications, and production observability.
