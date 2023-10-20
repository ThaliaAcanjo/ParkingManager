package thalia.ParkingManager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name="tb_estabelecimento")
public class Estabelecimento {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String nome;
		@Column(unique = true)
		private String cnpj;
		private String endereco;
		private String telefone;
		private Integer vagasPorCarros;
		private Integer vagasPorMotos;
		@Column(precision = 13, scale = 2)
		private BigDecimal valorHora;

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public String getNome() {
				return nome;
		}

		public void setNome(String nome) {
				this.nome = nome;
		}

		public String getCnpj() {
				return cnpj;
		}

		public void setCnpj(String cnpj) {
				this.cnpj = cnpj;
		}

		public String getEndereco() {
				return endereco;
		}

		public void setEndereco(String endereco) {
				this.endereco = endereco;
		}

		public String getTelefone() {
				return telefone;
		}

		public void setTelefone(String telefone) {
				this.telefone = telefone;
		}

		public Integer getVagasPorCarros() {
				return vagasPorCarros;
		}

		public void setVagasPorCarros(Integer vagasPorCarros) {
				this.vagasPorCarros = vagasPorCarros;
		}

		public Integer getVagasPorMotos() {
				return vagasPorMotos;
		}

		public void setVagasPorMotos(Integer vagasPorMotos) {
				this.vagasPorMotos = vagasPorMotos;
		}

		public BigDecimal getValorHora() {
				return valorHora;
		}

		public void setValorHora(BigDecimal valorHora) {
				this.valorHora = valorHora;
		}

		/*@Override
		public String toString() {
				return "Estabelecimento{" +
								"id=" + id +
								", nome='" + nome + '\'' +
								", cnpj='" + cnpj + '\'' +
								", endereco='" + endereco + '\'' +
								", telefone='" + telefone + '\'' +
								", vagasPorCarros=" + vagasPorCarros +
								", vagasPorMotos=" + vagasPorMotos +
								'}';
		}*/
}
