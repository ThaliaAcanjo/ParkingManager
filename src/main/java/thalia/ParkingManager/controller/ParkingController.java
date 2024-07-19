package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.Parking.ParkingDTO;
import thalia.ParkingManager.controller.dtos.Parking.ParkingFindDTO;
import thalia.ParkingManager.model.Parking;
import thalia.ParkingManager.service.ParkingService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/parking")
@Tag(name="Parking's controllers", description = "Restful API")
public record ParkingController(ParkingService parkingService) {
		@GetMapping
		@Operation(summary = "Get all parking's", description = "Retrieve a list of all registered parking's")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully")
		})
		public ResponseEntity<List<ParkingFindDTO>> findAll(){
				var listParking = parkingService.getAll();
				return ResponseEntity.ok(listParking);
		}

		@GetMapping("/{id}")
		@Operation(summary = "Get parking by ID", description = "Retrieve parking by its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "parking not found")
		})
		public ResponseEntity<ParkingFindDTO> findById(@PathVariable Long id){
				var parking = parkingService.getById(id);
				return ResponseEntity.ok(parking);
		}

		@GetMapping("/cnpj/{cnpj}")
		@Operation(summary = "Get parking by CNPJ", description = "Retrieve parking by its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "parking not found")
		})
		public ResponseEntity<ParkingFindDTO> findByCnpj(@PathVariable String cnpj){
				var parking = parkingService.findByCnpj(cnpj);
				return ResponseEntity.ok(parking);
		}

		@PostMapping
		@Operation(summary = "Create parking", description = "Create a new parking")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "parking created successfully"),
						@ApiResponse(responseCode = "422", description = "Invalid provided parking data")
		})
		public ResponseEntity<ParkingDTO> create(@RequestBody ParkingDTO parking){
				var parkingCreated = parkingService.createParking(parking);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().
								path("/{id}").
								buildAndExpand(parkingCreated.getId()).
								toUri();
				return ResponseEntity.created(location).body(parkingCreated);
		}


		@PutMapping("/{id}")
		@Operation(summary = "Update parking", description = "Update data of an existing parking based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "parking updated successfully"),
						@ApiResponse(responseCode = "404", description = "parking not found"),
						@ApiResponse(responseCode = "422", description = "Invalid provided parking data")
		})
		public ResponseEntity<ParkingDTO> update(@PathVariable Long id, @RequestBody ParkingDTO parkingDto){
				var parking = parkingService.updateById(id, parkingDto);
				return ResponseEntity.ok(parking);
		}

		@DeleteMapping("/{id}")
		@Operation(summary = "Delete parking", description = "Delete an existing parking based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "parking deleted successfully"),
						@ApiResponse(responseCode = "404", description = "parking not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id){
				parkingService.delete(id);
				return ResponseEntity.noContent().build();
		}
}
