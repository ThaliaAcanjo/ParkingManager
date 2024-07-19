package thalia.ParkingManager.controller.dtos.ParkingVehicleType;

import thalia.ParkingManager.model.ParkingVehicleType;
import thalia.ParkingManager.model.Vehicle;
import thalia.ParkingManager.model.VehicleType;

public class ParkingVehicleTypeFindDTO {
		private Long id;
		private Long parkingId;
		private VehicleType vehicleType;
		private Integer numberOfSpots;

		public ParkingVehicleTypeFindDTO(Long id, Long id1, VehicleType vehicleType, Integer numberOfSpots) {
				this.id = id;
				this.parkingId = id1;
				this.vehicleType = vehicleType;
				this.numberOfSpots = numberOfSpots;
		}

		public Long getId() {
				return id;
		}
		public void setId(Long id) {
				this.id = id;
		}
		public Long getParkingId() {
				return parkingId;
		}
		public void setParkingId(Long parkingId) {
				this.parkingId = parkingId;
		}
		public VehicleType getVehicleType() { return vehicleType;}
		public void setVehicleType(VehicleType vehicleType) {this.vehicleType = vehicleType;}
		public Integer getNumberOfSpots() {
				return numberOfSpots;
		}
		public void setNumberOfSpots(Integer numberOfSpots) {
				this.numberOfSpots = numberOfSpots;
		}
}
