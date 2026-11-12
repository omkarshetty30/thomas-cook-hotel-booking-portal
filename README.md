# Thomas Cook – Hotel Booking Portal

A full-stack hotel booking assessment project built with React + Vite, Java 21 + Spring Boot, and MySQL 8.4.

## Features
- Browse seeded hotels (5 sample properties) with location, description, amenities, room types and nightly prices.
- Search by destination and stay dates.
- View hotel details and available room types.
- Book a room using guest name and email.
- Server-side total calculation based on nights × nightly rate.
- Unique booking reference generation.
- Retrieve a booking using booking reference + guest email.
- Cancel a confirmed booking using booking reference + guest email.
- Prevent overlapping bookings for the same room.
- Compare up to three hotels.
- Responsive layout and basic client/server validation.

## Tech stack
- Frontend: React 18, Vite, plain CSS
- Backend: Java 21, Spring Boot 3.5, Spring Web, Spring Data JPA, Bean Validation
- Database: MySQL 8.4

## Requirements
- Java 21+
- Node.js 20.19+ or 22.12+
- npm
- MySQL Server 8.4+
- Maven (or use the included Maven Wrapper if added separately)

## 1. Database setup
Start MySQL Server, then run `database/schema.sql` in MySQL Workbench.

The script creates `hotel_booking` and its tables. It also inserts five hotels and room types.

## 2. Backend setup
Open a terminal in `backend/`.

Set your MySQL credentials using environment variables (recommended):
- Windows PowerShell:
  ```powershell
  $env:DB_USERNAME="root"
  $env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
  ```
- Windows CMD:
  ```cmd
  set DB_USERNAME=root
  set DB_PASSWORD=YOUR_MYSQL_PASSWORD
  ```

Run:
```bash
mvn spring-boot:run
```
Backend runs at `http://localhost:8080`.

If Maven is not installed, install Apache Maven or import the `backend/pom.xml` in an IDE such as IntelliJ IDEA. Do not put your database password in GitHub.

## 3. Frontend setup
Open a second terminal in `frontend/`:
```bash
npm install
npm run dev
```
Open the URL printed by Vite (usually `http://localhost:5173`).

The Vite development server proxies `/api` requests to `http://localhost:8080`.

## API overview
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/hotels` | List/search hotels. Optional `location`, `checkIn`, `checkOut` |
| GET | `/api/hotels/{id}` | Hotel details and room types |
| POST | `/api/bookings` | Create booking |
| GET | `/api/bookings/{reference}?email=` | Retrieve booking |
| DELETE | `/api/bookings/{reference}?email=` | Cancel booking |

### Create booking example
```json
{
  "hotelId": 1,
  "roomId": 1,
  "guestName": "Aarav Sharma",
  "email": "aarav@example.com",
  "checkIn": "2026-12-10",
  "checkOut": "2026-12-13"
}
```

## Assumptions
- Dates are interpreted in the hotel's local calendar; check-out date is exclusive.
- A booking reserves one room of the selected room type.
- A room type has a fixed inventory count.
- Prices are stored in INR per room per night and do not include optional taxes/fees.
- Booking lookup and cancellation require both booking reference and the same guest email used at booking.
- Cancelled bookings no longer block inventory.

## Challenges and solutions
- **Double booking:** availability is checked on the server before saving; booking creation is transactional.
- **Trusting browser totals:** the backend calculates nights and total amount; client-supplied totals are never accepted.
- **Private booking access:** retrieval/cancellation require matching booking reference and email.
- **Local setup differences:** frontend and backend run independently; Vite proxies API requests to Spring Boot.

## Possible enhancements
- Payment gateway integration and refunds.
- Authentication, admin hotel/room management, and role-based access.
- Email/SMS booking confirmation.
- Stronger concurrency protection using database locking for high-traffic inventory.
- Taxes, coupons, reviews, maps, pagination, and production deployment.

## AI assistance disclosure
AI assistance was used to draft parts of the implementation and documentation. The candidate should review, run, test, understand, and adapt the code before submission, and disclose assistance in accordance with the assessment rules.

## Submission checklist
- Add your own resume PDF (including college name) to the submission email.
- Push this project to your own GitHub repository and include the repository URL.
- Review and personalize `DESIGN.md`.
- Run the project and capture genuine screenshots.
- Do not claim features were tested unless you have run them successfully.
