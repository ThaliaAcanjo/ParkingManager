package thalia.ParkingManager.controller.dtos.ParkingVehicleType;

public class ParkingVehicleTypeDTO {
		private Long id;
		private Long parkingId;
		private Long vehicleTypeId;
		private Integer numberOfSpots;

		public ParkingVehicleTypeDTO(Long id, Long id1, Long id2, String name, Integer numberOfSpots) {
				this.id = id;
				this.parkingId = id1;
				this.vehicleTypeId = id2;
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
		public Long getVehicleTypeId() {
				return vehicleTypeId;
		}
		public void setVehicleTypeId(Long vehicleTypeId) {
				this.vehicleTypeId = vehicleTypeId;
		}
		public Integer getNumberOfSpots() {
				return numberOfSpots;
		}
		public void setNumberOfSpots(Integer numberOfSpots) {
				this.numberOfSpots = numberOfSpots;
		}
}
