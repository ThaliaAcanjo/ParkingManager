package thalia.ParkingManager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity(name="tb_parking")
public class Parking {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String name;
		@Column(unique = true)
		private String cnpj;
		private String address;
		@Column(precision = 13, scale = 2)
		private BigDecimal pricePerHour;

		@OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
		private List<ParkingVehicleType> parkingVehicleTypes;

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


		public List<ParkingVehicleType> getParkingVehicleTypes() {
				return parkingVehicleTypes;
		}

		public void setParkingVehicleTypes(List<ParkingVehicleType> parkingVehicleTypes) {
				this.parkingVehicleTypes = parkingVehicleTypes;
		}
}
