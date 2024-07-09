package thalia.ParkingManager.controller.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import thalia.ParkingManager.model.Estabelecimento;
import thalia.ParkingManager.model.Movimento;
import thalia.ParkingManager.model.Veiculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

public record MovimentoDto(Long id,
													Estabelecimento estabelecimento,
													Veiculo veiculo,
													LocalDateTime dataHoraEntrada,
													LocalDateTime dataHoraSaida,
													BigDecimal valorTotal) {

		public MovimentoDto(Movimento model) {
				this(
								model.getId(),
								model.getEstabelecimento(),
								model.getVeiculo(),
								model.getDataHoraEntrada(),
								model.getDataHoraSaida(),
								model.getValorHora()
				);
		}
		public Movimento toModel() {
				Movimento model = new Movimento();
				model.setId(this.id);
				model.setEstabelecimento(this.estabelecimento);
				model.setVeiculo(ofNullable(this.veiculo).orElse(null));
				model.setDataHoraEntrada(ofNullable(this.dataHoraEntrada).orElse(null));
				model.setDataHoraSaida(ofNullable(this.dataHoraSaida).orElse(null));
				model.setValorHora(ofNullable(this.valorTotal).orElse(null));
				return model;
		}
}
