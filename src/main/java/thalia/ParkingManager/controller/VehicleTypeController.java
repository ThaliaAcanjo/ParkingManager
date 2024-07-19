package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thalia.ParkingManager.controller.dtos.VehicleTypeDTO;

import java.util.List;
import java.util.stream.Collectors;
import thalia.ParkingManager.service.VehicleTypeService;

@CrossOrigin
@RestController
@RequestMapping("/vehicleType")
@Tag(name = "Vehicle type controllers", description = "Restful API")
public record VehicleTypeController(VehicleTypeService vehicleTypeService) {

		@GetMapping
		@Operation(summary = "Get all vehicle types", description = "Retrieve a list of all registered vehicle type")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully")
		})
		public ResponseEntity<List<VehicleTypeDTO>> findAll() {
				var vehiclesType = vehicleTypeService.findAll();
				var vehiclesTypeDto = vehiclesType.stream().map(VehicleTypeDTO::new).collect(Collectors.toList());
				return ResponseEntity.ok(vehiclesTypeDto);
		}

		@GetMapping("/{id}")
		@Operation(summary = "Get vehicle type by ID", description = "Retrieve vehicle type by its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "Vehicle type not found")
		})
		public ResponseEntity<VehicleTypeDTO> findById(@PathVariable Long id) {
				var vehicleType = vehicleTypeService.findById(id);
				return ResponseEntity.ok(new VehicleTypeDTO(vehicleType));
		}
/*
		@PostMapping
		@Operation(summary = "Create new vehicle type", description = "Create a new vehicle type and return the body of the new vehicle")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "201", description = "Vehicle type created successfully"),
						@ApiResponse(responseCode = "422", description = "Invalid provided vehicle type data")
		})
		public ResponseEntity<VehicleTypeDto> create(@RequestBody VehicleTypeDto vehicleTypeDto) {
				var vehicleTypeCreated = vehicleTypeService.create(vehicleTypeDto.toModel());
				URI location = ServletUriComponentsBuilder.fromCurrentRequest()
								.path("/{id}")
								.buildAndExpand(vehicleTypeCreated.getId())
								.toUri();
				return ResponseEntity.created(location).body(new VehicleTypeDto(vehicleTypeCreated));
		}

		@PutMapping("/{id}")
		@Operation(summary = "Update vehicle type", description = "Update data of an existing vehicle type based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Vehicle type updated successfully"),
						@ApiResponse(responseCode = "404", description = "Vehicle type not found"),
						@ApiResponse(responseCode = "422", description = "Invalid provided vehicle type data")
		})
		public ResponseEntity<VehicleTypeDto> update(@PathVariable Long id, @RequestBody VehicleTypeDto vehicleTypeDto) {
				var vehicleType = vehicleTypeService.update(id, vehicleTypeDto.toModel());
				return ResponseEntity.ok(new VehicleTypeDto(vehicleType));
		}

		@DeleteMapping("/{id}")
		@Operation(summary = "Delete vehicle type", description = "Delete an existing vehicle type based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "Vehicle type deleted successfully"),
						@ApiResponse(responseCode = "404", description = "Vehicle type not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id) {
				vehicleTypeService.delete(id);
				return ResponseEntity.noContent().build();
		}*/
}
