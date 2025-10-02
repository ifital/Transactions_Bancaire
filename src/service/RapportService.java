package service;

import dao.ClientDAO;
import dao.CompteDAO;
import dao.TransactionDAO;
import entity.Client;
import entity.Compte;
import entity.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RapportService {

    private final ClientDAO clientDAO;
    private final CompteDAO compteDAO;
    private final TransactionDAO transactionDAO;

    public RapportService(ClientDAO clientDAO, CompteDAO compteDAO, TransactionDAO transactionDAO) {
        this.clientDAO = clientDAO;
        this.compteDAO = compteDAO;
        this.transactionDAO = transactionDAO;
    }

    public List<Client> top5Clients(){
        try{
            return clientDAO.findAll().stream().sorted((t1,t2)-> Double.compare(compteDAO.findByClientId(t2.id()).stream().mapToDouble(Compte::getSolde).sum()
                            , compteDAO.findByClientId(t1.id()).stream().mapToDouble(Compte::getSolde).sum()))
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de trouver les top 5 clients: " + e.getMessage());
            throw new RuntimeException("Impossible de trouverles top 5 clients: ", e);
        }
    }

    public Map<String, Long> rapportMensuel() {
        try {
            return transactionDAO.findAll().stream()
                    .collect(Collectors.groupingBy(t -> t.type().name(), Collectors.counting()));
        }catch (Exception e) {
            System.err.println("Erreur lors de trouver le rapportMensuel: " + e.getMessage());
            throw new RuntimeException("Impossible de trouver le rapportMensuel: ", e);
        }
    }

    public List<Transaction> detecterTransactionsSuspectes(double seuilMontant){
        try {
            return transactionDAO.findAll().stream().filter(e->e.montant() > seuilMontant)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur lors de detecter les transactions suspectes: " + e.getMessage());
            throw new RuntimeException("Impossible de detecter les transactions suspectes: ", e);
        }
    }

    public List<Compte> comptesInactifs(LocalDate depuis) {
        List<Transaction> transactions = transactionDAO.findAll();
        try {
            return compteDAO.findAll().stream()
                    .filter(c -> transactions.stream()
                            .filter(t -> t.idCompte() == c.getId())
                            .noneMatch(t -> !t.date().isBefore(depuis)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de detecter les comptes inactifs: " + e.getMessage());
            throw new RuntimeException("Impossible de detecter les comptes inactifs: ", e);
        }

    }


}
