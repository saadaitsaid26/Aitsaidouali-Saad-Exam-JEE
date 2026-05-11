package ma.enset.aitsaidouali.insurance.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("AUTO")
public class ContratAssuranceAuto extends ContratAssurance {

    private String numeroImmatriculation;
    private String marqueVehicule;
    private String modeleVehicule;
}
