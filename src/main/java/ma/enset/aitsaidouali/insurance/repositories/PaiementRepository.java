package ma.enset.aitsaidouali.insurance.repositories;

import java.time.LocalDate;
import java.util.List;
import ma.enset.aitsaidouali.insurance.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByContratId(Long contratId);

    List<Paiement> findByDatePaiementBetween(LocalDate start, LocalDate end);

    @Query("select coalesce(sum(p.montant), 0) from Paiement p where p.contrat.id = :contratId")
    Double sumMontantByContratId(@Param("contratId") Long contratId);
}
