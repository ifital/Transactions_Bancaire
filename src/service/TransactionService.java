package service;

import entity.Transaction;
import enums.TypeTransaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    public void enregistrerTransaction(Transaction transaction);
    public List<Transaction> listerTransactionsParCompte(int idCompte);
    public List<Transaction> filtrerTransactions(int idCompte, Double montantMin, Double montantMax,
                                                 TypeTransaction type, LocalDate dateDebut, LocalDate dateFin);
    public List<Transaction> trouverTransactionsSuspectes(double seuilMontant);

}
