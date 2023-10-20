package thalia.ParkingManager.model.enums;

public enum TipoVeiculo {
		CARRO(0, "Carro"), MOTO(1, "Moto");

		private Integer cod;
		private String descricao;

		private TipoVeiculo(Integer cod, String descricao) {
				this.cod = cod;
				this.descricao = descricao;
		}

		public Integer getCod() {
				return cod;
		}

		public String getDescricao() {
				return descricao;
		}
}
