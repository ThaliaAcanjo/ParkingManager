package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.EstabelecimentoDto;
import thalia.ParkingManager.controller.dtos.VeiculoDto;
import thalia.ParkingManager.model.Estabelecimento;
import thalia.ParkingManager.service.EstabService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/estabelecimentos")
@Tag(name="Estabelecimentos controllers", description = "Restful API")
public record EstabController(EstabService estabService) {

		@GetMapping
		@Operation(summary = "Obter todos os estabelecimentos", description = "Recuperar uma lista de todos os estabelecimentos registrados")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operação concluída com sucesso")
		})
		public ResponseEntity<List<EstabelecimentoDto>> findAll(){
				var estabelecimento = estabService.findAll();
				var estabDto = estabelecimento.stream().map(EstabelecimentoDto::new).collect(Collectors.toList());
				return ResponseEntity.ok(estabDto);
		}

		@GetMapping("/{id}")
		@Operation(summary = "Obter estabelecimento por ID", description = "Recuperar estabelecimento")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operação concluída com sucesso"),
						@ApiResponse(responseCode = "404", description = "estabelecimento not found")
		})
		public ResponseEntity<EstabelecimentoDto> findById(@PathVariable Long id){
				var estabelecimento = estabService.findById(id);
				var estabDto = new EstabelecimentoDto(estabelecimento);
				return ResponseEntity.ok(estabDto);
		}

		@PostMapping
		@Operation(summary = "Criar estabelecimento", description = "Criar um estabelecimento")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Estabelecimento criado com sucesso"),
						@ApiResponse(responseCode = "422", description = "Dados de veículo fornecidos inválidos")
		})
		public ResponseEntity<EstabelecimentoDto> create(@RequestBody Estabelecimento estabelecimento){
				var estabCreated = estabService.create(estabelecimento);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().
								path("/{id}").
								buildAndExpand(estabCreated.getId()).
								toUri();
				return ResponseEntity.created(location).body(new EstabelecimentoDto(estabCreated));
		}

		@PutMapping("/{id}")
		@Operation(summary = "Alteração de estabelecimento", description = "Atualizar os dados de um estabelecimento existente com base em seu ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "estabelecimento alterado com sucesso"),
						@ApiResponse(responseCode = "404", description = "estabelecimento not found"),
						@ApiResponse(responseCode = "422", description = "Dados de estabelecimento fornecidos inválidos")
		})
		public ResponseEntity<EstabelecimentoDto> update(@PathVariable Long id, @RequestBody EstabelecimentoDto estabelecimentoDto){
				var estabelecimento = estabService.update(id, estabelecimentoDto.toModel());
				return ResponseEntity.ok(new EstabelecimentoDto(estabelecimento));
		}

		@DeleteMapping("/{id}")
		@Operation(summary = "Delete a estabelecimento", description = "Delete an existing estabelecimento based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "estabelecimento deletado com sucesso"),
						@ApiResponse(responseCode = "404", description = "estabelecimento not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id){
				estabService.delete(id);
				return ResponseEntity.noContent().build();
		}
}
