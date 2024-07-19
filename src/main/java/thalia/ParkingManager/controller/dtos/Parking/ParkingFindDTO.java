package thalia.ParkingManager.controller.dtos.Parking;

import thalia.ParkingManager.controller.dtos.ParkingVehicleType.ParkingVehicleTypeFindDTO;
import thalia.ParkingManager.model.ParkingVehicleType;

import java.math.BigDecimal;
import java.util.List;

public class ParkingFindDTO {
		private Long id;
		private String name;
		private String cnpj;
		private String address;
		private BigDecimal pricePerHour;
		//private List<ParkingVehicleType> parkingVehicleTypes;
		private List<ParkingVehicleTypeFindDTO> parkingVehicleTypeFindDTOList;

		public ParkingFindDTO(Long id, String name, String cnpj, String address, BigDecimal pricePerHour, List<ParkingVehicleTypeFindDTO> parkingVehicleTypeFindDTOList) {
				this.id = id;
				this.name = name;
				this.cnpj = cnpj;
				this.address = address;
				this.pricePerHour = pricePerHour;
				this.parkingVehicleTypeFindDTOList = parkingVehicleTypeFindDTOList;
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

		public List<ParkingVehicleTypeFindDTO> getParkingVehicleTypeFindDTOList() {
				return parkingVehicleTypeFindDTOList;
		}

		public void setParkingVehicleTypeFindDTOList(List<ParkingVehicleTypeFindDTO> parkingVehicleTypeFindDTOList) {
				this.parkingVehicleTypeFindDTOList = parkingVehicleTypeFindDTOList;
		}
}
