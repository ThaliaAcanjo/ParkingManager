package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.VeiculoDto;
import thalia.ParkingManager.repository.VeiculoRepository;
import thalia.ParkingManager.service.VeiculoService;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/veiculos")
@Tag(name="Veiculos controllers", description = "Restful API")
public record VeiculoController(VeiculoService veiculoService) {

		@PostMapping
		@Operation(summary = "Create new veículo", description = "Criação novo veículo e retorna o corpo do novo veículo")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "201", description = "veículo criado com sucesso"),
						@ApiResponse(responseCode = "422", description = "Dados de veículo fornecidos inválidos")
		})
		public ResponseEntity<VeiculoDto> create(@RequestBody VeiculoDto veiculoDto){
				var userCreated =  veiculoService.create(veiculoDto.toModel());
				URI location = ServletUriComponentsBuilder.fromCurrentRequest()
								.path("/{id}")
								.buildAndExpand(userCreated.getId())
								.toUri();
				return ResponseEntity.created(location).body(new VeiculoDto(userCreated));
		}
}
