package service;

import entity.Compte;

import java.util.List;

public class CompteServiceImpl implements CompteService{
    @Override
    public void creerCompte(Compte compte) {

    }

    @Override
    public void mettreAJourCompte(Compte compte) {

    }

    @Override
    public Compte rechercherCompteParNumero(String numero) {
        return null;
    }

    @Override
    public List<Compte> rechercherComptesParClient(int idClient) {
        return List.of();
    }

    @Override
    public Compte compteAvecSoldeMax() {
        return null;
    }

    @Override
    public Compte compteAvecSoldeMin() {
        return null;
    }
}
