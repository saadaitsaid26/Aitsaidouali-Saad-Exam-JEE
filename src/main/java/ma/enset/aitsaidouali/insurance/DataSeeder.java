package ma.enset.aitsaidouali.insurance;

import java.time.LocalDate;
import java.util.List;
import ma.enset.aitsaidouali.insurance.entities.Client;
import ma.enset.aitsaidouali.insurance.entities.ContratAssurance;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceAuto;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceHabitation;
import ma.enset.aitsaidouali.insurance.entities.ContratAssuranceSante;
import ma.enset.aitsaidouali.insurance.entities.Paiement;
import ma.enset.aitsaidouali.insurance.enums.NiveauCouverture;
import ma.enset.aitsaidouali.insurance.enums.StatutContrat;
import ma.enset.aitsaidouali.insurance.enums.TypeLogement;
import ma.enset.aitsaidouali.insurance.enums.TypePaiement;
import ma.enset.aitsaidouali.insurance.repositories.ClientRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceAutoRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceHabitationRepository;
import ma.enset.aitsaidouali.insurance.repositories.ContratAssuranceSanteRepository;
import ma.enset.aitsaidouali.insurance.repositories.PaiementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final ContratAssuranceAutoRepository autoRepository;
    private final ContratAssuranceHabitationRepository habitationRepository;
    private final ContratAssuranceSanteRepository santeRepository;
    private final PaiementRepository paiementRepository;

    public DataSeeder(
            ClientRepository clientRepository,
            ContratAssuranceAutoRepository autoRepository,
            ContratAssuranceHabitationRepository habitationRepository,
            ContratAssuranceSanteRepository santeRepository,
            PaiementRepository paiementRepository) {
        this.clientRepository = clientRepository;
        this.autoRepository = autoRepository;
        this.habitationRepository = habitationRepository;
        this.santeRepository = santeRepository;
        this.paiementRepository = paiementRepository;
    }

    @Override
    public void run(String... args) {
        if (clientRepository.count() > 0
                || autoRepository.count() > 0
                || habitationRepository.count() > 0
                || santeRepository.count() > 0) {
            printSummary();
            return;
        }

        List<Client> clients = clientRepository.saveAll(
                List.of(
                        Client.builder()
                                .nom("Jean Dupont")
                                .email("jean.dupont@example.com")
                                .build(),
                        Client.builder()
                                .nom("Claire Martin")
                                .email("claire.martin@example.com")
                                .build(),
                        Client.builder()
                                .nom("Sophie Bernard")
                                .email("sophie.bernard@example.com")
                                .build(),
                        Client.builder()
                                .nom("Luc Moreau")
                                .email("luc.moreau@example.com")
                                .build(),
                        Client.builder()
                                .nom("Nadia Benali")
                                .email("nadia.benali@example.com")
                                .build()));

        ContratAssuranceAuto auto1 = new ContratAssuranceAuto();
        auto1.setDatesouscription(LocalDate.now().minusMonths(6));
        auto1.setStatut(StatutContrat.EN_COURS);
        auto1.setDateValidation(LocalDate.now().minusMonths(5));
        auto1.setMontantCotisation(1200);
        auto1.setDuree(12);
        auto1.setTauxCouverture(0.7);
        auto1.setClient(clients.get(0));
        auto1.setNumeroImmatriculation("AA-123-BB");
        auto1.setMarqueVehicule("Peugeot");
        auto1.setModeleVehicule("208");
        auto1 = autoRepository.save(auto1);
        addPaiements(auto1, 120, 1200);

        ContratAssuranceAuto auto2 = new ContratAssuranceAuto();
        auto2.setDatesouscription(LocalDate.now().minusMonths(10));
        auto2.setStatut(StatutContrat.VALIDE);
        auto2.setDateValidation(LocalDate.now().minusMonths(9));
        auto2.setMontantCotisation(1500);
        auto2.setDuree(12);
        auto2.setTauxCouverture(0.8);
        auto2.setClient(clients.get(1));
        auto2.setNumeroImmatriculation("BB-456-CC");
        auto2.setMarqueVehicule("Renault");
        auto2.setModeleVehicule("Clio");
        auto2 = autoRepository.save(auto2);
        addPaiements(auto2, 150, 1500);

        ContratAssuranceAuto auto3 = new ContratAssuranceAuto();
        auto3.setDatesouscription(LocalDate.now().minusMonths(18));
        auto3.setStatut(StatutContrat.RESILIE);
        auto3.setDateValidation(LocalDate.now().minusMonths(17));
        auto3.setMontantCotisation(1000);
        auto3.setDuree(12);
        auto3.setTauxCouverture(0.6);
        auto3.setClient(clients.get(2));
        auto3.setNumeroImmatriculation("CC-789-DD");
        auto3.setMarqueVehicule("Citroen");
        auto3.setModeleVehicule("C3");
        auto3 = autoRepository.save(auto3);
        addPaiements(auto3, 100, 1000);

        ContratAssuranceHabitation hab1 = new ContratAssuranceHabitation();
        hab1.setDatesouscription(LocalDate.now().minusMonths(4));
        hab1.setStatut(StatutContrat.EN_COURS);
        hab1.setDateValidation(LocalDate.now().minusMonths(3));
        hab1.setMontantCotisation(900);
        hab1.setDuree(12);
        hab1.setTauxCouverture(0.75);
        hab1.setClient(clients.get(0));
        hab1.setTypeLogement(TypeLogement.APPARTEMENT);
        hab1.setAdresseLogement("12 Rue Lafayette, Lyon");
        hab1.setSuperficie(65);
        hab1 = habitationRepository.save(hab1);
        addPaiements(hab1, 90, 900);

        ContratAssuranceHabitation hab2 = new ContratAssuranceHabitation();
        hab2.setDatesouscription(LocalDate.now().minusMonths(8));
        hab2.setStatut(StatutContrat.VALIDE);
        hab2.setDateValidation(LocalDate.now().minusMonths(7));
        hab2.setMontantCotisation(1100);
        hab2.setDuree(12);
        hab2.setTauxCouverture(0.8);
        hab2.setClient(clients.get(3));
        hab2.setTypeLogement(TypeLogement.MAISON);
        hab2.setAdresseLogement("5 Avenue Victor Hugo, Paris");
        hab2.setSuperficie(120);
        hab2 = habitationRepository.save(hab2);
        addPaiements(hab2, 110, 1100);

        ContratAssuranceHabitation hab3 = new ContratAssuranceHabitation();
        hab3.setDatesouscription(LocalDate.now().minusMonths(14));
        hab3.setStatut(StatutContrat.RESILIE);
        hab3.setDateValidation(LocalDate.now().minusMonths(13));
        hab3.setMontantCotisation(800);
        hab3.setDuree(12);
        hab3.setTauxCouverture(0.65);
        hab3.setClient(clients.get(4));
        hab3.setTypeLogement(TypeLogement.APPARTEMENT);
        hab3.setAdresseLogement("22 Boulevard des Belges, Nantes");
        hab3.setSuperficie(55);
        hab3 = habitationRepository.save(hab3);
        addPaiements(hab3, 80, 800);

        ContratAssuranceSante sante1 = new ContratAssuranceSante();
        sante1.setDatesouscription(LocalDate.now().minusMonths(5));
        sante1.setStatut(StatutContrat.EN_COURS);
        sante1.setDateValidation(LocalDate.now().minusMonths(4));
        sante1.setMontantCotisation(600);
        sante1.setDuree(12);
        sante1.setTauxCouverture(0.7);
        sante1.setClient(clients.get(1));
        sante1.setNiveauCouverture(NiveauCouverture.BASIQUE);
        sante1.setNbPersonnesCouvertes(2);
        sante1 = santeRepository.save(sante1);
        addPaiements(sante1, 60, 600);

        ContratAssuranceSante sante2 = new ContratAssuranceSante();
        sante2.setDatesouscription(LocalDate.now().minusMonths(9));
        sante2.setStatut(StatutContrat.VALIDE);
        sante2.setDateValidation(LocalDate.now().minusMonths(8));
        sante2.setMontantCotisation(900);
        sante2.setDuree(12);
        sante2.setTauxCouverture(0.85);
        sante2.setClient(clients.get(2));
        sante2.setNiveauCouverture(NiveauCouverture.PREMIUM);
        sante2.setNbPersonnesCouvertes(4);
        sante2 = santeRepository.save(sante2);
        addPaiements(sante2, 90, 900);

        printSummary();
    }

    private void addPaiements(ContratAssurance contrat, double mensualite, double annuel) {
        paiementRepository.save(
                Paiement.builder()
                        .datePaiement(LocalDate.now().minusMonths(1))
                        .montant(mensualite)
                        .typePaiement(TypePaiement.MENSUALITE)
                        .contrat(contrat)
                        .build());
        paiementRepository.save(
                Paiement.builder()
                        .datePaiement(LocalDate.now().minusDays(10))
                        .montant(annuel)
                        .typePaiement(TypePaiement.ANNUEL)
                        .contrat(contrat)
                        .build());
    }

    private void printSummary() {
        long totalContracts = autoRepository.count()
                + habitationRepository.count()
                + santeRepository.count();
        System.out.println("Seed summary:");
        System.out.println("- Clients: " + clientRepository.count());
        System.out.println("- Contrats Auto: " + autoRepository.count());
        System.out.println("- Contrats Habitation: " + habitationRepository.count());
        System.out.println("- Contrats Sante: " + santeRepository.count());
        System.out.println("- Total Contrats: " + totalContracts);
        System.out.println("- Paiements: " + paiementRepository.count());
    }
}
