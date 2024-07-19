package thalia.ParkingManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "tb_parking_spot")
public class ParkingSpot {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "parking_id", nullable = false)
		private Parking parking;

		@ManyToOne//(fetch = FetchType.EAGER)
		@JoinColumn(name = "vehicle_type_id")
		private VehicleType vehicleType;
		@Column(name = "spot_number", nullable = false)
		private String spotNumber;
		@Column(name = "is_occupied", nullable = false)
		private boolean occupied;

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

	  public String getSpotNumber() {
				return spotNumber;
		}

		public void setSpotNumber(String spotNumber) {
				this.spotNumber = spotNumber;
		}

		public boolean isOccupied() {
				return occupied;
		}

		public void setOccupied(boolean occupied) {
				this.occupied = occupied;
		}

		@JsonIgnore
		public Parking getParking() {
				return parking;
		}

		public void setParking(Parking parking) {
				this.parking = parking;
		}

		public VehicleType getVehicleType() {
				return vehicleType;
		}

		public void setVehicleType(VehicleType vehicleType) {
				this.vehicleType = vehicleType;
		}
}
/*@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumns(value = {
						@JoinColumn(name = "parking_id", referencedColumnName = "parking_id", updatable = false, insertable = false),
						@JoinColumn(name = "vehicle_type_id", referencedColumnName = "vehicle_type_id", updatable = false, insertable = false)})
		private ParkedVehicleType parkedVehicleType;*/