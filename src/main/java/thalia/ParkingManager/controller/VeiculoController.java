package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.VeiculoDto;
import thalia.ParkingManager.service.VeiculoService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/veiculos")
@Tag(name="Veiculos controllers", description = "Restful API")
public record VeiculoController(VeiculoService veiculoService) {

		@GetMapping
		@Operation(summary = "Obter todos veículos", description = "Recuperar uma lista de todos os veículos registrados")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operação concluída com sucesso")
		})
		public ResponseEntity<List<VeiculoDto>> findAll(){
				var veiculos = veiculoService.findAll();
				var veiculosDto = veiculos.stream().map(VeiculoDto::new).collect(Collectors.toList());
				return ResponseEntity.ok(veiculosDto);
		}

		@GetMapping("/{id}")
		@Operation(summary = "Obter veículo por ID", description = "Recuperar veículo")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operação concluída com sucesso"),
						@ApiResponse(responseCode = "404", description = "veículo not found")
		})
		public ResponseEntity<VeiculoDto> findByID(@PathVariable Long id){
				var veiculo = veiculoService.findById(id);
				return ResponseEntity.ok(new VeiculoDto(veiculo));
		}

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

		@PutMapping("/{id}")
		@Operation(summary = "Alteração de veículo", description = "Atualizar os dados de um veículo existente com base em seu ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "veículo alterado com sucesso"),
						@ApiResponse(responseCode = "404", description = "veículo not found"),
						@ApiResponse(responseCode = "422", description = "Dados de veículo fornecidos inválidos")
		})
		public ResponseEntity<VeiculoDto> update(@PathVariable Long id, @RequestBody VeiculoDto veiculoDto){
				var veiculo = veiculoService.update(id, veiculoDto.toModel());
				return ResponseEntity.ok(new VeiculoDto(veiculo));
		}

		@DeleteMapping("/{id}")
		@Operation(summary = "Delete a veiculo", description = "Delete an existing veiculo based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "veiculo deletado com sucesso"),
						@ApiResponse(responseCode = "404", description = "veiculo not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id){
				veiculoService.delete(id);
				return ResponseEntity.noContent().build();
		}
}
