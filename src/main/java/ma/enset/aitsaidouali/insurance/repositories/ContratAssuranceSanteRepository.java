package ma.enset.aitsaidouali.insurance.repositories;

import java.util.List;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceSante;
import ma.enset.aitsaidouali.insurance.enums.NiveauCouverture;

public interface ContratAssuranceSanteRepository
        extends ContratAssuranceRepository<ContratAssuranceSante> {

    List<ContratAssuranceSante> findByNiveauCouverture(NiveauCouverture niveau);
}
