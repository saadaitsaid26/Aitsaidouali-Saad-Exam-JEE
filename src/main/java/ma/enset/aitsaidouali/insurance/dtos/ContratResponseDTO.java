package ma.enset.aitsaidouali.insurance.dtos;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.aitsaidouali.insurance.enums.StatutContrat;

@Data
@NoArgsConstructor
public class ContratResponseDTO {

    private Long id;
    private String type;
    private LocalDate datesouscription;
    private StatutContrat statut;
    private double montantCotisation;
    private int duree;
    private double tauxCouverture;
    private LocalDate dateValidation;
    private String clientNom;
    private String clientEmail;
    private int nombrePaiements;
}
