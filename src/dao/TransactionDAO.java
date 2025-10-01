package dao;

import entity.Transaction;
import java.util.List;

public interface TransactionDAO {
    void create(Transaction transaction);
    Transaction findById(int id);
    List<Transaction> findAll();
    void delete(int id);
}
