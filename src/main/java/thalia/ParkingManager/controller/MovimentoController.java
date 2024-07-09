package thalia.ParkingManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thalia.ParkingManager.controller.dtos.EstabelecimentoDto;
import thalia.ParkingManager.controller.dtos.MovimentoDto;
import thalia.ParkingManager.model.Estabelecimento;
import thalia.ParkingManager.model.Movimento;
import thalia.ParkingManager.service.MovimentoService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/movimento")
@Tag(name="Entrada e saída controllers", description = "Restful API")
public record MovimentoController(MovimentoService movimentoService) {

		@GetMapping
		@Operation(summary = "Obter todos os movimentos", description = "Recuperar uma lista de todos os estabelecimentos registrados")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operação concluída com sucesso")
		})
		public ResponseEntity<List<MovimentoDto>> findAll(){
				var movimentos = movimentoService.findAll();
				var movimentosDto = movimentos.stream().map(MovimentoDto::new).collect(Collectors.toList());
				return ResponseEntity.ok(movimentosDto);
		}

		@GetMapping("/{id}")
		@Operation(summary = "Obter movimento por id", description = "Recuperar um movimento")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "Operação concluída com sucesso"),
						@ApiResponse(responseCode = "404", description = "movimento not found")
		})
		public ResponseEntity<MovimentoDto> findById(@PathVariable Long id){
				var movimento = movimentoService.findById(id);
				var movimentosDto = new MovimentoDto(movimento);
				return ResponseEntity.ok(movimentosDto);
		}

		@PostMapping
		@Operation(summary = "Criar movimento", description = "Criar um movimento")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "movimento criado com sucesso"),
						@ApiResponse(responseCode = "422", description = "Dados de entrada/saída fornecidos inválidos")
		})
		public ResponseEntity<MovimentoDto> create(@RequestBody Movimento movimento){
				var movimentoCreated = movimentoService.create(movimento);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().
								path("/{id}").
								buildAndExpand(movimentoCreated.getId()).
								toUri();
				return ResponseEntity.created(location).body(new MovimentoDto(movimentoCreated));
		}

		@PutMapping("/{id}")
		@Operation(summary = "Alteração de movimento", description = "Atualizar os dados de um movimento existente com base em seu ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "movimento alterado com sucesso"),
						@ApiResponse(responseCode = "404", description = "movimento not found"),
						@ApiResponse(responseCode = "422", description = "Dados de entrada/saída fornecidos inválidos")
		})
		public ResponseEntity<MovimentoDto> update(@PathVariable Long id, @RequestBody MovimentoDto movimentoDto){
				var movimento = movimentoService.update(id, movimentoDto.toModel());
				return ResponseEntity.ok(new MovimentoDto(movimento));
		}

		@DeleteMapping("/{id}")
		@Operation(summary = "Delete a movimento", description = "Delete an existing movimento based on its ID")
		@ApiResponses(value = {
						@ApiResponse(responseCode = "204", description = "movimento deletado com sucesso"),
						@ApiResponse(responseCode = "404", description = "movimento not found")
		})
		public ResponseEntity<Void> delete(@PathVariable Long id){
				movimentoService.delete(id);
				return ResponseEntity.noContent().build();
		}

}
