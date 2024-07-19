package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.CheckInOutDto;
import thalia.ParkingManager.service.CheckInOutService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/checkInOut")
@Tag(name="CheckInOut controllers", description = "Restful API")
public record CheckInOutController(CheckInOutService checkInOutService) {

		@GetMapping
		@Operation(summary = "Get all check in", description = "Retrieve a list of all registered parking lots")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully")
		})
		public ResponseEntity<List<CheckInOutDto>> findAll(){
				var checkInOuts = checkInOutService.findAll();
				var checkInOutDto = checkInOuts.stream().map(CheckInOutDto::new).collect(Collectors.toList());
				return ResponseEntity.ok(checkInOutDto);
		}

		@GetMapping("/{id}")
		@Operation(summary = "get check in by id", description = "retrieve a check in")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "check in not found")
		})
		public ResponseEntity<CheckInOutDto> findById(@PathVariable Long id){
				var checkInOut = checkInOutService.findById(id);
				var checkInOutDto = new CheckInOutDto(checkInOut);
				return ResponseEntity.ok(checkInOutDto);
		}

		@PostMapping
		@Operation(summary = "create check in")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "422", description = "Invalid input/output data provided")
		})
		public ResponseEntity<CheckInOutDto> create(@RequestBody CheckInOutDto checkInOutDto){
				var checkInOutCreated = checkInOutService.create(checkInOutDto.toModel());
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().
								path("/{id}").
								buildAndExpand(checkInOutCreated.getId()).
								toUri();
				return ResponseEntity.created(location).body(new CheckInOutDto(checkInOutCreated));
		}

		@PutMapping("/{id}")
		@Operation(summary = "update of check in", description = "Update data for an existing check in based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "check in not found"),
						@ApiResponse(responseCode = "422", description = "Invalid input/output data provided")
		})
		public ResponseEntity<CheckInOutDto> update(@PathVariable Long id, @RequestBody CheckInOutDto checkInOutDto){
				var checkInOut = checkInOutService.update(id, checkInOutDto.toModel());
				return ResponseEntity.ok(new CheckInOutDto(checkInOut));
		}

		@DeleteMapping("/{id}")
		@Operation(summary = "Delete a check in", description = "Delete an existing check in based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "Operation completed successfully"),
						@ApiResponse(responseCode = "404", description = "check in not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id){
				checkInOutService.delete(id);
				return ResponseEntity.noContent().build();
		}

}
