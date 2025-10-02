package service;

import dao.ClientDAO;
import entity.Client;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO ;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public void ajouterClient(Client client) {
        try {
            clientDAO.create(client);
        }catch (Exception e) {
            System.err.println("Erreur lors de la création de client: " + e.getMessage());
            throw new RuntimeException("Impossible de créer client", e);
        }
    }

    @Override
    public void modifierClient(Client client) {
        try {
            clientDAO.update(client);
        }catch (Exception e) {
            System.err.println("Erreur lors de la modification de client: " + e.getMessage());
            throw new RuntimeException("Impossible modifier le client", e);
        }

    }

    @Override
    public void supprimerClient(int id) {
        try {
            clientDAO.delete(id);
        }catch (Exception e) {
            System.err.println("Erreur lors de la suppresion de client: " + e.getMessage());
            throw new RuntimeException("Impossible supprimer le client", e);
        }
    }

    @Override
     public Optional<Client> rechercherClientParId(int id) {
        try {
            return clientDAO.findById(id);
        }catch (Exception e) {
            System.err.println("Erreur lors de la recherche de client: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Client> rechercherClientsParNom(String nom) {
        try{
            return clientDAO.findAll().stream().filter(e -> e.equals(nom)).toList();
        }catch (Exception e){
            System.err.println("Erreur lors de chercher par nom le client: " + e.getMessage());
            throw new RuntimeException("Impossible de chercher par nom les client", e);
        }
    }

    @Override
    public List<Client> listerTousClients() {
        try {
            return clientDAO.findAll();
        } catch (Exception e) {
            System.err.println("Erreur lors de lister les client: " + e.getMessage());
            throw new RuntimeException("Impossible de lister les client", e);
        }
    }
}
