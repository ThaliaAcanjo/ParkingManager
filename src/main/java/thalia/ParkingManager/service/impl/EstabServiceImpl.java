package thalia.ParkingManager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thalia.ParkingManager.exceptionhandler.BadRequestException;
import thalia.ParkingManager.exceptionhandler.NotFoundException;
import thalia.ParkingManager.model.Estabelecimento;
import thalia.ParkingManager.model.Veiculo;
import thalia.ParkingManager.repository.EstabRepository;
import thalia.ParkingManager.service.EstabService;
import thalia.ParkingManager.service.exception.BusinessException;

import java.util.List;

@Service
public class EstabServiceImpl implements EstabService {
		private static final Long UNCHANGEABLE_VEICULO_ID = 0L;
		private final EstabRepository estabRepository;

		public EstabServiceImpl(EstabRepository estabRepository){
				this.estabRepository = estabRepository;
		}
		@Transactional(readOnly = true)
		public List<Estabelecimento> findAll() {
				return this.estabRepository.findAll();
		}

		@Transactional(readOnly = true)
		public Estabelecimento findById(Long id) {
				return this.estabRepository.findById(id).orElseThrow(NotFoundException::new);
		}

		@Transactional
		public Estabelecimento create(Estabelecimento estabelecimento) {
				if (estabelecimento.getNome() == null)
						throw new BadRequestException("O campo Nome é obrigatório");
				if (estabelecimento.getCnpj() == null)
						throw new BadRequestException("O campo CNPJ é obrigatório");
				if (estabelecimento.getTelefone() == null)
						throw new BadRequestException("O campo telefone é obrigatório");
				if (estabelecimento.getEndereco() == null)
						throw new BadRequestException("O campo endereço é obrigatório");
				if (estabelecimento.getVagasPorMotos() == null)
						throw new BadRequestException("O campo vagas por carro é obrigatório");
				if (estabelecimento.getVagasPorCarros() == null)
						throw new BadRequestException("O campo vagas por moto é obrigatório");
				if (estabelecimento.getValorHora() == null)
						throw new BadRequestException("O campo valor por hora é obrigatório");
				this.validateChangeableId(estabelecimento.getId(), "created");
				return this.estabRepository.save(estabelecimento);
		}

		@Transactional
		public Estabelecimento update(Long id, Estabelecimento estabelecimento) {
				this.validateChangeableId(id, "updated");
				Estabelecimento estabUpdate = this.findById(id);
				if (!estabUpdate.getId().equals(estabelecimento.getId())) {
						throw new BadRequestException("update IDs must be the same.");
				}
				return estabRepository.save(estabelecimento);
		}

		@Override
		public void delete(Long id) {
			this.validateChangeableId(id, "deleted");
			Estabelecimento estabelecimento = this.findById(id);
			estabRepository.delete(estabelecimento);
		}

		private void validateChangeableId(Long id, String operation) throws BusinessException {
				if (UNCHANGEABLE_VEICULO_ID.equals(id)) {
						throw new BusinessException("Estabelecimento %d não pode ser %s.".formatted(UNCHANGEABLE_VEICULO_ID, operation));
				}
		}
}
