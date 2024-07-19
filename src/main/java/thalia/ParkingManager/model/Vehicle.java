package thalia.ParkingManager.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity(name = "tb_vehicles")
public class Vehicle {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@ManyToOne
		@JoinColumn(name = "vehicle_type_id")
		private VehicleType vehicleType;

		public VehicleType getVehicleType() {
				return vehicleType;
		}

		public void setVehicleType(VehicleType vehicleType) {
				this.vehicleType = vehicleType;
		}

		@Column(unique = true)
		private String licensePlate;
		private String model;
		private String color;

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}
		public String getLicensePlate() {
				return licensePlate;
		}

		public void setLicensePlate(String licensePlate) {
				this.licensePlate = licensePlate;
		}

		public String getModel() {
				return model;
		}

		public void setModel(String model) {
				this.model = model;
		}

		public String getColor() {
				return color;
		}

		public void setColor(String color) {
				this.color = color;
		}
}
