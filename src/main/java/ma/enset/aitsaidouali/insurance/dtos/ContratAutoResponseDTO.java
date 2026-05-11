package ma.enset.aitsaidouali.insurance.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratAutoResponseDTO extends ContratResponseDTO {

    private String numeroImmatriculation;
    private String marqueVehicule;
    private String modeleVehicule;
}
