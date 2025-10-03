package util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Scanner scanner = new Scanner(System.in);

    // Lire un entier avec vérification
    public static int lireInt(String message) {
        int valeur;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                valeur = scanner.nextInt();
                scanner.nextLine(); // consommer le retour chariot
                return valeur;
            } else {
                System.out.println("❌ Entrée invalide, veuillez entrer un entier.");
                scanner.nextLine(); // consommer l'entrée invalide
            }
        }
    }

    // Lire un double avec vérification
    public static double lireDouble(String message) {
        double valeur;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                valeur = scanner.nextDouble();
                scanner.nextLine(); // consommer le retour chariot
                return valeur;
            } else {
                System.out.println("❌ Entrée invalide, veuillez entrer un nombre décimal.");
                scanner.nextLine(); // consommer l'entrée invalide
            }
        }
    }

    // Lire une chaîne non vide
    public static String lireString(String message) {
        String valeur;
        while (true) {
            System.out.print(message);
            valeur = scanner.nextLine().trim();
            if (!valeur.isEmpty()) {
                return valeur;
            } else {
                System.out.println("❌ Entrée invalide, veuillez ne pas laisser vide.");
            }
        }
    }

    // Lire un email valide
    public static String lireEmail(String message) {
        String email;
        Pattern pattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        while (true) {
            System.out.print(message);
            email = scanner.nextLine().trim();
            if (pattern.matcher(email).matches()) {
                return email;
            } else {
                System.out.println("❌ Email invalide, veuillez réessayer.");
            }
        }
    }

    // Lire un type de transaction (DEPOT ou RETRAIT)
    public static String lireTypeTransaction(String message) {
        String type;
        while (true) {
            System.out.print(message);
            type = scanner.nextLine().trim().toUpperCase();
            if (type.equals("DEPOT") || type.equals("RETRAIT")) {
                return type;
            } else {
                System.out.println("❌ Type invalide, veuillez entrer DEPOT ou RETRAIT.");
            }
        }
    }

    // Lire une valeur booléenne (oui/non)
    public static boolean lireOuiNon(String message) {
        String valeur;
        while (true) {
            System.out.print(message + " (oui/non) : ");
            valeur = scanner.nextLine().trim().toLowerCase();
            if (valeur.equals("oui")) return true;
            else if (valeur.equals("non")) return false;
            else System.out.println("❌ Entrée invalide, veuillez entrer 'oui' ou 'non'.");
        }
    }
}
