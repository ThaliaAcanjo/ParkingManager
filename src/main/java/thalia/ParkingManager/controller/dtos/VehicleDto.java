package thalia.ParkingManager.controller.dtos;

import thalia.ParkingManager.model.Vehicle;
import thalia.ParkingManager.model.VehicleType;

import static java.util.Optional.ofNullable;

public record VehicleDto(Long id,
												 String licensePlate,
												 String model,
												 String color,
												 VehicleType vehicleType) {

		public VehicleDto(Vehicle model) {
				this(
								model.getId(),
								model.getLicensePlate(),
								model.getModel(),
								model.getColor(),
								model.getVehicleType()
				);
		}

		public Vehicle toModel() {
				Vehicle model = new Vehicle();
				model.setId(this.id);
				model.setLicensePlate(this.licensePlate);
				model.setModel(ofNullable(this.model).orElse(null));
				model.setColor(ofNullable(this.color).orElse(null));
				model.setVehicleType(ofNullable(this.vehicleType).orElse(null));
				return model;
		}

}
