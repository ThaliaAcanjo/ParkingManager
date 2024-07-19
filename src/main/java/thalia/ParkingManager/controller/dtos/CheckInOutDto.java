package thalia.ParkingManager.controller.dtos;
import thalia.ParkingManager.model.Parking;
import thalia.ParkingManager.model.CheckInOut;
import thalia.ParkingManager.model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

public record CheckInOutDto(Long id,
													Parking parking,
													Vehicle vehicle,
													LocalDateTime dateHourCheckIn,
													LocalDateTime dateHourCheckOut,
													BigDecimal amount,
													Boolean finished ) {

		public CheckInOutDto(CheckInOut model) {
				this(
								model.getId(),
								model.getParking(),
								model.getVehicle(),
								model.getDateHourCheckIn(),
								model.getDateHourCheckOut(),
								model.getAmount(),
								model.isFinished()
				);
		}
		public CheckInOut toModel() {
				CheckInOut model = new CheckInOut();
				model.setId(this.id);
				model.setParking(this.parking);
				model.setVehicle(this.vehicle);
				model.setDateHourCheckIn(ofNullable(this.dateHourCheckIn).orElse(LocalDateTime.now()));
				model.setDateHourCheckout(ofNullable(this.dateHourCheckOut).orElse(LocalDateTime.now()));
				model.setAmount(ofNullable(this.amount).orElse(BigDecimal.valueOf(0)));
				model.setFinished(ofNullable(this.finished).orElse(false));
				return model;
		}
}
