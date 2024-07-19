package thalia.ParkingManager.controller.dtos;

import thalia.ParkingManager.model.VehicleType;

public record VehicleTypeDTO(Long id,
														 String name) {

		public VehicleTypeDTO(VehicleType model){
				this(
						model.getId(),
						model.getName()
				);
		}
		public VehicleType toModel(){
				VehicleType model = new VehicleType();
				model.setId(this.id);
				model.setName(this.name);
				return model;
		}
}
