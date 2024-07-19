package thalia.ParkingManager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity(name="tb_checkInOut")
public class CheckInOut {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@ManyToOne(cascade = CascadeType.ALL)
		private Parking parking;
		@ManyToOne(cascade = CascadeType.ALL)
		private Vehicle vehicle;
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private LocalDateTime dateHourCheckIn;
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private LocalDateTime dateHourCheckOut;
		@Column(precision = 13, scale = 2)
		private BigDecimal amount;
		private boolean finished = false;

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public Parking getParking() {
				return parking;
		}

		public void setParking(Parking parking) {
				this.parking = parking;
		}

		public Vehicle getVehicle() {
				return vehicle;
		}

		public void setVehicle(Vehicle vehicle) {
				this.vehicle = vehicle;
		}

		public LocalDateTime getDateHourCheckIn() {
				return dateHourCheckIn;
		}

		public void setDateHourCheckIn(LocalDateTime dateHourCheckIn) {
				this.dateHourCheckIn = dateHourCheckIn;
		}

		public LocalDateTime getDateHourCheckOut() {
				return dateHourCheckOut;
		}

		public void setDateHourCheckout(LocalDateTime dateHourCheckout) {
				this.dateHourCheckOut = dateHourCheckout;
		}

		public BigDecimal getAmount() {
				return amount;
		}

		public void setAmount(BigDecimal amount) {
				this.amount = amount;
		}

		public boolean isFinished() {
				return finished;
		}

		public void setFinished(boolean finished) {
				this.finished = finished;
		}

		@Override
		public String toString() {
				return "CheckInOut{" +
								"id=" + id +
								", parking=" + parking +
								", vehicle=" + vehicle +
								", dateHourCheckIn=" + dateHourCheckIn +
								", dateHourCheckout=" + dateHourCheckOut +
								", amount=" + amount +
								", finished=" + finished +
								'}';
		}
}
