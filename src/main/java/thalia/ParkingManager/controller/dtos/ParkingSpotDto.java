package thalia.ParkingManager.controller.dtos;

import thalia.ParkingManager.model.Parking;
import thalia.ParkingManager.model.ParkingSpot;
import thalia.ParkingManager.model.VehicleType;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public record ParkingSpotDto(Long id, Parking parking, VehicleType vehicleType, String spotNumber, boolean occupied) {
/*
		public ParkingSpotDto(ParkingSpot model){
				this(
								model.getId(),
								model.getParking(),
								model.getVehicleType(),
								model.getSpotNumber(),
								model.isOccupied()
				);
		}*/

		public ParkingSpotDto(Long id, String spotNumber, VehicleType vehicleType, boolean occupied) {
				this(id, null, vehicleType, spotNumber, occupied);
		}
		public ParkingSpotDto(Long id, String spotNumber, boolean occupied){
				this(id, null, null, spotNumber, occupied);
		}

		public ParkingSpot toModel(){
				ParkingSpot model = new ParkingSpot();
				model.setId(this.id);
				model.setSpotNumber(ofNullable(this.spotNumber).orElse("000"));
				model.setOccupied(Optional.of(this.occupied()).orElse(false));
				return model;
		}
}
