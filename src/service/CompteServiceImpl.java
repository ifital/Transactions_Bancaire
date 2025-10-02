package service;

import dao.CompteDAO;
import entity.Compte;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CompteServiceImpl implements CompteService{

    private final CompteDAO compteDAO ;

    public CompteServiceImpl(CompteDAO compteDAO) {
        this.compteDAO = compteDAO;
    }

    @Override
    public void creerCompte(Compte compte) {
        try {
            compteDAO.create(compte);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de compte: " + e.getMessage());
            throw new RuntimeException("Impossible de créer compte", e);
        }
    }

    @Override
    public void mettreAJourCompte(Compte compte) {
        try {
            compteDAO.update(compte);
        }catch (Exception e) {
            System.err.println("Erreur lors de la modification de compte: " + e.getMessage());
            throw new RuntimeException("Impossible de modifier le compte", e);
        }
    }

    @Override
    public Optional<Compte> rechercherCompteParNumero(String numero) {
        try {
            compteDAO.findAll().stream().filter(e -> e.getNumero().equals(numero)).findFirst().orElse(null);
        }catch (Exception e){
            System.err.println("Erreur lors de chercher de compte par numero" + e.getMessage());
        }
        return null ;
    }

    @Override
    public List<Compte> rechercherComptesParClient(int idClient) {
        try {
            return compteDAO.findByClientId(idClient);
        } catch (Exception e) {
            System.err.println("Erreur lors de chercher les comptes par client: " + e.getMessage());
            throw new RuntimeException("Impossible de chercher les comptes oar client: ", e);
        }
    }

    @Override
    public Compte compteAvecSoldeMax() {
        try {
            return compteDAO.findAll().stream().max(Comparator.comparing(Compte::getSolde)).orElse(null);
        }catch (Exception e){
            System.err.println("Erreur lors de trouver le max du solde: " + e.getMessage());
            throw new RuntimeException("Impossible de trouver le max: ", e);
        }
    }

    @Override
    public Compte compteAvecSoldeMin() {
        try{
            return compteDAO.findAll().stream().min(Comparator.comparing(Compte::getSolde)).orElse(null);
        } catch (Exception e) {
            System.err.println("Erreur lors de trouver le min du solde: " + e.getMessage());
            throw new RuntimeException("Impossible de trouver le min: ", e);
        }
    }
}
