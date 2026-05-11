package ma.enset.aitsaidouali.insurance.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ma.enset.aitsaidouali.insurance.dtos.ClientRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ClientResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratAutoRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratHabitationRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratSanteRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.PaiementRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.PaiementResponseDTO;
import ma.enset.aitsaidouali.insurance.entities.Client;
import ma.enset.aitsaidouali.insurance.entities.ContratAssurance;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceAuto;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceHabitation;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceSante;
import ma.enset.aitsaidouali.insurance.entities.Paiement;
import ma.enset.aitsaidouali.insurance.enums.StatutContrat;
import ma.enset.aitsaidouali.insurance.exceptions.ResourceNotFoundException;
import ma.enset.aitsaidouali.insurance.mappers.InsuranceMapper;
import ma.enset.aitsaidouali.insurance.repositories.ClientRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceAutoRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceHabitationRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceSanteRepository;
import ma.enset.aitsaidouali.insurance.repositories.PaiementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InsuranceServiceImpl implements InsuranceService {

    private final ClientRepository clientRepository;
    private final ContratAssuranceAutoRepository autoRepository;
    private final ContratAssuranceHabitationRepository habitationRepository;
    private final ContratAssuranceSanteRepository santeRepository;
    private final PaiementRepository paiementRepository;
    private final InsuranceMapper mapper;

    public InsuranceServiceImpl(
            ClientRepository clientRepository,
            ContratAssuranceAutoRepository autoRepository,
            ContratAssuranceHabitationRepository habitationRepository,
            ContratAssuranceSanteRepository santeRepository,
            PaiementRepository paiementRepository,
            InsuranceMapper mapper) {
        this.clientRepository = clientRepository;
        this.autoRepository = autoRepository;
        this.habitationRepository = habitationRepository;
        this.santeRepository = santeRepository;
        this.paiementRepository = paiementRepository;
        this.mapper = mapper;
    }

    @Override
    public ClientResponseDTO saveClient(ClientRequestDTO dto) {
        Client client = mapper.toClient(dto);
        Client saved = clientRepository.save(client);
        return mapper.toClientResponseDTO(saved);
    }

    @Override
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO dto) {
        Client client = getClientEntity(id);
        client.setNom(dto.getNom());
        client.setEmail(dto.getEmail());
        Client saved = clientRepository.save(client);
        return mapper.toClientResponseDTO(saved);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = getClientEntity(id);
        clientRepository.delete(client);
    }

    @Override
    public ClientResponseDTO getClient(Long id) {
        Client client = getClientEntity(id);
        return mapper.toClientResponseDTO(client);
    }

    @Override
    public Page<ClientResponseDTO> searchClients(String keyword, int page, int size) {
        Page<Client> clients = clientRepository.findByNomContainingIgnoreCase(
                keyword,
                PageRequest.of(page, size));
        return clients.map(mapper::toClientResponseDTO);
    }

    @Override
    public ContratResponseDTO saveContratAuto(ContratAutoRequestDTO dto) {
        Client client = getClientEntity(dto.getClientId());
        ContratAssuranceAuto entity = mapper.toAuto(dto);
        entity.setClient(client);
        applyDefaultStatut(entity);
        ContratAssuranceAuto saved = autoRepository.save(entity);
        return mapper.toAutoDTO(saved);
    }

    @Override
    public ContratResponseDTO saveContratHabitation(ContratHabitationRequestDTO dto) {
        Client client = getClientEntity(dto.getClientId());
        ContratAssuranceHabitation entity = mapper.toHabitation(dto);
        entity.setClient(client);
        applyDefaultStatut(entity);
        ContratAssuranceHabitation saved = habitationRepository.save(entity);
        return mapper.toHabitationDTO(saved);
    }

    @Override
    public ContratResponseDTO saveContratSante(ContratSanteRequestDTO dto) {
        Client client = getClientEntity(dto.getClientId());
        ContratAssuranceSante entity = mapper.toSante(dto);
        entity.setClient(client);
        applyDefaultStatut(entity);
        ContratAssuranceSante saved = santeRepository.save(entity);
        return mapper.toSanteDTO(saved);
    }

    @Override
    public ContratResponseDTO updateStatut(Long contratId, StatutContrat statut) {
        ContratAssurance contrat = findContratById(contratId);
        contrat.setStatut(statut);
        if (StatutContrat.VALIDE.equals(statut)) {
            contrat.setDateValidation(LocalDate.now());
        }
        ContratAssurance saved = saveContrat(contrat);
        return mapToResponse(saved);
    }

    @Override
    public void deleteContrat(Long id) {
        ContratAssurance contrat = findContratById(id);
        deleteContratEntity(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public ContratResponseDTO getContrat(Long id) {
        ContratAssurance contrat = findContratById(id);
        return mapToResponse(contrat);
    }

    @Override
    public List<ContratResponseDTO> getContratsForClient(Long clientId) {
        getClientEntity(clientId);
        List<ContratResponseDTO> result = new ArrayList<>();
        result.addAll(autoRepository.findByClientId(clientId).stream()
                .map(mapper::toAutoDTO)
                .collect(Collectors.toList()));
        result.addAll(habitationRepository.findByClientId(clientId).stream()
                .map(mapper::toHabitationDTO)
                .collect(Collectors.toList()));
        result.addAll(santeRepository.findByClientId(clientId).stream()
                .map(mapper::toSanteDTO)
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public Page<ContratResponseDTO> getAllContrats(int page, int size) {
        List<ContratAssurance> contrats = new ArrayList<>();
        contrats.addAll(autoRepository.findAll());
        contrats.addAll(habitationRepository.findAll());
        contrats.addAll(santeRepository.findAll());
        contrats.sort(Comparator.comparing(ContratAssurance::getId, Comparator.nullsLast(Long::compareTo)));

        int start = page * size;
        int end = Math.min(start + size, contrats.size());
        List<ContratResponseDTO> content = start >= contrats.size()
                ? List.of()
                : contrats.subList(start, end).stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(content, PageRequest.of(page, size), contrats.size());
    }

    @Override
    public PaiementResponseDTO addPaiement(PaiementRequestDTO dto) {
        ContratAssurance contrat = findContratById(dto.getContratId());
        Paiement paiement = mapper.toPaiement(dto);
        paiement.setContrat(contrat);
        Paiement saved = paiementRepository.save(paiement);
        return mapper.toPaiementDTO(saved);
    }

    @Override
    public List<PaiementResponseDTO> getPaiementsForContrat(Long contratId) {
        findContratById(contratId);
        return paiementRepository.findByContratId(contratId).stream()
                .map(mapper::toPaiementDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePaiement(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement not found: " + id));
        paiementRepository.delete(paiement);
    }

    @Override
    public Map<String, Long> getContractCountByType() {
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("AUTO", autoRepository.count());
        stats.put("HABITATION", habitationRepository.count());
        stats.put("SANTE", santeRepository.count());
        return stats;
    }

    @Override
    public Map<String, Long> getContractCountByStatut() {
        Map<String, Long> stats = new LinkedHashMap<>();
        for (StatutContrat statut : StatutContrat.values()) {
            stats.put(statut.name(), countByStatut(statut));
        }
        return stats;
    }

    private long countByStatut(StatutContrat statut) {
        Pageable pageable = Pageable.unpaged();
        long total = autoRepository.findByStatut(statut, pageable).getTotalElements();
        total += habitationRepository.findByStatut(statut, pageable).getTotalElements();
        total += santeRepository.findByStatut(statut, pageable).getTotalElements();
        return total;
    }

    private void applyDefaultStatut(ContratAssurance contrat) {
        if (contrat.getStatut() == null) {
            contrat.setStatut(StatutContrat.EN_COURS);
        }
    }

    private Client getClientEntity(Long clientId) {
        if (clientId == null) {
            throw new ResourceNotFoundException("Client id is required");
        }
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + clientId));
    }

    private ContratAssurance findContratById(Long id) {
        if (id == null) {
            throw new ResourceNotFoundException("Contrat id is required");
        }
        ContratAssuranceAuto auto = autoRepository.findById(id).orElse(null);
        if (auto != null) {
            return auto;
        }
        ContratAssuranceHabitation habitation = habitationRepository.findById(id).orElse(null);
        if (habitation != null) {
            return habitation;
        }
        ContratAssuranceSante sante = santeRepository.findById(id).orElse(null);
        if (sante != null) {
            return sante;
        }
        throw new ResourceNotFoundException("Contrat not found: " + id);
    }

    private ContratAssurance saveContrat(ContratAssurance contrat) {
        if (contrat instanceof ContratAssuranceAuto auto) {
            return autoRepository.save(auto);
        }
        if (contrat instanceof ContratAssuranceHabitation habitation) {
            return habitationRepository.save(habitation);
        }
        if (contrat instanceof ContratAssuranceSante sante) {
            return santeRepository.save(sante);
        }
        throw new IllegalArgumentException("Unsupported contrat type: " + contrat.getClass().getSimpleName());
    }

    private void deleteContratEntity(ContratAssurance contrat) {
        if (contrat instanceof ContratAssuranceAuto auto) {
            autoRepository.delete(auto);
            return;
        }
        if (contrat instanceof ContratAssuranceHabitation habitation) {
            habitationRepository.delete(habitation);
            return;
        }
        if (contrat instanceof ContratAssuranceSante sante) {
            santeRepository.delete(sante);
            return;
        }
        throw new IllegalArgumentException("Unsupported contrat type: " + contrat.getClass().getSimpleName());
    }

    private ContratResponseDTO mapToResponse(ContratAssurance contrat) {
        if (contrat instanceof ContratAssuranceAuto auto) {
            return mapper.toAutoDTO(auto);
        }
        if (contrat instanceof ContratAssuranceHabitation habitation) {
            return mapper.toHabitationDTO(habitation);
        }
        if (contrat instanceof ContratAssuranceSante sante) {
            return mapper.toSanteDTO(sante);
        }
        return mapBaseResponse(contrat);
    }

    private ContratResponseDTO mapBaseResponse(ContratAssurance contrat) {
        ContratResponseDTO dto = new ContratResponseDTO();
        dto.setId(contrat.getId());
        dto.setDatesouscription(contrat.getDatesouscription());
        dto.setStatut(contrat.getStatut());
        dto.setMontantCotisation(contrat.getMontantCotisation());
        dto.setDuree(contrat.getDuree());
        dto.setTauxCouverture(contrat.getTauxCouverture());
        dto.setDateValidation(contrat.getDateValidation());
        if (contrat.getClient() != null) {
            dto.setClientNom(contrat.getClient().getNom());
            dto.setClientEmail(contrat.getClient().getEmail());
        }
        dto.setNombrePaiements(contrat.getPaiements() == null ? 0 : contrat.getPaiements().size());
        return dto;
    }
}
