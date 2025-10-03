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
        String sql = "INSERT INTO compte (id, numero, solde, idclient, typecompte, decouvertautorise, tauxinteret) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, compte.getId());
            stmt.setString(2, compte.getNumero());
            stmt.setDouble(3, compte.getSolde());
            stmt.setInt(4, compte.getIdClient());

            if (compte instanceof CompteCourant cc) {
                stmt.setString(5, "COURANT");
                stmt.setDouble(6, cc.getDecouvertAutorise());
                stmt.setNull(7, Types.NUMERIC); // tauxinteret nul pour courant
            } else if (compte instanceof CompteEpargne ce) {
                stmt.setString(5, "EPARGNE");
                stmt.setNull(6, Types.NUMERIC); // découvert nul pour épargne
                stmt.setDouble(7, ce.getTauxInteret());
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
    public List<Compte> findByClientId(int idClient) {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM compte WHERE idclient = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comptes.add(mapCompte(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
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
        String sql = "UPDATE compte SET solde = ?, decouvertautorise = ?, tauxinteret = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, compte.getSolde());

            if (compte instanceof CompteCourant cc) {
                stmt.setDouble(2, cc.getDecouvertAutorise());
                stmt.setNull(3, Types.NUMERIC);
            } else if (compte instanceof CompteEpargne ce) {
                stmt.setNull(2, Types.NUMERIC);
                stmt.setDouble(3, ce.getTauxInteret());
            }

            stmt.setInt(4, compte.getId());
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
        String type = rs.getString("typecompte");
        if ("COURANT".equalsIgnoreCase(type)) {
            return new CompteCourant(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDouble("solde"),
                    rs.getInt("idclient"),
                    rs.getDouble("decouvertautorise")
            );
        } else {
            return new CompteEpargne(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDouble("solde"),
                    rs.getInt("idclient"),
                    rs.getDouble("tauxinteret")
            );
        }
    }
}
