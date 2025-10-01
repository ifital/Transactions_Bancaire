import java.sql.Connection;
import config.DatabaseConfig;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connexion réussie à la base de données !");
            } else {
                System.out.println("Échec de la connexion.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}