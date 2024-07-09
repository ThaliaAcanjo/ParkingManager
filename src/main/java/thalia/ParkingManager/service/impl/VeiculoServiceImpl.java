package thalia.ParkingManager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.exceptionhandler.NotFoundException;
import thalia.ParkingManager.model.Veiculo;
import thalia.ParkingManager.repository.VeiculoRepository;
import thalia.ParkingManager.service.VeiculoService;
import thalia.ParkingManager.service.exception.BusinessException;

import java.util.List;

@Service
public class VeiculoServiceImpl implements VeiculoService {
		private static final Long UNCHANGEABLE_VEICULO_ID = 0L;
		private final VeiculoRepository veiculoRepository;

		public VeiculoServiceImpl(VeiculoRepository veiculoRepository) {

				this.veiculoRepository = veiculoRepository;
		}

		@Transactional(readOnly = true)
		public List<Veiculo> findAll() {
				return this.veiculoRepository.findAll();
		}

		@Transactional(readOnly = true)
		public Veiculo findById(Long id) {
				return this.veiculoRepository.findById(id).orElseThrow(NotFoundException::new);
		}

		@Transactional
		public Veiculo create(Veiculo veiculoCreate) {
				if (veiculoCreate.getPlaca() == null)
						throw new BadRequestException("O campo placa é obrigatório");
				if (veiculoCreate.getModelo() == null)
						throw new BadRequestException("O campo modelo é obrigatório");
				if (veiculoCreate.getMarca() == null)
						throw new BadRequestException("O campo marca é obrigatório");
				if (veiculoCreate.getCor() == null)
						throw new BadRequestException("O campo cor é obrigatório");
				if (veiculoCreate.getTipoVeiculo() == null)
						throw new BadRequestException("O campo tipo veículo é obrigatório");
				if (veiculoCreate.getNomeDono() == null)
						throw new BadRequestException("O campo nome do dono é obrigatório");
				if (veiculoCreate.getDocumentoDono() == null)
						throw new BadRequestException("O campo documento do dono é obrigatório");
				this.validateChangeableId(veiculoCreate.getId(), "created");
				return this.veiculoRepository.save(veiculoCreate);
		}

		@Transactional
		public Veiculo update(Long id, Veiculo veiculo) {
				this.validateChangeableId(id, "updated");
				Veiculo veiculoUpdate = this.findById(id);
				if (!veiculoUpdate.getId().equals(veiculo.getId())) {
						throw new BadRequestException("update IDs must be the same.");
				}

				return veiculoRepository.save(veiculo);
		}

		@Override
		public void delete(Long id) {
				this.validateChangeableId(id, "deleted");
				Veiculo veiculo = this.findById(id);
				veiculoRepository.delete(veiculo);
		}

		private void validateChangeableId(Long id, String operation) {
				if (UNCHANGEABLE_VEICULO_ID.equals(id)) {
						throw new BusinessException("Veículo com ID %d não pode ser %s.".formatted(UNCHANGEABLE_VEICULO_ID, operation));
				}
		}

}
