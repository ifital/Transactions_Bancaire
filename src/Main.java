import ui.MainUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initialisation de l'application...");

        try {
            MainUI ui = new MainUI();
            ui.demarrer();
        } catch (Exception e) {
            System.err.println("Erreur fatale lors du démarrage: " + e.getMessage());
            e.printStackTrace();
        }
    }
}