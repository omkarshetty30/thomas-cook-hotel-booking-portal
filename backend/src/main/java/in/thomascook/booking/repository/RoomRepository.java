package in.thomascook.booking.repository;
import in.thomascook.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomRepository extends JpaRepository<Room, Long> {}
