package ui;

import dao.ClientDAOImpl;
import dao.CompteDAOImpl;
import dao.TransactionDAOImpl;
import entity.Client;
import entity.CompteCourant;
import entity.CompteEpargne;
import entity.Transaction;
import enums.TypeTransaction;
import service.ClientServiceImpl;
import service.CompteServiceImpl;
import service.TransactionServiceImpl;
import service.RapportService;
import util.ValidationUtil;

import java.time.LocalDate;
import java.util.Optional;

public class MainUI {

    private final ClientServiceImpl clientService;
    private final CompteServiceImpl compteService;
    private final TransactionServiceImpl transactionService;
    private final RapportService rapportService;

    public MainUI() {
        // Initialisation DAO + Services
        this.clientService = new ClientServiceImpl(new ClientDAOImpl());
        this.compteService = new CompteServiceImpl(new CompteDAOImpl());
        this.transactionService = new TransactionServiceImpl(new TransactionDAOImpl());
        this.rapportService = new RapportService(new ClientDAOImpl(), new CompteDAOImpl(), new TransactionDAOImpl());
    }

    public void demarrer() {
        int choix;
        do {
            System.out.println("\n===== BANQUE APP =====");
            System.out.println("1. Gérer Clients");
            System.out.println("2. Gérer Comptes");
            System.out.println("3. Gérer Transactions");
            System.out.println("4. Rapports");
            System.out.println("0. Quitter");

            choix = ValidationUtil.lireInt("Choix : ");

            switch (choix) {
                case 1 -> menuClients();
                case 2 -> menuComptes();
                case 3 -> menuTransactions();
                case 4 -> menuRapports();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }

    // === Menu Clients ===
    private void menuClients() {
        System.out.println("\n--- Gestion Clients ---");
        System.out.println("1. Ajouter client");
        System.out.println("2. Modifier client");
        System.out.println("3. Supprimer client");
        System.out.println("4. Lister clients");
        System.out.println("5. Rechercher client par nom");
        System.out.println("0. Retour au menu principal");

        int choix = ValidationUtil.lireInt("Choix : ");

        switch (choix) {
            case 1 -> {
                int id = ValidationUtil.lireInt("ID : ");
                String nom = ValidationUtil.lireString("Nom : ");
                String email = ValidationUtil.lireEmail("Email : ");
                clientService.ajouterClient(new Client(id, nom, email));
                System.out.println("✅ Client ajouté !");
            }
            case 2 -> {
                int id = ValidationUtil.lireInt("ID du client à modifier : ");
                Optional<Client> c = clientService.rechercherClientParId(id);
                if (c.isPresent()) {
                    String nom = ValidationUtil.lireString("Nouveau nom : ");
                    String email = ValidationUtil.lireEmail("Nouveau email : ");
                    clientService.modifierClient(new Client(id, nom, email));
                    System.out.println("✅ Client modifié !");
                } else {
                    System.out.println("❌ Client introuvable.");
                }
            }
            case 3 -> {
                int id = ValidationUtil.lireInt("ID du client à supprimer : ");
                clientService.supprimerClient(id);
                System.out.println("✅ Client supprimé !");
            }
            case 4 -> {
                System.out.println("\n--- Liste des clients ---");
                clientService.listerTousClients().forEach(System.out::println);
            }
            case 5 -> {
                String nom = ValidationUtil.lireString("Nom à rechercher : ");
                var clients = clientService.rechercherClientsParNom(nom);
                if (clients.isEmpty()) {
                    System.out.println("❌ Aucun client trouvé avec ce nom.");
                } else {
                    clients.forEach(System.out::println);
                }
            }
            case 0 ->{
                return;
            }
            default -> System.out.println("Choix invalide !");
        }
    }

    // === Menu Comptes ===
    private void menuComptes() {
        System.out.println("\n--- Gestion Comptes ---");
        System.out.println("1. Créer compte courant");
        System.out.println("2. Créer compte épargne");
        System.out.println("3. Lister comptes par client");
        System.out.println("4. Rechercher compte par numéro");
        System.out.println("5. Compte avec solde maximum");
        System.out.println("6. Compte avec solde minimum");
        System.out.println("0. Retour au menu principal");

        int choix = ValidationUtil.lireInt("Choix : ");

        switch (choix) {
            case 1 -> {
                int id = ValidationUtil.lireInt("ID : ");
                String numero = ValidationUtil.lireString("Numéro : ");
                double solde = ValidationUtil.lireDouble("Solde : ");
                int idClient = ValidationUtil.lireInt("ID Client : ");
                double decouvert = ValidationUtil.lireDouble("Découvert autorisé : ");
                compteService.creerCompte(new CompteCourant(id, numero, solde, idClient, decouvert));
                System.out.println("✅ Compte courant créé !");
            }
            case 2 -> {
                int id = ValidationUtil.lireInt("ID : ");
                String numero = ValidationUtil.lireString("Numéro : ");
                double solde = ValidationUtil.lireDouble("Solde : ");
                int idClient = ValidationUtil.lireInt("ID Client : ");
                double taux = ValidationUtil.lireDouble("Taux intérêt : ");
                compteService.creerCompte(new CompteEpargne(id, numero, solde, idClient, taux));
                System.out.println("✅ Compte épargne créé !");
            }
            case 3 -> {
                int idClient = ValidationUtil.lireInt("ID Client : ");
                var comptes = compteService.rechercherComptesParClient(idClient);
                if (comptes.isEmpty()) {
                    System.out.println("❌ Aucun compte trouvé pour ce client.");
                } else {
                    comptes.forEach(System.out::println);
                }
            }
            case 4 -> {
                String numero = ValidationUtil.lireString("Numéro du compte : ");
                Optional<entity.Compte> compte = compteService.rechercherCompteParNumero(numero);
                if (compte.isPresent()) {
                    System.out.println(compte.get());
                } else {
                    System.out.println("❌ Compte introuvable.");
                }
            }
            case 5 -> {
                var compte = compteService.compteAvecSoldeMax();
                if (compte != null) {
                    System.out.println("Compte avec solde max : " + compte);
                } else {
                    System.out.println("❌ Aucun compte trouvé.");
                }
            }
            case 6 -> {
                var compte = compteService.compteAvecSoldeMin();
                if (compte != null) {
                    System.out.println("Compte avec solde min : " + compte);
                } else {
                    System.out.println("❌ Aucun compte trouvé.");
                }
            }
            case 0 ->{
                return;
            }
            default -> System.out.println("Choix invalide !");
        }
    }

    // === Menu Transactions ===
    private void menuTransactions() {
        System.out.println("\n--- Gestion Transactions ---");
        System.out.println("1. Enregistrer transaction");
        System.out.println("2. Lister transactions par compte");
        System.out.println("3. Filtrer transactions");
        System.out.println("0. Retour au menu principal");

        int choix = ValidationUtil.lireInt("Choix : ");

        switch (choix) {
            case 1 -> {
                int id = ValidationUtil.lireInt("ID : ");
                double montant = ValidationUtil.lireDouble("Montant : ");
                String type = ValidationUtil.lireTypeTransaction("Type (DEPOT/RETRAIT) : ");
                String lieu = ValidationUtil.lireString("Lieu : ");
                int idCompte = ValidationUtil.lireInt("ID Compte : ");
                LocalDate date = LocalDate.now();
                Transaction t = new Transaction(id, date, montant, TypeTransaction.valueOf(type.toUpperCase()), lieu, idCompte);
                transactionService.enregistrerTransaction(t);
                System.out.println("✅ Transaction enregistrée !");
            }
            case 2 -> {
                int idCompte = ValidationUtil.lireInt("ID Compte : ");
                var transactions = transactionService.listerTransactionsParCompte(idCompte);
                if (transactions.isEmpty()) {
                    System.out.println("❌ Aucune transaction trouvée pour ce compte.");
                } else {
                    transactions.forEach(System.out::println);
                }
            }
            case 3 -> {
                int idCompte = ValidationUtil.lireInt("ID Compte : ");
                System.out.println("Laissez vide pour ne pas filtrer sur un critère");

                String montantMinStr = ValidationUtil.lireString("Montant minimum (ou vide) : ");
                Double montantMin = montantMinStr.isEmpty() ? null : Double.parseDouble(montantMinStr);

                String montantMaxStr = ValidationUtil.lireString("Montant maximum (ou vide) : ");
                Double montantMax = montantMaxStr.isEmpty() ? null : Double.parseDouble(montantMaxStr);

                var transactions = transactionService.filtrerTransactions(idCompte, montantMin, montantMax, null, null, null);
                if (transactions.isEmpty()) {
                    System.out.println("❌ Aucune transaction correspondant aux critères.");
                } else {
                    transactions.forEach(System.out::println);
                }
            }
            case 0 ->{
                return;
            }
            default -> System.out.println("Choix invalide !");
        }
    }

    // === Menu Rapports ===
    private void menuRapports() {
        System.out.println("\n--- Rapports ---");
        System.out.println("1. Top 5 clients");
        System.out.println("2. Rapport mensuel");
        System.out.println("3. Transactions suspectes (>10000)");
        System.out.println("4. Comptes inactifs");
        System.out.println("0. Retour au menu principal");

        int choix = ValidationUtil.lireInt("Choix : ");

        switch (choix) {
            case 1 -> {
                System.out.println("\n--- Top 5 Clients ---");
                rapportService.top5Clients().forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("\n--- Rapport Mensuel ---");
                rapportService.rapportMensuel().forEach((type, count) ->
                        System.out.println(type + " : " + count));
            }
            case 3 -> {
                System.out.println("\n--- Transactions Suspectes ---");
                var transactions = rapportService.detecterTransactionsSuspectes(10000);
                if (transactions.isEmpty()) {
                    System.out.println("✅ Aucune transaction suspecte détectée.");
                } else {
                    transactions.forEach(System.out::println);
                }
            }
            case 4 -> {
                System.out.println("\n--- Comptes Inactifs ---");
                LocalDate depuis = LocalDate.now().minusMonths(6);
                var comptes = rapportService.comptesInactifs(depuis);
                if (comptes.isEmpty()) {
                    System.out.println("✅ Aucun compte inactif.");
                } else {
                    comptes.forEach(System.out::println);
                }
            }
            case 0 -> {
                return;
            }
            default -> System.out.println("Choix invalide !");
        }
    }
}