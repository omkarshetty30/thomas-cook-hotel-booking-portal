package in.thomascook.booking.repository;
import in.thomascook.booking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("select distinct h from Hotel h left join fetch h.rooms where (:location is null or lower(h.location) like lower(concat('%', :location, '%')))")
    List<Hotel> searchWithRooms(@Param("location") String location);

    @Query("select distinct h from Hotel h left join fetch h.rooms where h.id = :id")
    java.util.Optional<Hotel> findDetailsById(@Param("id") Long id);
}
