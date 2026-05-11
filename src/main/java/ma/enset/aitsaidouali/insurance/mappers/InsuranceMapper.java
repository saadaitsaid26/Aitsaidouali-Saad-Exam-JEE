package ma.enset.aitsaidouali.insurance.mappers;

import ma.enset.aitsaidouali.insurance.dtos.ClientRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ClientResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratAutoRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratAutoResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratHabitationRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratHabitationResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratSanteRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratSanteResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.PaiementRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.PaiementResponseDTO;
import ma.enset.aitsaidouali.insurance.entities.Client;
import ma.enset.aitsaidouali.insurance.entities.ContratAssurance;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceAuto;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceHabitation;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceSante;
import ma.enset.aitsaidouali.insurance.entities.Paiement;
import org.springframework.stereotype.Component;

@Component
public class InsuranceMapper {

    public Client toClient(ClientRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Client client = new Client();
        client.setNom(dto.getNom());
        client.setEmail(dto.getEmail());
        return client;
    }

    public ClientResponseDTO toClientResponseDTO(Client client) {
        if (client == null) {
            return null;
        }
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setEmail(client.getEmail());
        dto.setNombreContrats(client.getContrats() == null ? 0 : client.getContrats().size());
        return dto;
    }

    public ContratAssuranceAuto toAuto(ContratAutoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ContratAssuranceAuto entity = new ContratAssuranceAuto();
        applyBaseRequestFields(entity, dto.getDatesouscription(), dto.getMontantCotisation(), dto.getDuree(),
                dto.getTauxCouverture(), dto.getClientId());
        entity.setNumeroImmatriculation(dto.getNumeroImmatriculation());
        entity.setMarqueVehicule(dto.getMarqueVehicule());
        entity.setModeleVehicule(dto.getModeleVehicule());
        return entity;
    }

    public ContratAutoResponseDTO toAutoDTO(ContratAssuranceAuto entity) {
        if (entity == null) {
            return null;
        }
        ContratAutoResponseDTO dto = new ContratAutoResponseDTO();
        applyBaseResponseFields(entity, dto, "AUTO");
        dto.setNumeroImmatriculation(entity.getNumeroImmatriculation());
        dto.setMarqueVehicule(entity.getMarqueVehicule());
        dto.setModeleVehicule(entity.getModeleVehicule());
        return dto;
    }

    public ContratAssuranceHabitation toHabitation(ContratHabitationRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ContratAssuranceHabitation entity = new ContratAssuranceHabitation();
        applyBaseRequestFields(entity, dto.getDatesouscription(), dto.getMontantCotisation(), dto.getDuree(),
                dto.getTauxCouverture(), dto.getClientId());
        entity.setTypeLogement(dto.getTypeLogement());
        entity.setAdresseLogement(dto.getAdresseLogement());
        entity.setSuperficie(dto.getSuperficie());
        return entity;
    }

    public ContratHabitationResponseDTO toHabitationDTO(ContratAssuranceHabitation entity) {
        if (entity == null) {
            return null;
        }
        ContratHabitationResponseDTO dto = new ContratHabitationResponseDTO();
        applyBaseResponseFields(entity, dto, "HABITATION");
        dto.setTypeLogement(entity.getTypeLogement());
        dto.setAdresseLogement(entity.getAdresseLogement());
        dto.setSuperficie(entity.getSuperficie());
        return dto;
    }

    public ContratAssuranceSante toSante(ContratSanteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ContratAssuranceSante entity = new ContratAssuranceSante();
        applyBaseRequestFields(entity, dto.getDatesouscription(), dto.getMontantCotisation(), dto.getDuree(),
                dto.getTauxCouverture(), dto.getClientId());
        entity.setNiveauCouverture(dto.getNiveauCouverture());
        entity.setNbPersonnesCouvertes(dto.getNbPersonnesCouvertes());
        return entity;
    }

    public ContratSanteResponseDTO toSanteDTO(ContratAssuranceSante entity) {
        if (entity == null) {
            return null;
        }
        ContratSanteResponseDTO dto = new ContratSanteResponseDTO();
        applyBaseResponseFields(entity, dto, "SANTE");
        dto.setNiveauCouverture(entity.getNiveauCouverture());
        dto.setNbPersonnesCouvertes(entity.getNbPersonnesCouvertes());
        return dto;
    }

    public Paiement toPaiement(PaiementRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Paiement paiement = new Paiement();
        paiement.setDatePaiement(dto.getDatePaiement());
        paiement.setMontant(dto.getMontant());
        paiement.setTypePaiement(dto.getTypePaiement());
        return paiement;
    }

    public PaiementResponseDTO toPaiementDTO(Paiement paiement) {
        if (paiement == null) {
            return null;
        }
        PaiementResponseDTO dto = new PaiementResponseDTO();
        dto.setId(paiement.getId());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setMontant(paiement.getMontant());
        dto.setTypePaiement(paiement.getTypePaiement());
        dto.setContratId(paiement.getContrat() == null ? null : paiement.getContrat().getId());
        return dto;
    }

    private void applyBaseRequestFields(ContratAssurance entity, java.time.LocalDate datesouscription,
            double montantCotisation, int duree, double tauxCouverture, Long clientId) {
        entity.setDatesouscription(datesouscription);
        entity.setMontantCotisation(montantCotisation);
        entity.setDuree(duree);
        entity.setTauxCouverture(tauxCouverture);
        entity.setClient(buildClientReference(clientId));
    }

    private void applyBaseResponseFields(ContratAssurance entity, ContratResponseDTO dto, String type) {
        dto.setId(entity.getId());
        dto.setType(type);
        dto.setDatesouscription(entity.getDatesouscription());
        dto.setStatut(entity.getStatut());
        dto.setMontantCotisation(entity.getMontantCotisation());
        dto.setDuree(entity.getDuree());
        dto.setTauxCouverture(entity.getTauxCouverture());
        dto.setDateValidation(entity.getDateValidation());
        if (entity.getClient() != null) {
            dto.setClientNom(entity.getClient().getNom());
            dto.setClientEmail(entity.getClient().getEmail());
        }
        dto.setNombrePaiements(entity.getPaiements() == null ? 0 : entity.getPaiements().size());
    }

    private Client buildClientReference(Long clientId) {
        if (clientId == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientId);
        return client;
    }
}
