package thalia.ParkingManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.model.*;
import thalia.ParkingManager.repository.ParkingRepository;
import thalia.ParkingManager.repository.ParkingSpotRepository;
import thalia.ParkingManager.repository.VehicleTypeRepository;
import thalia.ParkingManager.service.ParkingSpotService;
import thalia.ParkingManager.service.exception.BusinessException;
import thalia.ParkingManager.service.exception.ValidateFields;

import java.util.List;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService{
		private static final Long UNCHANGEABLE_VEHICLE_TYPE_ID = 0L;
		@Autowired
		private final ParkingSpotRepository parkingSpotRepository;
		@Autowired
		private final ParkingRepository parkingRepository;
		@Autowired
		private final VehicleTypeRepository vehicleTypeRepository;

		public ParkingSpotServiceImpl(ParkingSpotRepository parkingSpotRepository, ParkingRepository parkingRepository, VehicleTypeRepository vehicleTypeRepository) {
				this.parkingSpotRepository = parkingSpotRepository;
				this.parkingRepository = parkingRepository;
				this.vehicleTypeRepository = vehicleTypeRepository;
		}

		@Transactional(readOnly = true)
		public List<ParkingSpot> findAll() {
				return this.parkingSpotRepository.findAll();
		}

		@Transactional(readOnly = true)
		public ParkingSpot findByParkingSpot(Long parkingId, Long vehicleTypeId, String spotNumber) {
				Parking parking = new Parking();
				parking.setId(parkingId);

				VehicleType vehicleType = new VehicleType();
				vehicleType.setId(vehicleTypeId);


				return this.parkingSpotRepository.findByParkingAndVehicleTypeAndSpotNumber(parking, vehicleType, spotNumber);
		}

		@Transactional(readOnly = true)
		public List<ParkingSpot> findByParking(Long parkingId) {
				Parking parking = this.parkingRepository.findById(parkingId).orElseThrow(() -> new BadRequestException("parking not found"));
				return this.parkingSpotRepository.findByParking(parking);
		}

		@Transactional(readOnly = true)
		public List<ParkingSpot> findByParkingAndVehicleType(Long parkingId, Long vehicleTypeId) {
				Parking parking = this.parkingRepository.findById(parkingId).orElseThrow(() -> new BadRequestException("parking not found"));
				VehicleType vehicleType = this.vehicleTypeRepository.findById(vehicleTypeId).orElseThrow(() -> new BadRequestException("vehicle type not found"));
				return this.parkingSpotRepository.findByParkingAndVehicleType(parking, vehicleType);
		}

		@Transactional
		public ParkingSpot create(ParkingSpot entity) {
				new ValidateFields(entity.getParking(), "field parking is mandatory");
				new ValidateFields(entity.getVehicleType(), "field vehicle type is mandatory");
				new ValidateFields(entity.getSpotNumber(), "field spot number is mandatory");
				new ValidateFields(entity.isOccupied(), "field occupied is mandatory");
				var parkingId = entity.getParking().getId();
				var vehicleTypeId =  entity.getVehicleType().getId();
				if (parkingId == 0)
						throw new BadRequestException("field parking is mandatory");
				if (vehicleTypeId == 0)
						throw new BadRequestException("field vehicle type is mandatory");
				if (this.findByParkingSpot(parkingId, vehicleTypeId, entity.getSpotNumber()) != null)
						throw new BadRequestException("space number already registered");

				parkingRepository.findById(parkingId)
								.orElseThrow(() -> new BadRequestException("parking not found: " + parkingId));
				vehicleTypeRepository.findById(vehicleTypeId)
								.orElseThrow(() -> new BadRequestException("vehicle type not found: " + vehicleTypeId));

				entity.setParking(entity.getParking());
				entity.setVehicleType(entity.getVehicleType());
				return this.parkingSpotRepository.save(entity);
		}

		@Transactional
		public ParkingSpot update(Long id, ParkingSpot entity) {
				new ValidateFields(entity.getParking(), "field parking is mandatory");
				new ValidateFields(entity.getVehicleType(), "field vehicle type is mandatory");
				new ValidateFields(entity.getSpotNumber(), "field spot number is mandatory");
				new ValidateFields(entity.isOccupied(), "field occupied is mandatory");
				var parkingId = entity.getParking().getId();
				var vehicleTypeId =  entity.getVehicleType().getId();
				if (parkingId == 0)
						throw new BadRequestException("field parking is mandatory");
				if (vehicleTypeId == 0)
						throw new BadRequestException("field vehicle type is mandatory");
				if (!entity.getId().equals(id))
						throw new BadRequestException("update IDs must be the same.");

				var parking = parkingRepository.findById(parkingId)
								.orElseThrow(() -> new BadRequestException("parking not found: " + parkingId));
				var vehicleType =vehicleTypeRepository.findById(vehicleTypeId)
								.orElseThrow(() -> new BadRequestException("vehicle type not found: " + vehicleTypeId));

				entity.setParking(parking);
				entity.setVehicleType(vehicleType);
				return this.parkingSpotRepository.save(entity);
		}

		@Override
		public void delete(Long id) {
			this.parkingSpotRepository.deleteById(id);
		}

		@Override
		public ParkingSpot findById(Long aLong) {
				return null;
		}

}
