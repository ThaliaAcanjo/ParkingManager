package thalia.ParkingManager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.exceptionhandler.NotFoundException;
import thalia.ParkingManager.model.VehicleType;
import thalia.ParkingManager.repository.VehicleTypeRepository;
import thalia.ParkingManager.service.VehicleTypeService;
import thalia.ParkingManager.service.exception.BusinessException;

import java.util.List;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {
		private static final Long UNCHANGEABLE_VEHICLE_TYPE_ID = 0L;
		private final VehicleTypeRepository vehicleTypeRepository;

		public VehicleTypeServiceImpl(VehicleTypeRepository vehicleTypeRepository) {
				this.vehicleTypeRepository = vehicleTypeRepository;
		}

		@Transactional(readOnly = true)
		public List<VehicleType> findAll() {
				return this.vehicleTypeRepository.findAll();
		}

		@Transactional(readOnly = true)
		public VehicleType findById(Long id) {
				var x = this.vehicleTypeRepository.findById(id).orElseThrow(NotFoundException::new);
				return x;
		}

		@Transactional
		public VehicleType create(VehicleType entity) {
				throw new BusinessException("Vehicle type %d can not be %s.".formatted(UNCHANGEABLE_VEHICLE_TYPE_ID, "created"));
		}

		@Transactional
		public VehicleType update(Long id, VehicleType entity) {
				this.validateChangeableId(id, "updated");
				VehicleType vehicleType = this.findById(id);
				if (!vehicleType.getId().equals(vehicleType.getId())){
						throw new BadRequestException("update IDs must be the same.");
				}
				return vehicleTypeRepository.save(entity);
		}

		@Override
		public void delete(Long id) {
				this.validateChangeableId(id, "deleted");
				throw new BusinessException("Vehicle type %d can not be %s.".formatted(UNCHANGEABLE_VEHICLE_TYPE_ID, "deleted"));
		}

		private void validateChangeableId(Long id, String operation) throws BusinessException {
				if (UNCHANGEABLE_VEHICLE_TYPE_ID.equals(id)) {
						throw new BusinessException("Vehicle type %d can not be %s.".formatted(UNCHANGEABLE_VEHICLE_TYPE_ID, operation));
				}
		}
}
