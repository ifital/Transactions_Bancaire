package dao;

import entity.Compte;
import java.util.List;

public interface CompteDAO {
    void create(Compte compte);
    Compte findById(int id);
    List<Compte> findByClientId(int idClient);
    List<Compte> findAll();
    void update(Compte compte);
    void delete(int id);
}
