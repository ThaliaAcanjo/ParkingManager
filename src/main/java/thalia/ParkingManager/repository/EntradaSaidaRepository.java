package thalia.ParkingManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thalia.ParkingManager.model.EntradaSaida;

@Repository
public interface EntradaSaidaRepository extends JpaRepository<EntradaSaida, Long> {
}
