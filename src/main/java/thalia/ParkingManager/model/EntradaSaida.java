package thalia.ParkingManager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity(name="tb_entrada_saida")
public class EntradaSaida {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@OneToOne(cascade = CascadeType.ALL)
		private Estabelecimento estabelecimento;
		@OneToOne(cascade = CascadeType.ALL)
		private Veiculo veiculo;
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private LocalDateTime dataHoraEntrada;
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private LocalDateTime dataHoraSaida;
		@Column(precision = 13, scale = 2)
		private BigDecimal valorTotal;

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public Estabelecimento getEstabelecimento() {
				return estabelecimento;
		}

		public void setEstabelecimento(Estabelecimento estabelecimento) {
				this.estabelecimento = estabelecimento;
		}

		public Veiculo getVeiculo() {
				return veiculo;
		}

		public void setVeiculo(Veiculo veiculo) {
				this.veiculo = veiculo;
		}

		public LocalDateTime getDataHoraEntrada() {
				return dataHoraEntrada;
		}

		public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
				this.dataHoraEntrada = dataHoraEntrada;
		}

		public LocalDateTime getDataHoraSaida() {
				return dataHoraSaida;
		}

		public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
				this.dataHoraSaida = dataHoraSaida;
		}

		public BigDecimal getValorHora() {
				return valorTotal;
		}

		public void setValorHora(BigDecimal valorTotal) {
				this.valorTotal = valorTotal;
		}

		@Override
		public String toString() {
				return "EntradaSaida{" +
								"id=" + id +
								", estabelecimento=" + estabelecimento +
								", veiculo=" + veiculo +
								", dataHoraEntrada=" + dataHoraEntrada +
								", dataHoraSaida=" + dataHoraSaida +
								", valorHora=" + valorTotal +
								'}';
		}
}
