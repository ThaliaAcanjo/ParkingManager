package thalia.ParkingManager.model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import thalia.ParkingManager.exceptionhandler.NotFoundException;
import thalia.ParkingManager.repository.ParkingRepository;
import thalia.ParkingManager.repository.VehicleTypeRepository;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {
		@PersistenceContext
		private EntityManager entityManager;

		@Autowired
		private final VehicleTypeRepository vehicleTypeRepository;

		@Autowired
		private final ParkingRepository parkingRepository;

		public DataLoader(VehicleTypeRepository vehicleTypeRepository, ParkingRepository parkingRepository) {
				this.vehicleTypeRepository = vehicleTypeRepository;
				this.parkingRepository = parkingRepository;
		}

		@Override
		@Transactional
		public void run(String... args) throws Exception {
				createAndSaveVehicleTypes();
		}

		private void createAndSaveVehicleTypes() {
				if (entityManager.createQuery("SELECT COUNT(v) FROM tb_vehicle_type v", Long.class).getSingleResult() == 0) {
						var vehicleSmall = new VehicleType();
						vehicleSmall.setName("Small vehicles");
						entityManager.persist(vehicleSmall);

						var vehicleMedium = new VehicleType();
						vehicleMedium.setName("Medium vehicles");
						entityManager.persist(vehicleMedium);

						var vehicleLarge = new VehicleType();
						vehicleLarge.setName("Large vehicles");
						entityManager.persist(vehicleLarge);

						var vehicleMotorcycle = new VehicleType();
						vehicleMotorcycle.setName("Motorcycle vehicles");
						entityManager.persist(vehicleMotorcycle);
				}

				if (entityManager.createQuery("SELECT COUNT(p) FROM tb_parking p", Long.class).getSingleResult() == 0) {
						var parking = new Parking();
						parking.setName("Parking Manager");
						parking.setCnpj("62.936.478/0001-85");
						parking.setAddress("Rua dos Pe");
						parking.setPricePerHour(BigDecimal.valueOf(10.90));
						entityManager.persist(parking);

						var parking2 = new Parking();
						parking2.setName("Parking Manager 2");
						parking2.setCnpj("22.936.478/0001-10");
						parking2.setAddress("Rua das dores");
						parking2.setPricePerHour(BigDecimal.valueOf(5.90));
						entityManager.persist(parking2);
				}

				if (entityManager.createQuery("SELECT COUNT(c) FROM tb_checkInOut c", Long.class).getSingleResult() == 0) {
						var vehicle = new Vehicle();
						var vehicleType = this.vehicleTypeRepository.findById(Long.valueOf(1)).orElseThrow(NotFoundException::new);
						vehicle.setVehicleType(vehicleType);
						vehicle.setLicensePlate("EIB20");
						vehicle.setColor("white");
						vehicle.setModel("cor");
						entityManager.persist(vehicle);
				}
		}
}
