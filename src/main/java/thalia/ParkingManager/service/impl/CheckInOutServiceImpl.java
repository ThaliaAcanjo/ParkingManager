package thalia.ParkingManager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.model.*;
import thalia.ParkingManager.repository.ParkingRepository;
import thalia.ParkingManager.repository.CheckInOutRepository;
import thalia.ParkingManager.repository.VehicleRepository;
import thalia.ParkingManager.service.CheckInOutService;
import thalia.ParkingManager.service.exception.BusinessException;
import thalia.ParkingManager.service.exception.NotFoundException;
import thalia.ParkingManager.service.exception.ValidateFields;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CheckInOutServiceImpl implements CheckInOutService {
		private static final Long UNCHANGEABLE_VEHICLE_ID = 0L;

		private final CheckInOutRepository checkInOutRepository;
		private final VehicleRepository vehicleRepository;
		private final ParkingRepository parkingRepository;

		public CheckInOutServiceImpl(CheckInOutRepository checkInOutRepository, VehicleRepository vehicleRepository, ParkingRepository parkingRepository) {
				this.checkInOutRepository = checkInOutRepository;
				this.vehicleRepository = vehicleRepository;
				this.parkingRepository = parkingRepository;
		}

		@Transactional(readOnly = true)
		public List<CheckInOut> findAll() {
				return this.checkInOutRepository.findAll();
		}

		@Transactional(readOnly = true)
		public CheckInOut findById(Long id) {
				return this.checkInOutRepository.findById(id).orElseThrow(NotFoundException::new);
		}

		@Transactional
		public CheckInOut create(CheckInOut checkInOut) {
				new ValidateFields(checkInOut.getParking(), "field parking is mandatory");
				new ValidateFields(checkInOut.getVehicle(), "field vehicle is mandatory");
				new ValidateFields(checkInOut.getDateHourCheckIn(), "field dateHourCheckIn is mandatory");
				new ValidateFields(checkInOut.isFinished(), "field finished is mandatory");
				if (checkInOut.isFinished())
						new ValidateFields(checkInOut.getDateHourCheckOut(), "field dateHourCheckOut is mandatory");
				new ValidateFields(checkInOut.getAmount(), "field amount is mandatory");

				Parking parking = parkingRepository.findById(checkInOut.getParking().getId())
								.orElseThrow(() -> new RuntimeException("parking not found: " + checkInOut.getParking().getId()));
				Vehicle vehicle = vehicleRepository.findById(checkInOut.getVehicle().getId())
								.orElseThrow(() -> new RuntimeException("vehicle type not found: " + checkInOut.getVehicle().getId()));

				checkInOut.setParking(parking);
				checkInOut.setVehicle(vehicle);

				this.validateChangeableId(checkInOut.getId(), "created");
				return this.checkInOutRepository.save(checkInOut);
		}

		@Transactional
		public CheckInOut update(Long id, CheckInOut checkInOut) {
				new ValidateFields(checkInOut.getParking(), "field parking is mandatory");
				new ValidateFields(checkInOut.getVehicle(), "field vehicle is mandatory");
				new ValidateFields(checkInOut.getDateHourCheckIn(), "field dateHourCheckIn is mandatory");
				new ValidateFields(checkInOut.isFinished(), "field finished is mandatory");
				new ValidateFields(checkInOut.getAmount(), "field amount is mandatory");
				if (checkInOut.isFinished()){
						new ValidateFields(checkInOut.getDateHourCheckOut(), "field dateHourCheckOut is mandatory");
						if (checkInOut.getDateHourCheckOut().isBefore(checkInOut.getDateHourCheckIn()))
							throw new BadRequestException("date hour check out invalid");
						if (Objects.equals(checkInOut.getAmount(), BigDecimal.valueOf(0)))
								throw new BadRequestException("amount not stated");
				}
				else{
						//only passes value if check out
						checkInOut.setDateHourCheckout(null);
				}
				Parking parking = parkingRepository.findById(checkInOut.getParking().getId())
								.orElseThrow(() -> new RuntimeException("parking not found: " + checkInOut.getParking().getId()));
				Vehicle vehicle = vehicleRepository.findById(checkInOut.getVehicle().getId())
								.orElseThrow(() -> new RuntimeException("vehicle type not found: " + checkInOut.getVehicle().getId()));

				checkInOut.setParking(parking);
				checkInOut.setVehicle(vehicle);

				this.validateChangeableId(id, "updated");
				var checkInOutUpdated = this.findById(id);
				if (!checkInOutUpdated.getId().equals(checkInOut.getId())) {
						throw new BadRequestException("update IDs must be the same.");
				}
				return this.checkInOutRepository.save(checkInOut);
		}

		@Override
		public void delete(Long id) {
				this.validateChangeableId(id, "deleted");
				var checkInOutDeleted = this.findById(id);
				checkInOutRepository.delete(checkInOutDeleted);
		}

		private void validateChangeableId(Long id, String operation) throws BusinessException {
				if (UNCHANGEABLE_VEHICLE_ID.equals(id)) {
						throw new BusinessException("check in %d can not be %s.".formatted(UNCHANGEABLE_VEHICLE_ID, operation));
				}
		}
}
