package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.VehicleDto;
import thalia.ParkingManager.model.Vehicle;
import thalia.ParkingManager.service.VehicleService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/vehicles")
@Tag(name="Vehicle controllers", description = "Restful API")
public record VehicleController(VehicleService vehicleService) {
		@GetMapping
		@Operation(summary = "Get all vehicles", description = "Retrieve a list of all registered vehicles")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully")
		})
		public ResponseEntity<List<VehicleDto>> findAll(){
				var vehicles = vehicleService.findAll();
				var vehiclesDto = vehicles.stream().map(VehicleDto::new).collect(Collectors.toList());
				return ResponseEntity.ok(vehiclesDto);
		}

		@GetMapping("/{id}")
		@Operation(summary = "Get vehicle by ID", description = "Retrieve vehicle by its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "Vehicle not found")
		})
		public ResponseEntity<VehicleDto> findByID(@PathVariable Long id){
				var vehicle = vehicleService.findById(id);
				return ResponseEntity.ok(new VehicleDto(vehicle));
		}

		@GetMapping("/license-plate/{licensePlate}")
		@Operation(summary = "Get vehicle by license plate", description = "Retrieve vehicle by license plate")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "license plate not found")
		})
		public ResponseEntity<VehicleDto> findByLicensePlate(@PathVariable String licensePlate) {
				Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);

				if (vehicle != null) {
						VehicleDto vehicleDto = new VehicleDto(vehicle);
						return ResponseEntity.ok(vehicleDto);
				} else {
						return ResponseEntity.notFound().build();
				}
		}

		@PostMapping
		@Operation(summary = "Create new vehicle", description = "Create a new vehicle and return the body of the new vehicle")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "201", description = "Vehicle created successfully"),
						@ApiResponse(responseCode = "422", description = "Invalid provided vehicle data")
		})
		public ResponseEntity<VehicleDto> create(@RequestBody VehicleDto vehicleDto){
				var vehicleCreated =  vehicleService.create(vehicleDto.toModel());
				URI location = ServletUriComponentsBuilder.fromCurrentRequest()
								.path("/{id}")
								.buildAndExpand(vehicleCreated.getId())
								.toUri();
				return ResponseEntity.created(location).body(new VehicleDto(vehicleCreated));
		}

		@PutMapping("/{id}")
		@Operation(summary = "Update vehicle", description = "Update data of an existing vehicle based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
						@ApiResponse(responseCode = "404", description = "Vehicle not found"),
						@ApiResponse(responseCode = "422", description = "Invalid provided vehicle data")
		})
		public ResponseEntity<VehicleDto> update(@PathVariable Long id, @RequestBody VehicleDto vehicleDto){
				var vehicle = vehicleService.update(id, vehicleDto.toModel());
				return ResponseEntity.ok(new VehicleDto(vehicle));
		}

		@DeleteMapping("/{id}")
		@Operation(summary = "Delete vehicle", description = "Delete an existing vehicle based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
						@ApiResponse(responseCode = "404", description = "Vehicle not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id){
				vehicleService.delete(id);
				return ResponseEntity.noContent().build();
		}
}
