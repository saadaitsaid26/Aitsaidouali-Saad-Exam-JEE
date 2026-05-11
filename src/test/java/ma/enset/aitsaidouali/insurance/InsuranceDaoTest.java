package ma.enset.aitsaidouali.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceAuto;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceHabitation;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceSante;
import ma.enset.aitsaidouali.insurance.entities.Paiement;
import ma.enset.aitsaidouali.insurance.repositories.ClientRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceAutoRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceHabitationRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceSanteRepository;
import ma.enset.aitsaidouali.insurance.repositories.PaiementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class InsuranceDaoTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContratAssuranceAutoRepository autoRepository;

    @Autowired
    private ContratAssuranceHabitationRepository habitationRepository;

    @Autowired
    private ContratAssuranceSanteRepository santeRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Test
    void clientCountAtLeastFive() {
        long count = clientRepository.count();
        assertTrue(count >= 5, "Expected at least 5 clients");
    }

    @Test
    void findByClientIdReturnsContractsForClientOne() {
        List<ContratAssuranceAuto> autos = autoRepository.findByClientId(1L);
        List<ContratAssuranceHabitation> habitations = habitationRepository.findByClientId(1L);
        List<ContratAssuranceSante> santes = santeRepository.findByClientId(1L);
        int total = autos.size() + habitations.size() + santes.size();
        assertTrue(total > 0, "Expected contracts for client id 1");
    }

    @Test
    void paiementSumCalculationWorks() {
        ContratAssuranceAuto contrat = autoRepository.findAll().get(0);
        List<Paiement> paiements = paiementRepository.findByContratId(contrat.getId());
        double expected = paiements.stream().mapToDouble(Paiement::getMontant).sum();
        Double actual = paiementRepository.sumMontantByContratId(contrat.getId());
        assertEquals(expected, actual, 0.001);
    }
}
