package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thalia.ParkingManager.controller.dtos.ParkingSpotDto;
import thalia.ParkingManager.model.ParkingSpot;
import thalia.ParkingManager.service.ParkingSpotService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/parkingSpot")
@Tag(name="Parking spot controllers", description = "Restful API")
public record ParkingSpotController (ParkingSpotService parkingSpotService){

		@GetMapping("/{parkingId}")
		@Operation(summary = "Get all parking spot", description = "Retrieve a list of all registered parking spot")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "parking spot not found")
		})
		public ResponseEntity<List<ParkingSpotDto>> findByParking(@PathVariable Long parkingId){
				var parkingSpots = parkingSpotService.findByParking(parkingId);
				var parkingSpotDto = parkingSpots.stream()
								.map(parkingSpot -> new ParkingSpotDto(
														parkingSpot.getId(),
														parkingSpot.getSpotNumber(),
														parkingSpot.getVehicleType(),
												    parkingSpot.isOccupied())
				).collect(Collectors.toList());
				return ResponseEntity.ok(parkingSpotDto);
		}

		@GetMapping("/{parkingId}/{vehicleTypeId}")
		@Operation(summary = "Get parking spot by ID's", description = "Retrieve parking spot by its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "parking spot not found")
		})
		public ResponseEntity<List<ParkingSpotDto>> findByParkingAndVehicleType(@PathVariable Long parkingId, @PathVariable Long vehicleTypeId){
				var parkingSpots = parkingSpotService.findByParkingAndVehicleType(parkingId, vehicleTypeId);
				var parkingSpotDto = parkingSpots.stream()
								.map(parkingSpot -> new ParkingSpotDto(
												parkingSpot.getId(),
												parkingSpot.getSpotNumber(),
												parkingSpot.isOccupied())
								).collect(Collectors.toList());
				return ResponseEntity.ok(parkingSpotDto);
		}

		@GetMapping("/{parkingId}/{vehicleTypeId}/{spaceNumber}")
		@Operation(summary = "Get parking spot by ID's", description = "Retrieve parking spot by its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "parking spot not found")
		})
		public ResponseEntity<ParkingSpotDto> findParkingSpot(@RequestParam("parkingId") Long parkingId,
																													@RequestParam("vehicleTypeId") Long vehicleTypeId,
																													@RequestParam("numberSpace") String spotNumber){
				ParkingSpot parkingSpot = parkingSpotService.findByParkingSpot(parkingId, vehicleTypeId, spotNumber);
				ParkingSpotDto parkingSpotDto = new ParkingSpotDto(
									parkingSpot.getId(),
									parkingSpot.getSpotNumber(),
									parkingSpot.isOccupied());
				return ResponseEntity.ok(parkingSpotDto);
		}

		@PutMapping("/{parkingId}/{vehicleTypeId}/{spaceNumber}")
		@Operation(summary = "Update parking spot", description = "Update data of an existing parking spot based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "updated successfully"),
						@ApiResponse(responseCode = "404", description = "not found"),
						@ApiResponse(responseCode = "422", description = "Invalid provided data")
		})
		public ResponseEntity<ParkingSpotDto> update(@RequestParam("parkingId") Long parkingId,
																						 		 @RequestParam("vehicleTypeId") Long vehicleTypeId,
																						 		 @RequestParam("numberSpace") String spotNumber,
																								 @RequestBody ParkingSpot parkingSpot){
				var parkingSpotFind = parkingSpotService.findByParkingSpot(parkingId, vehicleTypeId, spotNumber);
				if (parkingSpotFind != null){
						var parkingSpotUpdated = parkingSpotService.update(parkingSpotFind.getId(), parkingSpot);
						return ResponseEntity.ok(new ParkingSpotDto(parkingSpot.getId(),
										parkingSpot.getSpotNumber(),
										parkingSpot.getVehicleType(),
										parkingSpot.isOccupied()));
				}
				else {
						return ResponseEntity.noContent().build();
				}
		}
}
