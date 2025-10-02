package service;

import entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public void ajouterClient(Client client);
    public void modifierClient(Client client);
    public void supprimerClient(int id);
    public Optional<Client> rechercherClientParId(int id);
    public List<Client> rechercherClientsParNom(String nom);
    public List<Client> listerTousClients();
}
