package thalia.ParkingManager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.exceptionhandler.NotFoundException;
import thalia.ParkingManager.model.Vehicle;
import thalia.ParkingManager.repository.VehicleRepository;
import thalia.ParkingManager.service.VehicleService;
import thalia.ParkingManager.service.exception.BusinessException;
import thalia.ParkingManager.service.exception.ValidateFields;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
		private static final Long UNCHANGEABLE_VEHICLE_ID = 0L;
		private final VehicleRepository vehicleRepository;

		public VehicleServiceImpl(VehicleRepository vehicleRepository) {

				this.vehicleRepository = vehicleRepository;
		}

		@Transactional(readOnly = true)
		public List<Vehicle> findAll() {
				return this.vehicleRepository.findAll();
		}

		@Transactional(readOnly = true)
		public Vehicle findById(Long id) {
				return this.vehicleRepository.findById(id).orElseThrow(NotFoundException::new);
		}

		@Transactional
		public Vehicle create(Vehicle vehicleCreate) {
				new ValidateFields(vehicleCreate.getLicensePlate(), "field license plate is mandatory");
				new ValidateFields(vehicleCreate.getModel(), "field model is mandatory");
				new ValidateFields(vehicleCreate.getColor(), "field color is mandatory");
				new ValidateFields(vehicleCreate.getVehicleType(), "field vehicle type is mandatory");
				new ValidateFields(vehicleCreate.getVehicleType().getId(), "field vehicle type is mandatory");
				if (vehicleCreate.getVehicleType().getId() == 0)
						throw new BadRequestException("field vehicle type is mandatory");
				var vehicleLicensePlate = this.findByLicensePlate(vehicleCreate.getLicensePlate());
				if (vehicleLicensePlate != null)
						throw new BadRequestException("license plate already registered");
				this.validateChangeableId(vehicleCreate.getId(), "created");
				return this.vehicleRepository.save(vehicleCreate);
		}

		@Transactional
		public Vehicle update(Long id, Vehicle vehicle) {
				this.validateChangeableId(id, "updated");
				Vehicle vehicleUpdate = this.findById(id);
				if (!vehicleUpdate.getId().equals(vehicle.getId())) {
						throw new BadRequestException("update IDs must be the same.");
				}
				return vehicleRepository.save(vehicle);
		}

		@Override
		public void delete(Long id) {
				this.validateChangeableId(id, "deleted");
				Vehicle vehicle = this.findById(id);
				vehicleRepository.delete(vehicle);
		}

		private void validateChangeableId(Long id, String operation) {
				if (UNCHANGEABLE_VEHICLE_ID.equals(id)) {
						throw new BusinessException("vehicle with id %d can not be %s.".formatted(UNCHANGEABLE_VEHICLE_ID, operation));
				}
		}

		@Transactional(readOnly = true)
		public Vehicle findByLicensePlate(String licensePlate) {
				return vehicleRepository.findByLicensePlate(licensePlate);
		}
}
