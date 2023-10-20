package thalia.ParkingManager.model;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import thalia.ParkingManager.model.enums.TipoVeiculo;
@Entity(name = "tb_veiculos")
public class Veiculo {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@Column(unique = true)
		private String placa;
		private String marca;
		private String modelo;
		private String cor;

	  private TipoVeiculo tipoVeiculo;

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public String getPlaca() {
				return placa;
		}

		public void setPlaca(String placa) {
				this.placa = placa;
		}

		public String getMarca() {
				return marca;
		}

		public void setMarca(String marca) {
				this.marca = marca;
		}

		public String getModelo() {
				return modelo;
		}

		public void setModelo(String modelo) {
				this.modelo = modelo;
		}

		public String getCor() {
				return cor;
		}

		public void setCor(String cor) {
				this.cor = cor;
		}

		public TipoVeiculo getTipoVeiculo() {
				return tipoVeiculo;
		}

		public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
				this.tipoVeiculo = tipoVeiculo;
		}

		@Override
		public String toString() {
				return "Veiculo{" +
								"id=" + id +
								", placa='" + placa + '\'' +
								", marca='" + marca + '\'' +
								", modelo='" + modelo + '\'' +
								", cor='" + cor + '\'' +
								", tipoVeiculo=" + tipoVeiculo +
								'}';
		}
}
