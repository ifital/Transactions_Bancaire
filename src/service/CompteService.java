package service;

import entity.Compte;

import java.util.List;

public interface CompteService {

    public void creerCompte(Compte compte);
    public void mettreAJourCompte(Compte compte);
    public Compte rechercherCompteParNumero(String numero);
    public List<Compte> rechercherComptesParClient(int idClient);
    public Compte compteAvecSoldeMax();
    public Compte compteAvecSoldeMin();
}
