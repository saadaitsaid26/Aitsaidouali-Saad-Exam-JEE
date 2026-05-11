package ma.enset.aitsaidouali.insurance.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.aitsaidouali.insurance.enums.TypeLogement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("HABITATION")
public class ContratAssuranceHabitation extends ContratAssurance {

    @Enumerated(EnumType.STRING)
    private TypeLogement typeLogement;

    private String adresseLogement;
    private double superficie;
}
