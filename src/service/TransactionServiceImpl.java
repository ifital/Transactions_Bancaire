package service;

import dao.TransactionDAO;
import entity.Transaction;
import enums.TypeTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService{

    private final TransactionDAO transactionDAO ;
    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public void enregistrerTransaction(Transaction transaction) {
        try{
            transactionDAO.create(transaction);
        } catch (Exception e) {
            System.err.println("Erreur lors de creation de transaction: " + e.getMessage());
            throw new RuntimeException("Impossible de creer transaction: ", e);
        }
    }

    @Override
    public List<Transaction> listerTransactionsParCompte(int idCompte) {
        try{
            return transactionDAO.findAll().stream().sorted((t1, t2)-> t2.date().compareTo(t1.date()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de lister les transaction par compte: " + e.getMessage());
            throw new RuntimeException("Impossible de lister les transactions: ", e);
        }
    }

    @Override
    public List<Transaction> filtrerTransactions(int idCompte, Double montantMin, Double montantMax, TypeTransaction type, LocalDate dateDebut, LocalDate dateFin) {
        try {
            return listerTransactionsParCompte(idCompte).stream()
                    .filter(t -> (montantMin == null || t.montant() >= montantMin) &&
                            (montantMax == null || t.montant() <= montantMax) &&
                            (type == null || t.type() == type) &&
                            (dateDebut == null || !t.date().isBefore(dateDebut)) &&
                            (dateFin == null || !t.date().isAfter(dateFin)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de trouver le max du solde: " + e.getMessage());
            throw new RuntimeException("Impossible de trouver le max: ", e);
        }
    }

    @Override
    public List<Transaction> trouverTransactionsSuspectes(double seuilMontant) {
        try{
            return transactionDAO.findAll().stream().filter(e->e.montant() > seuilMontant)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de trouver les transactions suspectes: " + e.getMessage());
            throw new RuntimeException("Impossible de trouver les transactions suspectes: ", e);
        }
    }
}
