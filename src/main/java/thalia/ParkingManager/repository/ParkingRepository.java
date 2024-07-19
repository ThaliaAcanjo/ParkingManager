package thalia.ParkingManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thalia.ParkingManager.model.Parking;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
		Parking findByCnpj(String cnpj);
}
