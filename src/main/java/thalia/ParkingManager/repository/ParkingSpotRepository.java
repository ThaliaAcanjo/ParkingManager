package thalia.ParkingManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thalia.ParkingManager.model.Parking;
import thalia.ParkingManager.model.ParkingSpot;
import thalia.ParkingManager.model.VehicleType;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
		ParkingSpot findByParkingAndVehicleTypeAndSpotNumber(Parking parking, VehicleType vehicleType, String spotNumber);

		@Query("SELECT ps FROM tb_parking_spot ps JOIN FETCH ps.vehicleType vt WHERE ps.parking = :parking")
		List<ParkingSpot> findByParking(@Param("parking") Parking parking);

		List<ParkingSpot> findByParkingAndVehicleType(Parking parking, VehicleType vehicleType);

}
