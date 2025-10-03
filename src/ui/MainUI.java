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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainUI {

    private final Scanner scanner = new Scanner(System.in);

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
            System.out.print("Choix : ");

            while (!scanner.hasNextInt()) {
                System.out.print("Veuillez entrer un nombre valide : ");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour chariot

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
        System.out.print("Choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1 -> {
                System.out.print("ID : "); int id = scanner.nextInt(); scanner.nextLine();
                System.out.print("Nom : "); String nom = scanner.nextLine();
                System.out.print("Email : "); String email = scanner.nextLine();
                clientService.ajouterClient(new Client(id, nom, email));
                System.out.println("Client ajouté !");
            }
            case 2 -> {
                System.out.print("ID du client à modifier : "); int id = scanner.nextInt(); scanner.nextLine();
                Optional<Client> c = clientService.rechercherClientParId(id);
                if (c.isPresent()) {
                    System.out.print("Nouveau nom : "); String nom = scanner.nextLine();
                    System.out.print("Nouveau email : "); String email = scanner.nextLine();
                    clientService.modifierClient(new Client(id, nom, email));
                    System.out.println("Client modifié !");
                } else {
                    System.out.println("Client introuvable.");
                }
            }
            case 3 -> {
                System.out.print("ID du client à supprimer : "); int id = scanner.nextInt();
                clientService.supprimerClient(id);
                System.out.println("Client supprimé !");
            }
            case 4 -> clientService.listerTousClients().forEach(System.out::println);
            default -> System.out.println("Choix invalide !");
        }
    }

    // === Menu Comptes ===
    private void menuComptes() {
        System.out.println("\n--- Gestion Comptes ---");
        System.out.println("1. Créer compte courant");
        System.out.println("2. Créer compte épargne");
        System.out.println("3. Lister comptes par client");
        System.out.print("Choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1 -> {
                System.out.print("ID : "); int id = scanner.nextInt(); scanner.nextLine();
                System.out.print("Numéro : "); String numero = scanner.nextLine();
                System.out.print("Solde : "); double solde = scanner.nextDouble();
                System.out.print("ID Client : "); int idClient = scanner.nextInt();
                System.out.print("Découvert autorisé : "); double decouvert = scanner.nextDouble();
                compteService.creerCompte(new CompteCourant(id, numero, solde, idClient, decouvert));
                System.out.println("Compte courant créé !");
            }
            case 2 -> {
                System.out.print("ID : "); int id = scanner.nextInt(); scanner.nextLine();
                System.out.print("Numéro : "); String numero = scanner.nextLine();
                System.out.print("Solde : "); double solde = scanner.nextDouble();
                System.out.print("ID Client : "); int idClient = scanner.nextInt();
                System.out.print("Taux intérêt : "); double taux = scanner.nextDouble();
                compteService.creerCompte(new CompteEpargne(id, numero, solde, idClient, taux));
                System.out.println("Compte épargne créé !");
            }
            case 3 -> {
                System.out.print("ID Client : "); int idClient = scanner.nextInt();
                compteService.rechercherComptesParClient(idClient).forEach(System.out::println);
            }
            default -> System.out.println("Choix invalide !");
        }
    }

    // === Menu Transactions ===
    private void menuTransactions() {
        System.out.println("\n--- Gestion Transactions ---");
        System.out.println("1. Enregistrer transaction");
        System.out.println("2. Lister transactions par compte");
        System.out.print("Choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1 -> {
                System.out.print("ID : "); int id = scanner.nextInt(); scanner.nextLine();
                System.out.print("Montant : "); double montant = scanner.nextDouble(); scanner.nextLine();
                System.out.print("Type (DEPOT/RETRAIT) : "); String type = scanner.nextLine();
                System.out.print("Lieu : "); String lieu = scanner.nextLine();
                System.out.print("ID Compte : "); int idCompte = scanner.nextInt();
                Transaction t = new Transaction(id, LocalDate.now(), montant, TypeTransaction.valueOf(type.toUpperCase()), lieu, idCompte);
                transactionService.enregistrerTransaction(t);
                System.out.println("Transaction enregistrée !");
            }
            case 2 -> {
                System.out.print("ID Compte : "); int idCompte = scanner.nextInt();
                transactionService.listerTransactionsParCompte(idCompte).forEach(System.out::println);
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
        System.out.print("Choix : ");
        int choix = scanner.nextInt();

        switch (choix) {
            case 1 -> rapportService.top5Clients().forEach(System.out::println);
            case 2 -> rapportService.rapportMensuel().forEach((type, count) ->
                    System.out.println(type + " : " + count));
            case 3 -> rapportService.detecterTransactionsSuspectes(10000).forEach(System.out::println);
            default -> System.out.println("Choix invalide !");
        }
    }
}
