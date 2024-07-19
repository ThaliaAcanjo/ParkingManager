package thalia.ParkingManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeDTO;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeFindDTO;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.exceptionhandler.NotFoundException;
import thalia.ParkingManager.model.*;
import thalia.ParkingManager.repository.ParkingRepository;
import thalia.ParkingManager.repository.ParkingSpotRepository;
import thalia.ParkingManager.repository.ParkingVehicleTypeRepository;
import thalia.ParkingManager.repository.VehicleTypeRepository;
import thalia.ParkingManager.service.ParkingVehicleTypeService;
import thalia.ParkingManager.service.exception.BusinessException;
import thalia.ParkingManager.service.exception.ValidateFields;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingVehicleTypeServiceImpl implements ParkingVehicleTypeService {
		private static final Long UNCHANGEABLE_PARKING_VEHICLE_TYPE_ID = 0L;
		@Autowired
		private final ParkingVehicleTypeRepository parkingVehicleTypeRepository;
		private final ParkingRepository parkingRepository;
		private final VehicleTypeRepository vehicleTypeRepository;
		@Autowired
		private final ParkingSpotRepository parkingSpotRepository;

		public ParkingVehicleTypeServiceImpl(ParkingVehicleTypeRepository parkingVehicleTypeRepository, ParkingRepository parkingRepository, VehicleTypeRepository vehicleTypeRepository, ParkingSpotRepository parkingSpotRepository) {
				this.parkingVehicleTypeRepository = parkingVehicleTypeRepository;
				this.parkingRepository = parkingRepository;
				this.vehicleTypeRepository = vehicleTypeRepository;
				this.parkingSpotRepository = parkingSpotRepository;
		}

		@Transactional(readOnly = true)
		public List<ParkingVehicleTypeDTO> getAll() {
				return this.parkingVehicleTypeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
		}
		@Transactional(readOnly = true)
		public ParkingVehicleTypeFindDTO getById(Long id) {
				return toFindDTO(this.parkingVehicleTypeRepository.findById(id).orElseThrow(NotFoundException::new));
		}
		@Transactional(readOnly = true)
		public List<ParkingVehicleTypeFindDTO> findByParkingId(Long id) {
				return this.parkingVehicleTypeRepository.findByParkingId(id).stream().map(this::toFindDTO).collect(Collectors.toList());
		}

		@Transactional
		public ParkingVehicleTypeDTO createParkedVehicleType(ParkingVehicleTypeDTO dto) {
				new ValidateFields(dto.getVehicleTypeId(), "Field vehicle type is mandatory");
				new ValidateFields(dto.getParkingId(), "Field parking type is mandatory");
				/*new ValidateFields(dto.getNumberOfSpots(), "Field number of spots is mandatory");
				if (dto.getNumberOfSpots() <= 0)
						throw new BadRequestException("Field number of spots is mandatory");*/
				this.validateChangeableId(dto.getId(), "created");

				Parking parking = parkingRepository.findById(dto.getParkingId())
								.orElseThrow(() -> new BadRequestException("Parking not found"));

				VehicleType vehicleType = vehicleTypeRepository.findById(dto.getVehicleTypeId())
								.orElseThrow(() -> new BadRequestException("VehicleType not found"));

				dto.setNumberOfSpots(2);
				ParkingVehicleType entity = new ParkingVehicleType();
				entity.setParking(parking);
				entity.setVehicleType(vehicleType);
				entity.setNumberOfSpots(dto.getNumberOfSpots());

				var numberOfSpots = dto.getNumberOfSpots();
				if (numberOfSpots != null && numberOfSpots > 0) {
						createParkingSpots(entity, 1, numberOfSpots);
				} else {
						throw new BadRequestException("Number of spots must be greater than zero");
				}
				return toDTO(parkingVehicleTypeRepository.save(entity));
		}

		@Transactional
		public ParkingVehicleTypeDTO updateById(Long id, ParkingVehicleTypeDTO dto) {
				new ValidateFields(dto.getId(), "Field id is mandatory");
				new ValidateFields(dto.getVehicleTypeId(), "Field vehicle type is mandatory");
				new ValidateFields(dto.getParkingId(), "Field parking type is mandatory");
				new ValidateFields(dto.getNumberOfSpots(), "Field number of spots  is mandatory");
				if (dto.getNumberOfSpots() <= 0)
						throw new BadRequestException("Field number of spots  is mandatory");
				this.validateChangeableId(dto.getId(), "created");

				Parking parking = parkingRepository.findById(dto.getParkingId())
								.orElseThrow(() -> new BadRequestException("Parking not found"));

				VehicleType vehicleType = vehicleTypeRepository.findById(dto.getVehicleTypeId())
								.orElseThrow(() -> new BadRequestException("VehicleType not found"));

				var parkingVehicleTypeOld = this.getById(id);
				if (!parkingVehicleTypeOld.getId().equals(parkingVehicleTypeOld.getId())){
						throw new BadRequestException("update IDs must be the same.");
				}

				ParkingVehicleType entity = new ParkingVehicleType();
				entity.setParking(parking);
				entity.setVehicleType(vehicleType);
				entity.setNumberOfSpots(dto.getNumberOfSpots());

				var newNumberOfSpots = entity.getNumberOfSpots();
				var oldNumberOfSpots = parkingVehicleTypeOld.getNumberOfSpots();
				if (newNumberOfSpots > oldNumberOfSpots) {
						createParkingSpots(entity, oldNumberOfSpots +1, newNumberOfSpots);
				}else{
						var exceptionString = updateParkingSpots(parkingVehicleTypeOld.getNumberOfSpots(), entity);
						if (!exceptionString.isEmpty()) {
								throw new BadRequestException(exceptionString);
						}
				}
				return toDTO(parkingVehicleTypeRepository.save(entity));
		}

		@Transactional
		public void delete(Long id) {
				this.validateChangeableId(id, "deleted");
				var parkingVehicleType = this.getById(id);
				parkingVehicleTypeRepository.deleteById(parkingVehicleType.getId());
		}
		public void createParkingSpots(ParkingVehicleType entity, Integer  numberOfSpotsOld, Integer numberOfSpots) {
				List<ParkingSpot> parkingSpots = new ArrayList<>();
				for (int i = numberOfSpotsOld; i <= numberOfSpots; i++) {
						String spotNumber = String.format("%03d", i);

						ParkingSpot parkingSpot = new ParkingSpot();
						parkingSpot.setSpotNumber(spotNumber);
						parkingSpot.setOccupied(false);
						parkingSpot.setParking(entity.getParking());
						parkingSpot.setVehicleType(entity.getVehicleType());

						parkingSpots.add(parkingSpot);
				}
				parkingSpotRepository.saveAll(parkingSpots);
		}
		private String updateParkingSpots(int oldNumberOfSpots, ParkingVehicleType entity) {
				var newNumberOfSpots = entity.getNumberOfSpots();
				List<ParkingSpot> parkingSpots = new ArrayList<>();
				Parking parking = this.parkingRepository.findById(entity.getParking().getId()).get();
				VehicleType vehicleType = this.vehicleTypeRepository.findById(entity.getVehicleType().getId()).get();
				for (int i = newNumberOfSpots + 1; i <= oldNumberOfSpots; i++) {
						String spotNumber = String.format("%03d", i);

						ParkingSpot parkingSpot = this.parkingSpotRepository.findByParkingAndVehicleTypeAndSpotNumber(parking, vehicleType, spotNumber);
						if (parkingSpot.isOccupied())
								return "can not be deleted parking spots, parking spot %s is occupied".formatted(spotNumber);

						parkingSpots.add(parkingSpot);
				}
				this.parkingSpotRepository.deleteAll(parkingSpots);

				return "";
		}
		private void validateChangeableId(Long id, String operation) {
				if (UNCHANGEABLE_PARKING_VEHICLE_TYPE_ID.equals(id)) {
						throw new BusinessException("%d can not be %s.".formatted(UNCHANGEABLE_PARKING_VEHICLE_TYPE_ID, operation));
				}
		}
		private ParkingVehicleTypeDTO toDTO(ParkingVehicleType entity) {
				return new ParkingVehicleTypeDTO(
								entity.getId(),
								entity.getParking().getId(),
								entity.getVehicleType().getId(),
								entity.getVehicleType().getName(),
								entity.getNumberOfSpots()
				);
		}
		private ParkingVehicleTypeFindDTO toFindDTO(ParkingVehicleType entity) {
				return new ParkingVehicleTypeFindDTO(
								entity.getId(),
								entity.getParking().getId(),
								entity.getVehicleType(),
								entity.getNumberOfSpots()
				);
		}
		private ParkingVehicleType toEntity(ParkingVehicleTypeDTO dto) {
				ParkingVehicleType entity = new ParkingVehicleType();
				entity.setId(dto.getId());
				entity.setNumberOfSpots(dto.getNumberOfSpots());

				if (dto.getParkingId() != null) {
						Parking parking = parkingRepository.findById(dto.getParkingId())
										.orElseThrow(() -> new BadRequestException("Parking not found"));
						entity.setParking(parking);
				}

				if (dto.getVehicleTypeId() != null) {
						VehicleType vehicleType = vehicleTypeRepository.findById(dto.getVehicleTypeId())
										.orElseThrow(() -> new BadRequestException("VehicleType not found"));
						entity.setVehicleType(vehicleType);
				}
				return entity;
		}

		@Override
		public List<ParkingVehicleType> findAll() {
				return null;
		}

		@Override
		public ParkingVehicleType findById(Long aLong) {
				return null;
		}

		@Override
		public ParkingVehicleType create(ParkingVehicleType entity) {
				return null;
		}

		@Override
		public ParkingVehicleType update(Long aLong, ParkingVehicleType entity) {
				return null;
		}
}
