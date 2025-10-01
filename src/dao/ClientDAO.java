package dao;

import entity.Client;
import java.util.List;

public interface ClientDAO {
    void create(Client client);
    Client findById(int id);
    List<Client> findAll();
    void update(Client client);
    void delete(int id);
}
