package thalia.ParkingManager.service;

import thalia.ParkingManager.model.Vehicle;

public interface VehicleService extends CrudService<Long, Vehicle>{
		Vehicle findByLicensePlate(String licensePlate);
}
