package thalia.ParkingManager.controller.dtos.Parking;
import java.math.BigDecimal;
public class ParkingDTO {
		private Long id;
		private String name;
		private String cnpj;
		private String address;
		private BigDecimal pricePerHour;

		public ParkingDTO(Long id, String name, String cnpj, String address, BigDecimal pricePerHour) {
				this.id = id;
				this.name = name;
				this.cnpj = cnpj;
				this.address = address;
				this.pricePerHour = pricePerHour;
		}

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public String getName() {
				return name;
		}

		public void setName(String name) {
				this.name = name;
		}

		public String getCnpj() {
				return cnpj;
		}

		public void setCnpj(String cnpj) {
				this.cnpj = cnpj;
		}

		public String getAddress() {
				return address;
		}

		public void setAddress(String address) {
				this.address = address;
		}

		public BigDecimal getPricePerHour() {
				return pricePerHour;
		}

		public void setPricePerHour(BigDecimal pricePerHour) {
				this.pricePerHour = pricePerHour;
		}
}