package ma.enset.aitsaidouali.insurance.dtos;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.aitsaidouali.insurance.enums.TypeLogement;

@Data
@NoArgsConstructor
public class ContratHabitationRequestDTO {

    private LocalDate datesouscription;
    private double montantCotisation;
    private int duree;
    private double tauxCouverture;
    private Long clientId;

    private TypeLogement typeLogement;
    private String adresseLogement;
    private double superficie;
}
