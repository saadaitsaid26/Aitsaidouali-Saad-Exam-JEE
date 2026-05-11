package ma.enset.aitsaidouali.insurance.dtos;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.aitsaidouali.insurance.enums.NiveauCouverture;

@Data
@NoArgsConstructor
public class ContratSanteRequestDTO {

    private LocalDate datesouscription;
    private double montantCotisation;
    private int duree;
    private double tauxCouverture;
    private Long clientId;

    private NiveauCouverture niveauCouverture;
    private int nbPersonnesCouvertes;
}
