package onlineexam;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // initialize storage (creates sample user & questions if files don't exist)
        DataManager.init();
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}