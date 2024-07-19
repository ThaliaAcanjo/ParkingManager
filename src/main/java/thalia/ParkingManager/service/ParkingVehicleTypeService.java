package thalia.ParkingManager.service;

import org.springframework.stereotype.Service;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeDTO;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeFindDTO;
import thalia.ParkingManager.model.ParkingVehicleType;

import java.util.List;

@Service
public interface ParkingVehicleTypeService extends CrudService<Long, ParkingVehicleType>{
		List<ParkingVehicleTypeDTO> getAll();
		ParkingVehicleTypeFindDTO getById(Long id);
		List<ParkingVehicleTypeFindDTO> findByParkingId(Long id);
		ParkingVehicleTypeDTO updateById(Long id, ParkingVehicleTypeDTO dto);
		ParkingVehicleTypeDTO createParkedVehicleType(ParkingVehicleTypeDTO dto);
}
