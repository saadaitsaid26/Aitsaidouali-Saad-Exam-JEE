package ma.enset.aitsaidouali.insurance.dtos;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.aitsaidouali.insurance.enums.TypePaiement;

@Data
@NoArgsConstructor
public class PaiementRequestDTO {

    private LocalDate datePaiement;
    private double montant;
    private TypePaiement typePaiement;
    private Long contratId;
}
