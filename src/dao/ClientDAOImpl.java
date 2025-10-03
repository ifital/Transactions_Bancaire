    package dao;

    import config.DatabaseConfig;
    import entity.Client;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    public class ClientDAOImpl implements ClientDAO {

        @Override
        public void create(Client client) {
            String sql = "INSERT INTO client (id, nom, email) VALUES (?, ?, ?)";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, client.id());
                stmt.setString(2, client.nom());
                stmt.setString(3, client.email());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Optional<Client> findById(int id) {
            String sql = "SELECT * FROM client WHERE id = ?";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return Optional.of(new Client(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<Client> findAll() {
            List<Client> clients = new ArrayList<>();
            String sql = "SELECT * FROM client";
            try (Connection conn = DatabaseConfig.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    clients.add(new Client(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return clients;
        }

        @Override
        public void update(Client client) {
            String sql = "UPDATE client SET nom = ?, email = ? WHERE id = ?";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, client.nom());
                stmt.setString(2, client.email());
                stmt.setInt(3, client.id());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM client WHERE id = ?";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
