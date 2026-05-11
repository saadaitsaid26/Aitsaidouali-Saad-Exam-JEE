package ma.enset.aitsaidouali.insurance.repositories;

import java.util.List;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceHabitation;
import ma.enset.aitsaidouali.insurance.enums.TypeLogement;

public interface ContratAssuranceHabitationRepository
        extends ContratAssuranceRepository<ContratAssuranceHabitation> {

    List<ContratAssuranceHabitation> findByTypeLogement(TypeLogement type);
}
