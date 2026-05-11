package ma.enset.aitsaidouali.insurance.services;

import java.util.List;
import java.util.Map;
import ma.enset.aitsaidouali.insurance.dtos.ClientRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ClientResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratAutoRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratHabitationRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratSanteRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.PaiementRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.PaiementResponseDTO;
import ma.enset.aitsaidouali.insurance.enums.StatutContrat;
import org.springframework.data.domain.Page;

public interface InsuranceService {

    // Client operations
    ClientResponseDTO saveClient(ClientRequestDTO dto);

    ClientResponseDTO updateClient(Long id, ClientRequestDTO dto);

    void deleteClient(Long id);

    ClientResponseDTO getClient(Long id);

    Page<ClientResponseDTO> searchClients(String keyword, int page, int size);

    // Contract operations
    ContratResponseDTO saveContratAuto(ContratAutoRequestDTO dto);

    ContratResponseDTO saveContratHabitation(ContratHabitationRequestDTO dto);

    ContratResponseDTO saveContratSante(ContratSanteRequestDTO dto);

    ContratResponseDTO updateStatut(Long contratId, StatutContrat statut);

    void deleteContrat(Long id);

    ContratResponseDTO getContrat(Long id);

    List<ContratResponseDTO> getContratsForClient(Long clientId);

    Page<ContratResponseDTO> getAllContrats(int page, int size);

    // Payment operations
    PaiementResponseDTO addPaiement(PaiementRequestDTO dto);

    List<PaiementResponseDTO> getPaiementsForContrat(Long contratId);

    void deletePaiement(Long id);

    // Dashboard stats
    Map<String, Long> getContractCountByType();

    Map<String, Long> getContractCountByStatut();
}
