package thalia.ParkingManager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.model.Movimento;
import thalia.ParkingManager.repository.EstabRepository;
import thalia.ParkingManager.repository.MovimentoRepository;
import thalia.ParkingManager.repository.VeiculoRepository;
import thalia.ParkingManager.service.MovimentoService;
import thalia.ParkingManager.service.exception.BusinessException;
import thalia.ParkingManager.service.exception.NotFoundException;

import java.util.List;

@Service
public class MovimentoServiceImpl implements MovimentoService {
		private static final Long UNCHANGEABLE_VEICULO_ID = 0L;

		private final MovimentoRepository movimentoRepository;
		private final VeiculoRepository veiculoRepository;
		private final EstabRepository estabRepository;

		public MovimentoServiceImpl(MovimentoRepository movimentoRepository, VeiculoRepository veiculoRepository, EstabRepository estabRepository) {
				this.movimentoRepository = movimentoRepository;
				this.veiculoRepository = veiculoRepository;
				this.estabRepository = estabRepository;
		}

		@Transactional(readOnly = true)
		public List<Movimento> findAll() {
				return this.movimentoRepository.findAll();
		}

		@Transactional(readOnly = true)
		public Movimento findById(Long id) {
				return this.movimentoRepository.findById(id).orElseThrow(NotFoundException::new);
		}

		@Transactional
		public Movimento create(Movimento movimento) {
				if (movimento.getValorHora() == null)
						throw new BadRequestException("O campo valor hora é obrigatório");
				if (movimento.getEstabelecimento() == null)
						throw new BadRequestException("O campo estabelecimento é obrigatório");
				//if (movimento.getVeiculo() == null)
				//		throw new BadRequestException("O campo veiculo é obrigatório");
				if (movimento.getDataHoraEntrada() == null)
						throw new BadRequestException("O campo data hora de entrada é obrigatório");
				if (movimento.getDataHoraSaida() == null)
						throw new BadRequestException("O campo data hora de saída é obrigatório");

				var veiculo = this.veiculoRepository.findById(movimento.getVeiculo().getId());
				if (veiculo.get().getId() == null)
						throw new BadRequestException("veiculo é obrigatório / não encontrado");

				var estabelecimento = this.estabRepository.findById(movimento.getEstabelecimento().getId());
				if (estabelecimento.get().getId() == null)
						throw new BadRequestException("estabelecimento é obrigatório / não encontrado");

				this.validateChangeableId(movimento.getId(), "created");
				return this.movimentoRepository.save(movimento);
		}

		@Transactional
		public Movimento update(Long id, Movimento movimento) {
				this.validateChangeableId(id, "updated");
				var movimentoUpdated = this.findById(id);
				if (!movimentoUpdated.getId().equals(movimento.getId())) {
						throw new BadRequestException("update IDs must be the same.");
				}
				return this.movimentoRepository.save(movimento);
		}

		@Override
		public void delete(Long id) {
				this.validateChangeableId(id, "deleted");
				var movimentoDeleted = this.findById(id);
				movimentoRepository.delete(movimentoDeleted);
		}

		private void validateChangeableId(Long id, String operation) throws BusinessException {
				if (UNCHANGEABLE_VEICULO_ID.equals(id)) {
						throw new BusinessException("Estabelecimento %d não pode ser %s.".formatted(UNCHANGEABLE_VEICULO_ID, operation));
				}
		}
}
