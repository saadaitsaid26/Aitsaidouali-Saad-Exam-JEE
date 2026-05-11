package ma.enset.aitsaidouali.insurance.dtos;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContratAutoRequestDTO {

    private LocalDate datesouscription;
    private double montantCotisation;
    private int duree;
    private double tauxCouverture;
    private Long clientId;

    private String numeroImmatriculation;
    private String marqueVehicule;
    private String modeleVehicule;
}
