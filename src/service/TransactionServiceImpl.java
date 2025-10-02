package service;

import entity.Transaction;
import enums.TypeTransaction;

import java.time.LocalDate;
import java.util.List;

public class TransactionServiceImpl implements TransactionService{
    @Override
    public void enregistrerTransaction(Transaction transaction) {

    }

    @Override
    public List<Transaction> listerTransactionsParCompte(int idCompte) {
        return List.of();
    }

    @Override
    public List<Transaction> filtrerTransactions(int idCompte, Double montantMin, Double montantMax, TypeTransaction type, LocalDate dateDebut, LocalDate dateFin) {
        return List.of();
    }

    @Override
    public List<Transaction> trouverTransactionsSuspectes(double seuilMontant) {
        return List.of();
    }
}
