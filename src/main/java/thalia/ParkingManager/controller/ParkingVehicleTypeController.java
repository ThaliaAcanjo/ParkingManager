package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeDTO;
import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeFindDTO;
import thalia.ParkingManager.model.ParkingVehicleType;
import thalia.ParkingManager.model.Parking;
import thalia.ParkingManager.model.VehicleType;
import thalia.ParkingManager.repository.ParkingRepository;
import thalia.ParkingManager.repository.ParkingVehicleTypeRepository;
import thalia.ParkingManager.repository.VehicleTypeRepository;
import thalia.ParkingManager.service.ParkingVehicleTypeService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/parkingVehicleType")
@Tag(name="Parking vehicle type controllers", description = "Restful API")
public class ParkingVehicleTypeController {
		@Autowired
		private final ParkingVehicleTypeService parkingVehicleTypeService;
		@Autowired
		private final ParkingVehicleTypeRepository parkingVehicleTypeRepository;
		@Autowired
		private final ParkingRepository parkingRepository;
		@Autowired
		private final VehicleTypeRepository vehicleTypeRepository;

		public ParkingVehicleTypeController(ParkingVehicleTypeService parkingVehicleTypeService, ParkingVehicleTypeRepository parkingVehicleTypeRepository, ParkingRepository parkingRepository, VehicleTypeRepository vehicleTypeRepository) {
				this.parkingVehicleTypeService = parkingVehicleTypeService;
				this.parkingVehicleTypeRepository = parkingVehicleTypeRepository;
				this.parkingRepository = parkingRepository;
				this.vehicleTypeRepository = vehicleTypeRepository;
		}

		@GetMapping("/{id}")
		@Operation(summary = "Get parked vehicle type by ID", description = "Retrieve parked vehicle type by its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "not found")
		})
		public ResponseEntity<ParkingVehicleTypeFindDTO> findById(@PathVariable Long id){
				try {

					var parkingVehicleTypeDto = parkingVehicleTypeService.getById(id);
					return ResponseEntity.ok(parkingVehicleTypeDto);

				} catch (EntityNotFoundException ex) {
						return ResponseEntity.notFound().build();
				}
		}

		@GetMapping("/parking/{id}")
		@Operation(summary = "Get parked vehicle type by parking", description = "Retrieve parked vehicle type by parking")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "not found")
		})
		public ResponseEntity<List<ParkingVehicleTypeFindDTO>> findByVehicleTypes(@PathVariable Long id){

				List<ParkingVehicleTypeFindDTO> listDto = parkingVehicleTypeService.findByParkingId(id);
				return ResponseEntity.ok(listDto);
		}

		@PostMapping
		@Operation(summary = "Create parked vehicle type", description = "Create a new parked vehicle type")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Parked vehicle type created successfully"),
						@ApiResponse(responseCode = "422", description = "Invalid provided parked vehicle type data")
		})
		public ResponseEntity<ParkingVehicleTypeDTO> create(@RequestBody ParkingVehicleTypeDTO parkingVehicleTypeDto){
				var dtoCreated = parkingVehicleTypeService.createParkedVehicleType(parkingVehicleTypeDto);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().
								path("/{id}").
								buildAndExpand(dtoCreated.getId()).
								toUri();
				return ResponseEntity.created(location).body(dtoCreated);
		}

		@PutMapping("/{id}")
		@Operation(summary = "Update parked vehicle type", description = "Update data of an existing parked vehicle type based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "parked vehicle type updated successfully"),
						@ApiResponse(responseCode = "404", description = "parked vehicle type not found"),
						@ApiResponse(responseCode = "422", description = "Invalid provided parked vehicle type data")
		})
		public ResponseEntity<ParkingVehicleTypeDTO> update(@PathVariable Long id, @RequestBody ParkingVehicleTypeDTO parkingVehicleTypeDto){
				var parkingVehicleTypeUpdate = parkingVehicleTypeService.updateById(id, parkingVehicleTypeDto);
				return ResponseEntity.ok(parkingVehicleTypeUpdate);
		}


		@DeleteMapping("/{id}")
		@Operation(summary = "Delete parked vehicle type", description = "Delete an existing parked vehicle type based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "parked vehicle type deleted successfully"),
						@ApiResponse(responseCode = "404", description = "parked vehicle type not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id){
				parkingVehicleTypeService.delete(id);
				return ResponseEntity.noContent().build();
		}
}
