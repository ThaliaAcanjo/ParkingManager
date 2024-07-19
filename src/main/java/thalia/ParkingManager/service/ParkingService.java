package thalia.ParkingManager.service;

import thalia.ParkingManager.controller.dtos.Parking.ParkingDTO;
import thalia.ParkingManager.controller.dtos.Parking.ParkingFindDTO;
import thalia.ParkingManager.model.Parking;

import java.util.List;

public interface ParkingService extends CrudService<Long, Parking> {
		List<ParkingFindDTO> getAll();
		ParkingFindDTO getById(Long id);
		ParkingFindDTO findByCnpj(String cnpj);
		ParkingDTO createParking(ParkingDTO parkingDTO);
		ParkingDTO updateById(Long id, ParkingDTO parkingDTO);
}
