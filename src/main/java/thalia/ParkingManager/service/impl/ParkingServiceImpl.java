package thalia.ParkingManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.controller.dtos.Parking.ParkingDTO;
import thalia.ParkingManager.controller.dtos.Parking.ParkingFindDTO;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeFindDTO;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.exceptionhandler.NotFoundException;
import thalia.ParkingManager.model.Parking;
import thalia.ParkingManager.model.ParkingVehicleType;
import thalia.ParkingManager.repository.ParkingRepository;
import thalia.ParkingManager.repository.ParkingVehicleTypeRepository;
import thalia.ParkingManager.repository.VehicleTypeRepository;
import thalia.ParkingManager.service.ParkingService;
import thalia.ParkingManager.service.ParkingVehicleTypeService;
import thalia.ParkingManager.service.exception.BusinessException;
import thalia.ParkingManager.service.exception.ValidateFields;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingServiceImpl implements ParkingService {
		private static final Long UNCHANGEABLE_VEHICLE_ID = 0L;
		private final ParkingRepository parkingRepository;
		@Autowired
		private final ParkingVehicleTypeRepository parkingVehicleTypeRepository;
		@Autowired
		private final ParkingVehicleTypeService parkingVehicleTypeService;
		@Autowired
		private final VehicleTypeRepository vehicleTypeRepository;

		public ParkingServiceImpl(ParkingRepository parkingRepository, ParkingVehicleTypeRepository parkingVehicleTypeRepository, ParkingVehicleTypeService parkingVehicleTypeService, VehicleTypeRepository vehicleTypeRepository){
				this.parkingRepository = parkingRepository;
				this.parkingVehicleTypeRepository = parkingVehicleTypeRepository;
				this.parkingVehicleTypeService = parkingVehicleTypeService;
				this.vehicleTypeRepository = vehicleTypeRepository;
		}
		@Transactional(readOnly = true)
		public List<ParkingFindDTO> getAll() {
				return this.parkingRepository.findAll().stream().map(this::toFindDTO).collect(Collectors.toList());
		}
		@Transactional(readOnly = true)
		public ParkingFindDTO getById(Long id){
				return toFindDTO(this.parkingRepository.findById(id).orElseThrow(NotFoundException::new));
		}

		@Transactional(readOnly = true)
		public ParkingFindDTO findByCnpj(String cnpj) {

				var parking = this.parkingRepository.findByCnpj(cnpj);
				if (parking != null) {
						return toFindDTO(parking);
				}else {
						return null;
				}
		}

		@Override
		public ParkingDTO createParking(ParkingDTO parkingDTO) {
				new ValidateFields(parkingDTO.getName(), "field name is mandatory");
				new ValidateFields(parkingDTO.getCnpj(), "field CNPJ is mandatory");
				if (!ValidateFields.validateCNPJ(parkingDTO.getCnpj()))
						throw new BadRequestException("field CNPJ invalid");
				new ValidateFields(parkingDTO.getAddress(), "field address is mandatory");
				new ValidateFields(parkingDTO.getPricePerHour(), "field price per hour is mandatory");
				if (this.findByCnpj(parkingDTO.getCnpj()) != null)
						throw new BadRequestException("cnpj already registered");
				this.validateChangeableId(parkingDTO.getId(), "created");

				Parking parking = new Parking();
				parking.setName(parkingDTO.getName());
				parking.setCnpj(parkingDTO.getCnpj());
				parking.setAddress(parkingDTO.getAddress());
				parking.setPricePerHour(parkingDTO.getPricePerHour());
				return toDTO(this.parkingRepository.save(parking));
		}

		@Transactional
		public ParkingDTO updateById(Long id, ParkingDTO parkingDTO){
				new ValidateFields(parkingDTO.getName(), "field name is mandatory");
				new ValidateFields(parkingDTO.getCnpj(), "field CNPJ is mandatory");
				if (!ValidateFields.validateCNPJ(parkingDTO.getCnpj()))
						throw new BadRequestException("field CNPJ invalid");
				new ValidateFields(parkingDTO.getAddress(), "field address is mandatory");
				new ValidateFields(parkingDTO.getPricePerHour(), "field price per hour is mandatory");
				this.validateChangeableId(id, "updated");

				Parking parkingUpdate = this.findById(id);
				Parking parkingCnpj = parkingRepository.findByCnpj(parkingDTO.getCnpj());

				if ( (!parkingUpdate.getId().equals(parkingDTO.getId())) ||
						 (!parkingUpdate.getCnpj().equals(parkingDTO.getCnpj())))		{
						throw new BadRequestException("update IDs must be the same.");
				}

				if ((parkingCnpj != null) && (!parkingCnpj.getId().equals(parkingDTO.getId()))){
						throw new BadRequestException("update IDs must be the same.");
				}

				Parking entity = new Parking();
				entity.setId(parkingDTO.getId());
				entity.setName(parkingDTO.getName());
				entity.setCnpj(parkingDTO.getCnpj());
				entity.setAddress(parkingDTO.getAddress());
				entity.setPricePerHour(parkingDTO.getPricePerHour());
				entity.setParkingVehicleTypes(parkingUpdate.getParkingVehicleTypes());

				return toDTO(this.parkingRepository.save(entity));
		}

		@Override
		public void delete(Long id) {
			this.validateChangeableId(id, "deleted");
			parkingRepository.deleteById(id);
		}

		private void validateChangeableId(Long id, String operation) throws BusinessException {
				if (UNCHANGEABLE_VEHICLE_ID.equals(id)) {
						throw new BusinessException("Parking %d can not be %s.".formatted(UNCHANGEABLE_VEHICLE_ID, operation));
				}
		}
		private ParkingDTO toDTO(Parking entity) {
				return new ParkingDTO(
								entity.getId(),
								entity.getName(),
								entity.getCnpj(),
								entity.getAddress(),
								entity.getPricePerHour()
				);
		}
		private ParkingFindDTO toFindDTO(Parking entity) {
				return new ParkingFindDTO(
								entity.getId(),
								entity.getName(),
								entity.getCnpj(),
								entity.getAddress(),
								entity.getPricePerHour(),
								entity.getParkingVehicleTypes().stream().map(this::toVehicleTypesFindDTO).collect(Collectors.toList())
				);
		}
		private ParkingVehicleTypeFindDTO toVehicleTypesFindDTO(ParkingVehicleType entity) {
				return new ParkingVehicleTypeFindDTO(
								entity.getId(),
								entity.getParking().getId(),
								entity.getVehicleType(),
								entity.getNumberOfSpots()
				);
		}

		@Override
		public List<Parking> findAll() {
				return null;
		}

		@Override
		public Parking findById(Long aLong) {
				return this.parkingRepository.findById(aLong).orElseThrow(NotFoundException::new);
		}

		@Transactional
		public Parking create(Parking parking) {
				return  null;
		}

		@Override
		public Parking update(Long aLong, Parking entity) {
				return null;
		}
}
