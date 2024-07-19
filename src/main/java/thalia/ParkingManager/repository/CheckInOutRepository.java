package thalia.ParkingManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thalia.ParkingManager.model.CheckInOut;

@Repository
public interface CheckInOutRepository extends JpaRepository<CheckInOut, Long> {
}
