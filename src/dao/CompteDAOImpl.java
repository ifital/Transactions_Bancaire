package dao;

import config.DatabaseConfig;
import entity.Compte;
import entity.CompteCourant;
import entity.CompteEpargne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAOImpl implements CompteDAO {

    @Override
    public void create(Compte compte) {
        String sql = "INSERT INTO compte (id, numero, solde, idClient, type, extra) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, compte.getId());
            stmt.setString(2, compte.getNumero());
            stmt.setDouble(3, compte.getSolde());
            stmt.setInt(4, compte.getIdClient());

            if (compte instanceof CompteCourant cc) {
                stmt.setString(5, "COURANT");
                stmt.setDouble(6, cc.getDecouvertAutorise());
            } else if (compte instanceof CompteEpargne ce) {
                stmt.setString(5, "EPARGNE");
                stmt.setDouble(6, ce.getTauxInteret());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Compte findById(int id) {
        String sql = "SELECT * FROM compte WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapCompte(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Compte> findAll() {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM compte";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                comptes.add(mapCompte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
    }

    @Override
    public void update(Compte compte) {
        String sql = "UPDATE compte SET solde = ?, extra = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, compte.getSolde());

            if (compte instanceof CompteCourant cc) {
                stmt.setDouble(2, cc.getDecouvertAutorise());
            } else if (compte instanceof CompteEpargne ce) {
                stmt.setDouble(2, ce.getTauxInteret());
            }

            stmt.setInt(3, compte.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM compte WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Compte mapCompte(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if ("COURANT".equals(type)) {
            return new CompteCourant(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDouble("solde"),
                    rs.getInt("idClient"),
                    rs.getDouble("extra")
            );
        } else {
            return new CompteEpargne(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDouble("solde"),
                    rs.getInt("idClient"),
                    rs.getDouble("extra")
            );
        }
    }
}
