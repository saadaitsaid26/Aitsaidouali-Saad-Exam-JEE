package ma.enset.aitsaidouali.insurance.repositories;

import java.util.Optional;
import ma.enset.aitsaidouali.insurance.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    Page<Client> findByNomContainingIgnoreCase(String nom, Pageable pageable);
}
