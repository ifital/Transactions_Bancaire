package dao;

import entity.Transaction;

import java.util.List;

public class TransactionDAOImpl implements TransactionDAO{
    @Override
    public void create(Transaction transaction) {

    }

    @Override
    public Transaction findById(int id) {
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public void delete(int id) {

    }
}
