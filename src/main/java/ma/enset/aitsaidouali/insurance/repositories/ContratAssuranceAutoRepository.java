package ma.enset.aitsaidouali.insurance.repositories;

import java.util.Optional;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceAuto;

public interface ContratAssuranceAutoRepository extends ContratAssuranceRepository<ContratAssuranceAuto> {

    Optional<ContratAssuranceAuto> findByNumeroImmatriculation(String numero);
}
