CREATE DATABASE IF NOT EXISTS hotel_booking
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hotel_booking;

CREATE TABLE IF NOT EXISTS hotels (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(140) NOT NULL,
  location VARCHAR(140) NOT NULL,
  address VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  rating DECIMAL(2,1) NOT NULL DEFAULT 4.0,
  image_url VARCHAR(500),
  amenities VARCHAR(700) NOT NULL
);

CREATE TABLE IF NOT EXISTS rooms (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  hotel_id BIGINT NOT NULL,
  room_type VARCHAR(100) NOT NULL,
  description VARCHAR(500) NOT NULL,
  price_per_night DECIMAL(10,2) NOT NULL,
  inventory INT NOT NULL DEFAULT 1,
  CONSTRAINT fk_rooms_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  booking_reference VARCHAR(30) NOT NULL UNIQUE,
  hotel_id BIGINT NOT NULL,
  room_id BIGINT NOT NULL,
  guest_name VARCHAR(120) NOT NULL,
  email VARCHAR(180) NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  nights INT NOT NULL,
  total_amount DECIMAL(12,2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_bookings_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id),
  CONSTRAINT fk_bookings_room FOREIGN KEY (room_id) REFERENCES rooms(id),
  INDEX idx_booking_dates (room_id, check_in, check_out, status),
  INDEX idx_booking_reference_email (booking_reference, email)
);

INSERT INTO hotels (name, location, address, description, rating, image_url, amenities) VALUES
('The Orchid Grand', 'Mumbai', 'Vile Parle East, Mumbai, Maharashtra', 'A contemporary city hotel with convenient airport access, thoughtful service and comfortable rooms.', 4.5, 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=900', 'Free Wi-Fi,Pool,Restaurant,Gym,Airport shuttle'),
('Lakeview Retreat', 'Udaipur', 'Fateh Sagar Road, Udaipur, Rajasthan', 'A relaxed lakeside stay with warm hospitality and views of the Aravalli hills.', 4.7, 'https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=900', 'Free Wi-Fi,Lake view,Pool,Spa,Restaurant'),
('The Fern Residency', 'Pune', 'Shivajinagar, Pune, Maharashtra', 'A practical business-friendly hotel close to the city centre and local attractions.', 4.2, 'https://images.unsplash.com/photo-1564501049412-61c2a3083791?w=900', 'Free Wi-Fi,Restaurant,Conference room,Gym'),
('Jaipur Heritage House', 'Jaipur', 'Civil Lines, Jaipur, Rajasthan', 'A heritage-inspired property blending traditional details with modern comfort.', 4.6, 'https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=900', 'Free Wi-Fi,Courtyard,Restaurant,Pool,Parking'),
('Coastal Bay Resort', 'Goa', 'Candolim, North Goa, Goa', 'A coastal resort for guests looking for a calm break near the beach.', 4.4, 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?w=900', 'Free Wi-Fi,Beach access,Pool,Bar,Restaurant');

INSERT INTO rooms (hotel_id, room_type, description, price_per_night, inventory) VALUES
(1, 'Deluxe King', 'King bed, work desk and city-facing window.', 6200, 8),
(1, 'Executive Suite', 'Separate lounge area with premium amenities.', 9800, 3),
(2, 'Lake View Room', 'Balcony with a beautiful lake view.', 8500, 6),
(2, 'Palace Suite', 'Spacious suite with a separate sitting area.', 14500, 2),
(3, 'Superior Queen', 'Comfortable room for business and leisure stays.', 4800, 10),
(3, 'Business Suite', 'Extra space with a dedicated work area.', 7600, 4),
(4, 'Heritage Deluxe', 'Traditional decor with modern comforts.', 7100, 5),
(4, 'Royal Suite', 'Large suite with courtyard-facing views.', 12500, 2),
(5, 'Garden Room', 'Relaxed room surrounded by tropical greenery.', 6900, 7),
(5, 'Sea Breeze Suite', 'Premium suite with a private sit-out.', 11200, 3);
