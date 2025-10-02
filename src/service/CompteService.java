package service;

import entity.Compte;

import java.util.List;
import java.util.Optional;

public interface CompteService {

    public void creerCompte(Compte compte);
    public void mettreAJourCompte(Compte compte);
    public Optional<Compte> rechercherCompteParNumero(String numero);
    public List<Compte> rechercherComptesParClient(int idClient);
    public Compte compteAvecSoldeMax();
    public Compte compteAvecSoldeMin();
}
