package thalia.ParkingManager.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Objects;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "tb_parking_vehicle_type")
public class ParkingVehicleType {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@ManyToOne
		@JoinColumn(name = "parking_id")
		private Parking parking;
		@ManyToOne
		@JoinColumn(name = "vehicle_type_id")
		//@Column(insertable=false, updatable=false)
		private VehicleType vehicleType;
		private Integer numberOfSpots;

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public VehicleType getVehicleType() {
				return vehicleType;
		}

		public void setVehicleType(VehicleType vehicleType) {
				this.vehicleType = vehicleType;
		}

		public Integer getNumberOfSpots() {
				return numberOfSpots;
		}

		public void setNumberOfSpots(Integer numberOfSpots) {
				this.numberOfSpots = numberOfSpots;
		}

		//@JsonIgnoreProperties("parkingVehicleTypes")
		@JsonIgnore
		public Parking getParking() {
				return parking;
		}

		public void setParking(Parking parking) {
				this.parking = parking;
		}

		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				ParkingVehicleType that = (ParkingVehicleType) o;
				return id.equals(that.id) && vehicleType.equals(that.vehicleType) && numberOfSpots.equals(that.numberOfSpots);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id, vehicleType, numberOfSpots);
		}
}
