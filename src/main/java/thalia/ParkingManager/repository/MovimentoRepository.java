package thalia.ParkingManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thalia.ParkingManager.model.Movimento;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
}
