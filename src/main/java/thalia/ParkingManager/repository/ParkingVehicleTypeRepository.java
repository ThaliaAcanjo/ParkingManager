package thalia.ParkingManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeDTO;
import thalia.ParkingManager.model.ParkingVehicleType;

import java.util.List;

@Repository
public interface ParkingVehicleTypeRepository extends JpaRepository<ParkingVehicleType, Long> {
		List<ParkingVehicleType> findByParkingId(Long id);
		List<ParkingVehicleType> findAll();
		ParkingVehicleType findById(ParkingVehicleType id);
}
