package thalia.ParkingManager.controller.dtos;

import thalia.ParkingManager.model.Veiculo;
import thalia.ParkingManager.model.enums.TipoVeiculo;

import static java.util.Optional.ofNullable;

public record VeiculoDto(Long id,
												 String placa,
												 String marca,
												 String modelo,
												 String cor,
												 TipoVeiculo tipoVeiculo) {

		public VeiculoDto(Veiculo model) {
				this(
								model.getId(),
								model.getPlaca(),
								model.getMarca(),
								model.getModelo(),
								model.getCor(),
								model.getTipoVeiculo()
				);
		}

		public Veiculo toModel() {
				Veiculo model = new Veiculo();
				model.setId(this.id);
				model.setPlaca(this.placa);
				model.setMarca(ofNullable(this.marca).orElse(null));
				model.setModelo(ofNullable(this.modelo).orElse(null));
				model.setCor(ofNullable(this.cor).orElse(null));
				model.setTipoVeiculo(ofNullable(this.tipoVeiculo).orElse(null));
				return model;
		}

}
