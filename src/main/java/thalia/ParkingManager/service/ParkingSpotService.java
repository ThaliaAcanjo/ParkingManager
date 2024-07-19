package thalia.ParkingManager.service;

import thalia.ParkingManager.model.ParkingSpot;

import java.util.List;

public interface ParkingSpotService extends CrudService<Long, ParkingSpot>{
		//List<ParkingSpot> findByParkingIdAndVehicleTypeIdAndOccupied(Long parkingId, Long vehicleTypeId, Boolean isOccupied);
		ParkingSpot findByParkingSpot(Long parkingId, Long vehicleTypeId, String spotNumber);
		List<ParkingSpot> findByParking(Long parkingId);
		List<ParkingSpot> findByParkingAndVehicleType(Long parkingId, Long vehicleTypeId);
}
