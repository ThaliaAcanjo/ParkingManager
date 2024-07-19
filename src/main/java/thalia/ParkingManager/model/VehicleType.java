package thalia.ParkingManager.model;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceConstructor;

@Entity(name="tb_vehicle_type")
public class VehicleType {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@Column(unique = true)
		private String name;

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
}
