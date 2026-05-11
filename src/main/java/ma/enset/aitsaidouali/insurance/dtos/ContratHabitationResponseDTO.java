package ma.enset.aitsaidouali.insurance.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ma.enset.aitsaidouali.insurance.enums.TypeLogement;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratHabitationResponseDTO extends ContratResponseDTO {

    private TypeLogement typeLogement;
    private String adresseLogement;
    private double superficie;
}
