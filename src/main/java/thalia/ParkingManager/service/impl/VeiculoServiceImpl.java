package thalia.ParkingManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thalia.ParkingManager.model.Veiculo;
import thalia.ParkingManager.repository.VeiculoRepository;
import thalia.ParkingManager.service.VeiculoService;
import thalia.ParkingManager.service.exception.BusinessException;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class VeiculoServiceImpl implements VeiculoService {
		private static final Long UNCHANGEABLE_USER_ID = 1L;
		@Autowired
		private VeiculoRepository veiculoRepository;
		@Override
		public List<Veiculo> findAll() {
				return null;
		}

		@Override
		public Veiculo findById(Long aLong) {
				return null;
		}

		@Override
		public Veiculo create(Veiculo veiculoCreate) {
				ofNullable(veiculoCreate).orElseThrow(() -> new BusinessException("Veiculo to create must not be null."));
				ofNullable(veiculoCreate.getPlaca()).orElseThrow(() -> new BusinessException("Placa must not be null."));
				ofNullable(veiculoCreate.getModelo()).orElseThrow(() -> new BusinessException("Modelo must not be null."));
				ofNullable(veiculoCreate.getTipoVeiculo()).orElseThrow(() -> new BusinessException("Tipo Veiculo must not be null."));

				this.validateChangeableId(veiculoCreate.getId(), "created");
				return this.veiculoRepository.save(veiculoCreate);
		}

		@Override
		public Veiculo update(Long aLong, Veiculo entity) {
				return null;
		}

		@Override
		public void delete(Long aLong) {

		}

		private void validateChangeableId(Long id, String operation) {
				if (UNCHANGEABLE_USER_ID.equals(id)) {
						throw new BusinessException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
				}
		}

}
