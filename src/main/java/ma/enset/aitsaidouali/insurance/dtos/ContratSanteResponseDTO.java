package ma.enset.aitsaidouali.insurance.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ma.enset.aitsaidouali.insurance.enums.NiveauCouverture;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratSanteResponseDTO extends ContratResponseDTO {

    private NiveauCouverture niveauCouverture;
    private int nbPersonnesCouvertes;
}
