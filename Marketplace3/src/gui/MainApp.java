import gui.LoginFrame;
import service.SystemManager;

public class MainApp {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            SystemManager system = new SystemManager();
            new LoginFrame(system);
        });
    }
}
