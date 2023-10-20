package thalia.ParkingManager.controller.dtos;

import thalia.ParkingManager.model.Estabelecimento;
import thalia.ParkingManager.model.Veiculo;

import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

public record EstabelecimentoDto(Long id,
																 String nome,
																 String cnpj,
																 String endereco,
																 String telefone,
																 Integer vagasPorCarros,
																 Integer vagasPorMotos,
																 BigDecimal valorHora) {

		public EstabelecimentoDto(Estabelecimento model) {
				this(
								model.getId(),
								model.getNome(),
								model.getCnpj(),
								model.getEndereco(),
								model.getTelefone(),
								model.getVagasPorCarros(),
								model.getVagasPorMotos(),
								model.getValorHora()
				);
		}
		public Estabelecimento toModel() {
				Estabelecimento model = new Estabelecimento();
				model.setId(this.id);
				model.setNome(this.nome);
				model.setCnpj(ofNullable(this.cnpj).orElse(null));
				model.setEndereco(ofNullable(this.endereco).orElse(null));
				model.setTelefone(ofNullable(this.telefone).orElse(null));
				model.setVagasPorCarros(ofNullable(this.vagasPorCarros).orElse(null));
				model.setVagasPorCarros(ofNullable(this.vagasPorMotos).orElse(null));
				model.setValorHora(ofNullable(this.valorHora).orElse(null));
				return model;
		}
}
