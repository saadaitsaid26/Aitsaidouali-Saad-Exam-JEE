package ma.enset.aitsaidouali.insurance.repositories;

import java.time.LocalDate;
import java.util.List;
import ma.enset.aitsaidouali.insurance.entities.ContratAssurance;
import ma.enset.aitsaidouali.insurance.enums.StatutContrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContratAssuranceRepository<T extends ContratAssurance> extends JpaRepository<T, Long> {

    List<T> findByClientId(Long clientId);

    Page<T> findByStatut(StatutContrat statut, Pageable pageable);

    List<T> findByClientIdAndStatut(Long clientId, StatutContrat statut);

    @Query("select c from #{#entityName} c where c.dateValidation < :limitDate")
    List<T> findExpiringBefore(@Param("limitDate") LocalDate limitDate);
}
