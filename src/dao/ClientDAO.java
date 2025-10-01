package dao;

import entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO {
    void create(Client client) throws SQLException;
    Client findById(int id);
    List<Client> findAll();
    void update(Client client);
    void delete(int id);
}
